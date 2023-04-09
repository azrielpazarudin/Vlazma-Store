package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Dto.Address.AddressRequest;
import com.vlazma.Dto.CustomerAddress.CustomerAddressRequest;
import com.vlazma.Services.CustomerAddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/customer-address")
public class CustomerAddressController {
    @Autowired
    private CustomerAddressService customerAddressService;
    @GetMapping("/")
    public Object get(){
        return customerAddressService.getAllCustomerAddress();
    }

    @GetMapping("/find-by-customer-id/{id}")
    public Object findById(@PathVariable int id){
        return customerAddressService.findById(id);
    }
    @PostMapping("/")
    public Object create(@RequestBody CustomerAddressRequest customerAddressRequest){
        return customerAddressService.createCustomerAddress(customerAddressRequest);
    }

    @PostMapping("/edit-customer-address/{id}/{address}")
    public Object edit(@Valid@RequestBody AddressRequest addressRequest,@PathVariable int id,@PathVariable int address,Errors errors){
        return customerAddressService.editCustomerAddress(addressRequest, id, address, errors);
    }

}
