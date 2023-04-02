package com.vlazma.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.vlazma.Dto.ResponseData;
import com.vlazma.Dto.Address.AddressRequest;
import com.vlazma.Dto.Address.AddressResponse;
import com.vlazma.Models.Address;
import com.vlazma.Repositories.AddressRepository;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerAddressService customerAddressService;
    public ResponseEntity<ResponseData<AddressResponse>> createAddress(AddressRequest address, Errors errors) {
        ResponseData<AddressResponse> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        Address adr = new Address();
        adr.setCity(address.getCity());
        adr.setProvince(address.getProvince());
        adr.setCompleteAddress(address.getCompleteAddress());
        addressRepository.save(adr);
        responseData.setPayload(AddressResponse.builder()
                .id(adr.getId())
                .province(adr.getProvince())
                .city(adr.getCity())
                .completeAddress(adr.getCompleteAddress())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    public ResponseEntity<ResponseData<List<AddressResponse>>> getAllAdress() {
        List<Address> address = addressRepository.findAll();
        ResponseData<List<AddressResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(address.stream().map(this::mapToResponse).toList());

        return ResponseEntity.ok().body(responseData);
    }

    private AddressResponse mapToResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .city(address.getCity())
                .province(address.getProvince())
                .completeAddress(address.getCompleteAddress())
                .build();
    }

    public ResponseEntity<ResponseData<AddressResponse>> findAddressById(int id) {
        var adr = addressRepository.findById(id).get();
        ResponseData<AddressResponse> responseData = new ResponseData<>();
        if (adr == null) {
            responseData.getMessages().add("Address Id Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(AddressResponse.builder()
                .id(adr.getId())
                .province(adr.getProvince())
                .city(adr.getCity())
                .completeAddress(adr.getCompleteAddress())
                .build());
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<AddressResponse>> editAddress(AddressRequest addressRequest, int id,
            Errors errors) {
        Optional<Address> address = addressRepository.findById(id);
        ResponseData<AddressResponse> responseData = new ResponseData<>();
        if (address.isEmpty() || errors.hasErrors()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(address.isEmpty() ? "User Not Found" : null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        address.get().setProvince(addressRequest.getProvince());
        address.get().setCity(addressRequest.getCity());
        address.get().setCompleteAddress(addressRequest.getCompleteAddress());
        addressRepository.save(address.get());
        responseData.setPayload(AddressResponse.builder()
                .id(address.get().getId())
                .province(address.get()
                        .getProvince())
                .city(address.get()
                        .getCity())
                .build());
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<AddressResponse>> deleteAddress(int id) {
        Optional<Address> address = addressRepository.findById(id);
        ResponseData<AddressResponse> responseData = new ResponseData<>();
        if (address.isEmpty()) {
            responseData.getMessages().add("Address Id Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        var adr = address.get();
        customerAddressService.delete(id);
        addressRepository.deleteById(id);
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(AddressResponse.builder()
        .id(adr.getId())
        .province(adr.getProvince())
        .city(adr.getCity())
        .build());
        return ResponseEntity.ok(responseData);
    }
}
