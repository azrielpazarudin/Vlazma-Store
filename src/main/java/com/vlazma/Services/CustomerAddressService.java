package com.vlazma.Services;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.vlazma.Dto.ResponseData;
import com.vlazma.Dto.Address.AddressRequest;
import com.vlazma.Dto.CustomerAddress.CustomerAddressRequest;
import com.vlazma.Dto.CustomerAddress.CustomerAddressResponse;
import com.vlazma.Models.CustomerAddress;
import com.vlazma.Repositories.AddressRepository;
import com.vlazma.Repositories.CustomersAddressRepository;
import com.vlazma.Repositories.CustomersRepository;
import java.util.List;

@Service
public class CustomerAddressService {
    @Autowired
    private CustomersAddressRepository customersAddressRepository;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressService addressService;

    public ResponseEntity<ResponseData<CustomerAddressResponse>> createCustomerAddress(
            CustomerAddressRequest customerAddressRequest) {
        var cust = customersRepository.findById(customerAddressRequest.getCustomerId());
        var adr = addressRepository.findById(customerAddressRequest.getAddressId());
        ResponseData<CustomerAddressResponse> responseData = new ResponseData<>();
        if (cust.isEmpty() || adr.isEmpty()) {
            responseData.getMessages().add(cust.isEmpty() ? "Customer Not Found" : null);
            responseData.getMessages().add(adr.isEmpty() ? "Address Not Found" : null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        CustomerAddress cuad = new CustomerAddress();
        cuad.setCustmer(cust.get());
        cuad.setAddress(adr.get());
        customersAddressRepository.save(cuad);
        responseData.setPayload(CustomerAddressResponse.builder()
                .customerId(cuad.getCustomer().getId())
                .customerName(cuad.getCustomer().getFullName())
                .idAddress(cuad.getAddress().getId())
                .province(cuad.getAddress().getProvince())
                .city(cuad.getAddress().getCity())
                .completeAddress(cuad.getAddress().getCompleteAddress())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);

    }

    public ResponseEntity<ResponseData<List<CustomerAddressResponse>>> getAllCustomerAddress() {
        List<CustomerAddress> customerAddresses = customersAddressRepository.findAll();
        ResponseData<List<CustomerAddressResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(customerAddresses.stream().map(this::mapToResponse).toList());
        return ResponseEntity.ok(responseData);
    }

    private CustomerAddressResponse mapToResponse(CustomerAddress cuad) {
        return CustomerAddressResponse.builder()
                .customerId(cuad.getCustomer().getId())
                .customerName(cuad.getCustomer().getFullName())
                .idAddress(cuad.getAddress().getId())
                .province(cuad.getAddress().getProvince())
                .city(cuad.getAddress().getCity())
                .completeAddress(cuad.getAddress().getCompleteAddress())
                .build();
    }

    public ResponseEntity<ResponseData<List<CustomerAddressResponse>>> findById(int id) {
        List<CustomerAddress> customerAddresses = customersAddressRepository.findAll();
        List<CustomerAddress> custAddById = new ArrayList<>();
        for (CustomerAddress ca : customerAddresses) {
            if (ca.getCustomer().getId() == id) {
                custAddById.add(ca);
            }
        }

        ResponseData<List<CustomerAddressResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(custAddById.stream().map(this::mapToResponse).toList());
        return ResponseEntity.ok(responseData);
    }

    public Object editCustomerAddress(AddressRequest addressRequest, int id, int address, Errors errors) {
        var cust = customersRepository.findById(id);
        var adr = addressRepository.findById(address);
        ResponseData<CustomerAddress> responseData = new ResponseData<>();
        if (cust.isEmpty() || adr.isEmpty() || errors.hasErrors()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(cust.isEmpty() ? "Customer Not Found" : null);
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        return addressService.editAddress(addressRequest, id, errors);

    }

    public void delete(int id) {
        List<CustomerAddress> customerAddresses = customersAddressRepository.findAll();
        List<CustomerAddress> custAddById = new ArrayList<>();
        for (CustomerAddress ca : customerAddresses) {
            if (ca.getAddress().getId() == id) {
                custAddById.add(ca);
            }
            customersAddressRepository.delete(ca);
        }
    }

}
