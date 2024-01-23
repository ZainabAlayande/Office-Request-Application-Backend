package africa.semicolon.remApp.controllers;

import africa.semicolon.remApp.dtos.requests.MakeRequestForm;
import africa.semicolon.remApp.services.request.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/v1/request/form",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> request(@RequestBody MakeRequestForm requestForm) {
        String userId = SecurityContextHolder.getContext().toString();
        var response = requestService.makeRequest(userId, requestForm);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/v1/request/all-assigned-request",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> viewAllAssignedRequest()  {
        String userId = SecurityContextHolder.getContext().toString();
        var response = requestService.viewAllAssignedRequest(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/v1/request/view-all-completed-request",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> viewAllCompletedRequest()  {
        String userId = SecurityContextHolder.getContext().toString();
        var response = requestService.viewAllCompletedRequest(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/v1/request/view-all-pending-request",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> viewAllPendingRequest()  {
        String userId = SecurityContextHolder.getContext().toString();
        var response = requestService.viewAllPendingRequest(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
