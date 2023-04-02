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

import com.vlazma.Dto.Address.AddressRequest;
import com.vlazma.Services.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/")
    public Object get() {
        return addressService.getAllAdress();
    }

    @GetMapping("/{id}")
    public Object findById(@PathVariable int id){
        return addressService.findAddressById(id);
    }

    @PostMapping("/")
    public Object create(@Valid @RequestBody AddressRequest addressRequest, Errors errors) {
        return addressService.createAddress(addressRequest, errors);
    }

    @PostMapping("/{id}")
    public Object edit(@Valid@RequestBody AddressRequest addressRequest,@PathVariable int id,Errors errors){
        return addressService.editAddress(addressRequest, id, errors);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id){
        
        return addressService.deleteAddress(id);
    }
}
