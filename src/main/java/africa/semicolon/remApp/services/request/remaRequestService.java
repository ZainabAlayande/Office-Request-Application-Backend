package africa.semicolon.remApp.services.request;

import africa.semicolon.remApp.dtos.requests.EmailNotificationRequest;
import africa.semicolon.remApp.dtos.requests.MakeRequestForm;
import africa.semicolon.remApp.dtos.requests.Recipient;
import africa.semicolon.remApp.dtos.requests.Sender;
import africa.semicolon.remApp.dtos.responses.MakeRequestResponse;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.ViewRequestResponse;
import africa.semicolon.remApp.exception.ORMException;
import africa.semicolon.remApp.models.BioData;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.models.Request;
import africa.semicolon.remApp.models.RequestStatus;
import africa.semicolon.remApp.repositories.RequestRepository;
import africa.semicolon.remApp.services.employee.EmployeeService;
import africa.semicolon.remApp.services.notification.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static africa.semicolon.remApp.services.notification.template.MakeRequest.makeRequestTemplate;
import static africa.semicolon.remApp.utils.EmailUtils.*;

@Service
@AllArgsConstructor
@Slf4j
public class remaRequestService implements RequestService {

    private final RequestRepository requestRepository;
    private final ModelMapper modelMapper;
    private final MailService mailService;
    private final EmployeeService employeeService;

    @Override
    public MakeRequestResponse makeRequest(String userId, MakeRequestForm makeRequestForm) {
        Optional<Employee> employee = employeeService.findUserById(userId);
        Employee validatedEmployee = validateEmployeeById(employee);
        Request mappedRequest = modelMapper.map(makeRequestForm, Request.class);
        mappedRequest.setStatus(RequestStatus.UNASSIGNED);
        mappedRequest.setTimeRequested(LocalDateTime.now());
        mappedRequest.setUserId(userId);
        requestRepository.save(mappedRequest);

        EmailNotificationRequest emailNotificationRequest = buildEmailRequest(validatedEmployee);
        var response = mailService.sendMail(emailNotificationRequest);
        log.info("response {} -> " + response);

        return buildMakeRequestResponse();
    }

    private Employee validateEmployeeById(Optional<Employee> employee) {
        Employee foundEmployee = employee.get();
        if (foundEmployee == null) {
            throw new ORMException("Invalid.....Cannot process");
        }
        return foundEmployee;
    }

    private EmailNotificationRequest buildEmailRequest(Employee employee) {
        BioData bioData = employee.getBioData();
        String email = bioData.getOfficeEmailAddress();
        String firstName = employee.getFirstName();
        EmailNotificationRequest request = new EmailNotificationRequest();
        Sender sender = new Sender(APP_NAME, APP_EMAIL);
        Recipient recipient = new Recipient(email,firstName);
        request.setEmailSender(sender);
        request.setRecipient(Set.of(recipient));
        request.setSubject(PENDING_REQUEST);
        String template = makeRequestTemplate(firstName);
        request.setContent(template);
        return request;
    }

    private MakeRequestResponse buildMakeRequestResponse() {
        MakeRequestResponse makeRequestResponse = new MakeRequestResponse();
        makeRequestResponse.setMessage("Success");
        makeRequestResponse.setStatus(true);
        return makeRequestResponse;
    }

    @Override
    public List<ViewRequestResponse> viewAllRequestByUserId(String userId) {
        var allRequest = requestRepository.findAll();
        List<ViewRequestResponse> responseList = new ArrayList<>();
        Request foundRequest = new Request();

        for (Request request: allRequest) {
            if (request.getUserId().equals(userId)) {
                ViewRequestResponse response = convertRequestToResponse(request);
                responseList.add(response);
            }
        }
        if (foundRequest != null) {
            return responseList;
        } else {
            throw new ORMException("Invalid request");
        }
    }


    @Override
    public ApiResponse<?> viewAllPendingRequest(String userId) {
        List<Request> allRequest = requestRepository.findByUserId(userId);
        List<Request> response = new ArrayList<>();
        for (Request request: allRequest) {
            if (request.getStatus().equals(RequestStatus.UNASSIGNED)) {
                response.add(request);
            } else {
                ApiResponse.builder().status(false).message("No unassigned request").data("").build();
            }
        }
        return ApiResponse.builder().status(true).message("Unassigned request").data(response).build();

    }

    @Override
    public ApiResponse<?> viewAllAssignedRequest(String userId) {
        List<Request> allRequest = requestRepository.findByUserId(userId);
        List<Request> response = new ArrayList<>();
        for (Request request: allRequest) {
            if (request.getStatus().equals(RequestStatus.ASSIGNED)) {
                response.add(request);
            } else {
                ApiResponse.builder().status(false).message("No assigned request").data("").build();
            }
        }
        return ApiResponse.builder().status(true).message("Assigned request").data(response).build();
    }

    @Override
    public ApiResponse<?> viewAllDeclinedRequest(String userId) {
        List<Request> allRequest = requestRepository.findByUserId(userId);
        List<Request> response = new ArrayList<>();
        for (Request request: allRequest) {
            if (request.getStatus().equals(RequestStatus.DECLINED)) {
                response.add(request);
            } else {
                ApiResponse.builder().status(false).message("No declined request").data("").build();
            }
        }
        return ApiResponse.builder().status(true).message("Declined request").data(response).build();
    }

    @Override
    public ApiResponse<?> viewAllCompletedRequest(String userId) {
        List<Request> allRequest = requestRepository.findByUserId(userId);
        List<Request> response = new ArrayList<>();
        for (Request request: allRequest) {
            if (request.getStatus().equals(RequestStatus.COMPLETED)) {
                response.add(request);
            } else {
                ApiResponse.builder().status(false).message("No completed request").data("").build();
            }
        }
        return ApiResponse.builder().status(true).message("Completed request").data(response).build();
    }

    private ViewRequestResponse convertRequestToResponse(Request request) {
        ViewRequestResponse response = new ViewRequestResponse();
        response.setBody(request.getBody());
        response.setStatus(request.getStatus().toString());
        response.setName(request.getName());
        response.setDescription(request.getDescription());
        response.setTitle(request.getTitle());
        response.setTimeRequested(request.getTimeRequested().toString());
        return response;
    }


}
