package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vlazma.Dto.Admin.AdminRequest;
import com.vlazma.Dto.Users.ChangePassword;
import com.vlazma.Services.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/")
    public Object get() {
        return adminService.getAllAdmin();
    }

    @GetMapping("/find-by-id/{id}")
    public Object getById(@PathVariable int id) {
        return adminService.findById(id);
    }

    @Operation(summary = "Adding Admin Data", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/")
    public Object create(HttpServletRequest request, @Valid @RequestBody AdminRequest adminRequest, Errors errors) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return adminService.createAdmin(adminRequest, request, errors);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin Can Acces It");

    }

    @Operation(summary = "Editing Admin Data", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/edit")
    public Object edit(HttpServletRequest request, @Valid @RequestBody AdminRequest adminRequest,
            Errors errors) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return adminService.editAdmin(request, adminRequest, errors);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin Can Acces It");

    }

    @Operation(summary = "Change The Password", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/change-password")
    public Object changePassword(HttpServletRequest request, @Valid @RequestBody ChangePassword changePassword,
            Errors errors) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return adminService.changePassword(request, changePassword, errors);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin Can Acces It");
    }

    @DeleteMapping("/deactivate/{id}")
    public Object delete(@PathVariable int id) {
        return adminService.deactivateAdmin(id);
    }

}
