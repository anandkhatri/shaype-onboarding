package com.example.demo.service;

import com.example.demo.exception.ShaypeApiException;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.model.CreateCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import shaype.openapi.example.api.CustomersApiApi;
import shaype.openapi.example.model.ChangeHayCustomerStatusRequestBody;
import shaype.openapi.example.model.CreateHayCustomerRequestBody;
import shaype.openapi.example.model.HayCustomer;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerService {

    private final CustomersApiApi customersApi;
    private final CustomerMapper customerMapper;

    public HayCustomer createCustomer(CreateCustomer createCustomer) {
        //TODO: Perform your custom validation
        //TODO: Perform your business logic before customer creation
        HayCustomer customer;
        try {
            customer = customersApi.createHayCustomer(createCustomer);
        } catch (RestClientResponseException e) {
            log.error("Error occurred while creating customer: {}", e.getMessage());
            throw new ShaypeApiException(e);
        }
        log.info("Customer created on Shaype platform successfully.");
        //TODO: Perform any post action
        return customer;
    }

    public void activateCustomer(UUID customerId) {
        ChangeHayCustomerStatusRequestBody statusRequest = new ChangeHayCustomerStatusRequestBody()
                .newStatus(ChangeHayCustomerStatusRequestBody.NewStatusEnum.ACTIVE);
        try {
            customersApi.changeHayCustomerStatus(customerId, statusRequest);
        } catch (RestClientResponseException e) {
            log.error("Error occurred while updating customer status to ACTIVE: {}", e.getMessage());
            throw new ShaypeApiException(e);
        }
        log.info("Customer {} Activated on Shaype platform.", customerId);
    }

    public List<HayCustomer> getAllCustomer() {
        return customersApi.getAllCustomers(0, 10);
    }

}
