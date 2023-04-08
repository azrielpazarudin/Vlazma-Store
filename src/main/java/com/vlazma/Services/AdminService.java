package com.vlazma.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.vlazma.Dto.ResponseData;
import com.vlazma.Dto.Admin.AdminRequest;
import com.vlazma.Dto.Admin.AdminResponse;
import com.vlazma.Models.Admin;
import com.vlazma.Models.Users;
import com.vlazma.Repositories.AdminRepository;
import com.vlazma.Repositories.UsersRepository;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;

    public ResponseEntity<ResponseData<AdminResponse>> createAdmin(AdminRequest adminRequest, Errors errors) {
        ResponseData<AdminResponse> responseData = new ResponseData<>();
        Optional<Users> users = usersRepository.findById(0);
        var adUs = adminRepository.findByUserId(0);
        try {
            users = usersRepository.findById(Integer.parseInt(adminRequest.getUserId()));
            adUs=adminRepository.findByUserId(Integer.parseInt(adminRequest.getUserId()));
        } catch (NumberFormatException e) {
        }
        
        if (errors.hasErrors() || users.isEmpty() || !users.get().getRole().getRoleName().name().equals("ADMIN")||adUs.isPresent()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(users.isEmpty() ? "Users Not Found" : null);
            responseData.getMessages().add(adUs.isPresent()?"User Is Already Registered":null);
            responseData.getMessages()
                    .add(users.isPresent()&&!users.get().getRole().getRoleName().name().equals("ADMIN") ? "This User Role Must Admin" : null);
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            return ResponseEntity.badRequest().body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        var admin = Admin.builder()
                .fullName(adminRequest.getFullName())
                .user(users.get())
                .build();
        adminRepository.save(admin);
        responseData.setPayload(AdminResponse.builder()
                .id(admin.getId())
                .fullName(admin.getFullName())
                .userId(admin.getUser().getId())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    public ResponseEntity<ResponseData<List<AdminResponse>>> getAllAdmin() {
        List<Admin> admins = adminRepository.findAll();
        ResponseData<List<AdminResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(admins.stream().map(this::mapToResponse).toList());
        return ResponseEntity.ok().body(responseData);
    }

    private AdminResponse mapToResponse(Admin admin) {
        return AdminResponse.builder()
                .id(admin.getId())
                .fullName(admin.getFullName())
                .userId(admin.getUser().getId())
                .build();
    }

    public ResponseEntity<ResponseData<AdminResponse>> findById(int id) {
        Optional<Admin> admins = adminRepository.findById(id);
        ResponseData<AdminResponse> responseData = new ResponseData<>();
        if (admins.isEmpty()) {
            responseData.getMessages().add("Admin Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        var admin = AdminResponse.builder()
                .id(admins.get().getId())
                .fullName(admins.get().getFullName())
                .userId(admins.get().getId())
                .build();
        responseData.setPayload(admin);
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<AdminResponse>> editAdmin(int id, AdminRequest adminRequest, Errors errors) {
        Optional<Admin> adm = adminRepository.findById(id);
        ResponseData<AdminResponse> responseData = new ResponseData<>();
        if (adm.isEmpty() || errors.hasErrors()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(adm.isEmpty() ? "Admin Not Found" : null);
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        Admin admin = adm.get();
        admin.setFullName(adminRequest.getFullName());
        admin.setUser(adm.get().getUser());
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        adminRepository.save(admin);
        responseData.setPayload(AdminResponse.builder()
                .id(adm.get().getId())
                .fullName(adm.get().getFullName())
                .userId(adm.get().getUser().getId()).build());
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<AdminResponse>> deactivateAdmin(int id) {
        Optional<Admin> admin = adminRepository.findById(id);
        ResponseData<AdminResponse> responseData = new ResponseData<>();
        if (admin.isEmpty()) {
            responseData.getMessages().add("Admin Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        usersService.deactivateUser(admin.get().getUser().getId());
        responseData.setPayload(AdminResponse.builder()
                .id(id)
                .fullName(admin.get().getFullName())
                .userId(admin.get().getUser().getId()).build());
        return ResponseEntity.ok(responseData);

    }
}
