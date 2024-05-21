package com.example.demo.service;

import com.example.demo.mapper.CustomerMapper;
import com.example.demo.model.CreateCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import shaype.openapi.example.api.AccountsApiApi;
import shaype.openapi.example.api.CustomersApiApi;
import shaype.openapi.example.model.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerService {

    private final CustomersApiApi customersApi;
    private final CustomerMapper customerMapper;

    public HayCustomer createCustomer(CreateCustomer createCustomer, UUID caseId) {
        //TODO: Perform your custom validation
        //TODO: Perform your business logic before customer creation
        try {
            CreateHayCustomerRequestBody createCustomerRequest = customerMapper.mapCreateCustomer(createCustomer, caseId);
            return customersApi.createHayCustomer(createCustomerRequest);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        //TODO: Perform any post action
        return null;
    }

    public void activateCustomer(UUID customerId) {
        ChangeHayCustomerStatusRequestBody statusRequest = new ChangeHayCustomerStatusRequestBody()
                .newStatus(ChangeHayCustomerStatusRequestBody.NewStatusEnum.ACTIVE);
        customersApi.changeHayCustomerStatus(customerId, statusRequest);
    }

    public List<HayCustomer> getAllCustomer() {
        return customersApi.getAllCustomers(0, 10);
    }

}
