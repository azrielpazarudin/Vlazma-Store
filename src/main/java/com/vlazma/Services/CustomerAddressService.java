package com.vlazma.Services;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vlazma.Dto.ResponseData;
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
                .customers(cuad.getCustomer())
                .address(cuad.getAddress())
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

    private CustomerAddressResponse mapToResponse(CustomerAddress customerAddress) {
        return CustomerAddressResponse.builder()
                .customers(customerAddress.getCustomer())
                .address(customerAddress.getAddress())
                .build();
    }

    public ResponseEntity<ResponseData<List<CustomerAddressResponse>>> findById(int id) {
        List<CustomerAddress> customerAddresses = customersAddressRepository.findAll();
        List<CustomerAddress> custAddById = new ArrayList<>();
        for(CustomerAddress ca : customerAddresses){
            if(ca.getCustomer().getId() == id){
                custAddById.add(ca);
            }
        }
        
        ResponseData<List<CustomerAddressResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(custAddById.stream().map(this::mapToResponse).toList());
        return ResponseEntity.ok(responseData);
    }

    public void delete(int id){
        List<CustomerAddress> customerAddresses = customersAddressRepository.findAll();
        List<CustomerAddress> custAddById = new ArrayList<>();
        for(CustomerAddress ca : customerAddresses){
            if(ca.getAddress().getId() == id){
                custAddById.add(ca);
            }
            customersAddressRepository.delete(ca);
        }
    }
    
}
