package africa.semicolon.remApp.services.admin;

import africa.semicolon.remApp.dtos.requests.AdminLoginRequest;
import africa.semicolon.remApp.dtos.requests.AdminRegisterRequest;
import africa.semicolon.remApp.dtos.responses.AdminLoginResponse;
import africa.semicolon.remApp.dtos.responses.AdminRegisterResponse;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.models.Admin;
import africa.semicolon.remApp.models.BioData;
import africa.semicolon.remApp.repositories.AdminRepository;
import africa.semicolon.remApp.services.request.RequestService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public ApiResponse<?> viewPendingRequest() {
        return null;
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
