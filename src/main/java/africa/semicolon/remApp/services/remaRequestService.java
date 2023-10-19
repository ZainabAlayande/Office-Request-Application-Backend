package africa.semicolon.remApp.services;

import africa.semicolon.remApp.dtos.requests.MakeRequestForm;
import africa.semicolon.remApp.dtos.responses.MakeRequestResponse;
import africa.semicolon.remApp.dtos.responses.PendingRequestResponse;
import africa.semicolon.remApp.dtos.responses.ViewRequestResponse;
import africa.semicolon.remApp.models.Request;
import africa.semicolon.remApp.models.RequestStatus;
import africa.semicolon.remApp.repositories.RequestRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class remaRequestService implements RequestService {
    private final RequestRepository requestRepository;
    private final ModelMapper modelMapper;

    @Override
    public MakeRequestResponse makeRequest(MakeRequestForm makeRequestForm) {
        Request mappedRequest = modelMapper.map(makeRequestForm, Request.class);
        mappedRequest.setRequestStatus(RequestStatus.UNASSIGNED);
        mappedRequest.setTimeRequested(LocalDateTime.now());
        requestRepository.save(mappedRequest);
        return buildMakeRequestResponse(mappedRequest);
    }

    private MakeRequestResponse buildMakeRequestResponse(Request mappedRequest) {
        MakeRequestResponse makeRequestResponse = new MakeRequestResponse();
        makeRequestResponse.setMessage("Success");
        return makeRequestResponse;
    }

    @Override
    public List<ViewRequestResponse> viewAllRequestByUserId(String userId) {
        return null;
    }

    @Override
    public List<PendingRequestResponse> buildPendingRequest() {
        long unassignedRequestId = 0;
        requestRepository.findById(unassignedRequestId);
        return null;
    }
}
