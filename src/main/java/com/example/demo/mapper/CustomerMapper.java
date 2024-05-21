package com.example.demo.mapper;


import com.example.demo.model.CreateCustomer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shaype.openapi.example.model.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

/**
 * TODO: by default hardcoded the value, you have to map your customer object to create customer object.
 */
@Component
public class CustomerMapper {

    @Value("${shaype.skipKYC}")
    private Boolean skipKYC;

    public CreateHayCustomerRequestBody mapCreateCustomer(CreateCustomer createCustomer, UUID caseId) {
        return new CreateHayCustomerRequestBody()
                .customerDetails(mapCustomerDetails())
                .address(mapAddress())
                .customerTier(CreateHayCustomerRequestBody.CustomerTierEnum.FOUNDER)
                .idempotencyKey(UUID.randomUUID())
                .email("madaline.garett@gmail.com")
                .phoneNumber(new PhoneNumber().countryCodePrefix("+61").numberAfterPrefix("432123456"))
                .identityDocumentType(CreateHayCustomerRequestBody.IdentityDocumentTypeEnum.DRIVING_LICENSE)
                .identityDocumentNumber("1715576187")
                .identityDocumentRegion("NSW")
                .identityDocumentIssuingCountry("AUS")
                .externalCustomerId("ext123456")
                .identityVerificationCaseId(caseId) // In case skipKYC = true only
                .taxObligations(Collections.singletonList(mapTaxObligation()))
                .skipKyc(skipKYC);
    }

    private TaxObligation mapTaxObligation() {
        return new TaxObligation()
                .country("AUS")
                .taxIdNumber("1715576187");
    }

    private CustomerDetails mapCustomerDetails() {
        return new CustomerDetails()
                .dateOfBirth(LocalDate.of(1989, 4, 24))
                .gender("male")
                .title("Mr")
                .firstName("Madaline")
                .middleName("Garett")
                .lastName("Lebsack")
                .preferredName("Nina Block II");
    }

    private Address mapAddress() {
        return new Address()
                .administrativeRegion("NSW")
                .countryCodeIso("AUS")
                .line1("11 York st")
                .line2("")
                .postcode("2000")
                .townOrCity("Sydney");
    }
}