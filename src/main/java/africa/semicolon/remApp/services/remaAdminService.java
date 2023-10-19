package africa.semicolon.remApp.services;

import africa.semicolon.remApp.dtos.requests.AdminLoginRequest;
import africa.semicolon.remApp.dtos.requests.AdminRegisterRequest;
import africa.semicolon.remApp.dtos.responses.AdminLoginResponse;
import africa.semicolon.remApp.dtos.responses.AdminRegisterResponse;
import africa.semicolon.remApp.dtos.responses.PendingRequestResponse;
import africa.semicolon.remApp.models.Admin;
import africa.semicolon.remApp.models.BioData;
import africa.semicolon.remApp.repositories.AdminRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class remaAdminService implements AdminService{

    private AdminRepository adminRepository;
    private final RequestService requestService;
    private final ModelMapper modelMapper;


    @Override
    public AdminRegisterResponse register(AdminRegisterRequest adminRegisterRequest) {
        BioData bioData = modelMapper.map(adminRegisterRequest, BioData.class);
        Admin admin = new Admin();
        admin.setBioData(bioData);
        admin.setPosition(admin.getPosition());
        adminRepository.save(admin);
        return buildAdminResponse();
    }

    private AdminRegisterResponse buildAdminResponse() {
        return null;
    }

    @Override
    public AdminLoginResponse login(AdminLoginRequest adminLoginRequest) {
        return null;
    }

    @Override
    public List<PendingRequestResponse> viewPendingRequest() {
        List<PendingRequestResponse> pendingRequestResponses = requestService.buildPendingRequest();
        return pendingRequestResponses;
    }

    @Override
    public void assignedRequest() {

    }

    @Override
    public void declineRequest() {

    }

    @Override
    public void completeRequest() {

    }
}
