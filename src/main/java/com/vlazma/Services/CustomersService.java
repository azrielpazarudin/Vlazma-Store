package com.vlazma.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.vlazma.Dto.ResponseData;
import com.vlazma.Dto.Customers.CustomersRequest;
import com.vlazma.Dto.Customers.CustomersResponse;
import com.vlazma.Enumerations.Gender;
import com.vlazma.Models.Customers;
import com.vlazma.Repositories.CustomersRepository;
import com.vlazma.Repositories.UsersRepository;

import lombok.var;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import java.util.List;
import java.util.Optional;

@Service
public class CustomersService {
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;
    final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ResponseEntity<ResponseData<CustomersResponse>> createCustomers(CustomersRequest customersRequest,
            Errors errors) {
        ResponseData<CustomersResponse> responseData = new ResponseData<>();
        var user = usersRepository.findById(0);
        var cust = customersRepository.findByUserId(0);
        try {
            user = usersRepository.findById(Integer.parseInt(customersRequest.getUserId()));
            cust = customersRepository.findByUserId(Integer.parseInt(customersRequest.getUserId()));
        } catch (NumberFormatException e) {
        }
        if (errors.hasErrors() || user.isEmpty()
                || (user.isPresent() && !user.get().getRole().getRoleName().name().equals("CUSTOMER"))
                || !(customersRequest.getGender().equals("FEMALE") || customersRequest.getGender().equals("MALE"))
                || cust.isPresent()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(user.isEmpty() ? "User Not Found" : null);
            responseData.getMessages()
                    .add(!(customersRequest.getGender().equals("FEMALE") || customersRequest.getGender().equals("MALE"))
                            ? "Gender Must Be MALE Or Female"
                            : null);
            responseData.getMessages().add(customersRequest.getGender());
            responseData.getMessages()
                    .add(user.isPresent() && !user.get().getRole().getRoleName().name().equals("CUSTOMER")
                            ? "This User Role Must Customer"
                            : null);
            responseData.getMessages().add(cust.isPresent() ? "User Is Registered" : null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);

        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        Customers customers = new Customers();
        customers.setFullName(customersRequest.getFullName());
        customers.setGender(Gender.valueOf(customersRequest.getGender()));
        customers.setDateOfBirth(LocalDate.parse(customersRequest.getDateOfBirth(), dtf));
        customers.setPhoneNumber(customersRequest.getPhoneNumber());
        customers.setUser(usersRepository.findById(Integer.parseInt(customersRequest.getUserId())).get());
        customersRepository.save(customers);

        responseData.setPayload(CustomersResponse.builder()
                .id(customers.getId())
                .fullName(customers.getFullName())
                .gender(customers.getGender().name())
                .dateOfBirth(customers.getDateOfBirth())
                .phoneNumber(customers.getPhoneNumber())
                .userId(customers.getUser().getId())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    public ResponseEntity<ResponseData<List<CustomersResponse>>> getAllCustomers() {
        List<Customers> customers = customersRepository.findAll();
        ResponseData<List<CustomersResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(customers.stream().map(this::mapToResponse).toList());
        return ResponseEntity.ok(responseData);
    }

    private CustomersResponse mapToResponse(Customers customers) {
        return CustomersResponse.builder()
                .id(customers.getId())
                .fullName(customers.getFullName())
                .gender(customers.getGender().toString())
                .dateOfBirth(customers.getDateOfBirth())
                .phoneNumber(customers.getPhoneNumber())
                .userId(customers.getUser().getId())
                .build();
    }

    public ResponseEntity<ResponseData<CustomersResponse>> findById(int id) {
        var customers = customersRepository.findById(id).get();
        ResponseData<CustomersResponse> responseData = new ResponseData<>();
        if (customers == null) {
            responseData.getMessages().add("User Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(CustomersResponse.builder()
                .id(customers.getId())
                .fullName(customers.getFullName())
                .gender(customers.getGender().name())
                .dateOfBirth(customers.getDateOfBirth())
                .phoneNumber(customers.getPhoneNumber())
                .userId(customers.getUser().getId()).build());
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<CustomersResponse>> editCustomers(CustomersRequest customersRequest, int id,
            Errors errors) {
        Optional<Customers> cust = customersRepository.findById(id);
        ResponseData<CustomersResponse> responseData = new ResponseData<>();
        if (cust.isEmpty() || errors.hasErrors()
                || !(customersRequest.getGender().equals("FEMALE") || customersRequest.getGender().equals("MALE"))) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages().add(cust.isEmpty() ? "Customer Not Found" : null);
            responseData.getMessages()
                    .add(!(customersRequest.getGender().equals("FEMALE") || customersRequest.getGender().equals("MALE"))
                            ? "Gender Must Be MALE Or Female"
                            : null);

            responseData.getMessages().removeAll(Collections.singleton(null));
            return ResponseEntity.badRequest().body(responseData);
        }
        responseData.setStatus(true);
        cust.get().setFullName(customersRequest.getFullName());
        cust.get().setGender(Gender.valueOf(customersRequest.getGender()));
        cust.get().setDateOfBirth(LocalDate.parse(customersRequest.getDateOfBirth(), dtf));
        cust.get().setPhoneNumber(customersRequest.getPhoneNumber());
        customersRepository.save(cust.get());
        responseData.getMessages().add("Succes");
        responseData.setPayload(CustomersResponse.builder()
                .id(cust.get().getId())
                .fullName(cust.get().getFullName())
                .gender(cust.get().getGender().name())
                .dateOfBirth(cust.get().getDateOfBirth())
                .phoneNumber(cust.get().getPhoneNumber())
                .userId(cust.get().getUser().getId())
                .build());
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    public ResponseEntity<ResponseData<CustomersResponse>> deactivateCustomer(int id) {
        var cust = customersRepository.findById(id);
        ResponseData<CustomersResponse> responseData = new ResponseData<>();
        if (cust.isEmpty()) {
            responseData.getMessages().add("Customer Is Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        usersService.deactivateUser(cust.get().getUser().getId());
        responseData.setPayload(CustomersResponse.builder()
                .id(id)
                .fullName(cust.get().getFullName())
                .gender(cust.get().getGender().name())
                .dateOfBirth(cust.get().getDateOfBirth())
                .phoneNumber(cust.get().getPhoneNumber())
                .userId(cust.get().getUser().getId())
                .build());
        return ResponseEntity.ok().body(responseData);
    }
}
