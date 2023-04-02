package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vlazma.Dto.Admin.AdminRequest;
import com.vlazma.Services.AdminService;

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

    @GetMapping("/{id}")
    public Object getById(@PathVariable int id) {
        return adminService.findById(id);
    }

    @PostMapping("/")
    public Object create(@Valid @RequestBody AdminRequest adminRequest, Errors errors) {
        return adminService.createAdmin(adminRequest, errors);
    }

    @PostMapping("/{id}")
    public Object edit(@PathVariable int id, @Valid @RequestBody AdminRequest adminRequest, Errors errors) {
        return adminService.editAdmin(id, adminRequest, errors);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id) {
        return adminService.deleteAdmin(id);
    }

}
