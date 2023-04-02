package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Dto.CustomerAddress.CustomerAddressRequest;
import com.vlazma.Services.CustomerAddressService;

@RestController
@RequestMapping("/vlazma/customer-address")
public class CustomerAddressController {
    @Autowired
    private CustomerAddressService customerAddressService;
    @GetMapping("/")
    public Object get(){
        return customerAddressService.getAllCustomerAddress();
    }

    @GetMapping("/{id}")
    public Object findById(@PathVariable int id){
        return customerAddressService.findById(id);
    }
    @PostMapping("/")
    public Object create(@RequestBody CustomerAddressRequest customerAddressRequest){
        return customerAddressService.createCustomerAddress(customerAddressRequest);
    }
}
