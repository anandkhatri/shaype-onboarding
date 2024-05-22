package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shaype.openapi.example.api.AccountsApiApi;
import shaype.openapi.example.api.CustomersApiApi;
import shaype.openapi.example.api.KycApiApi;
import shaype.openapi.example.invoker.ApiClient;
import shaype.openapi.example.invoker.auth.HttpBearerAuth;

@Component
@Configuration
public class ShaypeApiConfig {

    @Value("${shaype.baseUrl}")
    private String shaypeApiBaseUrl;

    @Value("${shaype.accessToken}")
    private String shaypeApiAccessToken;

    @Bean
    public CustomersApiApi customerAPI(){
        return new CustomersApiApi(apiClient());
    }

    @Bean
    public AccountsApiApi accountAPI(){
        return new AccountsApiApi(apiClient());
    }

    @Bean
    public KycApiApi kycAPI(){
        return new KycApiApi(apiClient());
    }

    private RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    private ApiClient apiClient(){
        ApiClient apiClient = new ApiClient(restTemplate());
        apiClient.setBasePath(shaypeApiBaseUrl);
        // Configure HTTP bearer authorization: bearer-key
        HttpBearerAuth bearerAuth = (HttpBearerAuth) apiClient.getAuthentication("bearer-key");
        bearerAuth.setBearerToken(shaypeApiAccessToken);
        return apiClient;
    }
}
