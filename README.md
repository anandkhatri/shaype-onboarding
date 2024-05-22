# Onboarding on Shaype platform
This service is used to onboard customer onto the Shaype platform.


## API Endpoints
Below are the APIs exposed by the onboarding service.

| HTTP Method | Endpoint   | Description                                                                                                                                              |
|-------------|------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| POST        | `/onboard` | The API will create a customer, update the customer's status to ACTIVE, create an account for that customer, and update the account's risk level to LOW. |

## Configuration

| Property           | Default value  | Description                                               |
|--------------------|----------------|-----------------------------------------------------------|
| shaype.baseUrl     | localhost:9999 | Shaype environment Base URL                               |
| shaype.accessToken | {dummy value}  | Security token to access Shaype platform                  |
| skipKYC            | true           | Flag that indicate that are you using Shaype KYC feature? |

## Prerequisite 
* Replace `shaype.baseUrl` property value with the Shaype platform URL
* Replace `shaype.accessToken` property value with the access token that you received from Shaype team.
* Define `skipKYC` property only if you are using Shaype KYC
* Update the `mapCreateCustomer` method under `CustomerMapper.java` class with customer actual values (currently values are mock). 

## How to Run?
* Build the service with `$ gradle clean build`
* Run the application by `$ gradle bootrun`
* If you want to run service with mock, please refer `shaype-mock-server` project. 




