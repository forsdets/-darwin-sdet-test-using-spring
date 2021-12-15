@darwin_api_tests @post @negative_path
Feature: Validate the JSON Placeholder API's POST request - Negative Scenarios

  Background:
    Given baseUri is available

  @post_request_with_missing_mandatory_field
  Scenario: Validate the new user creation by using POST Request with missing mandatory fields
    Given user sets Accept header to application/json
    And user sets body to {"name": "testName"}
    When user makes POST request on the endpoint /users
    Then response code should be 400
#   Currently the test API is not giving any negative response to check the negative scenarios

  @post_request_with_missing_mandatory_value
  Scenario: Validate the new user creation by using POST Request with missing mandatory value
    Given user sets Accept header to application/json
    And user sets body to {"name": , "email": "testemail@mail.com", "address": "testAddress", "phone": "7444466665", "website": "website.com"}
    When user makes POST request on the endpoint /users
    Then response code should be 400
#   Currently the test API is not giving any negative response to check the negative scenarios


  @post_request_with_invalid_mandatory_header_values
  Scenario Outline: Validate the new user creation by using POST Request with invalid header fields
    Given user sets <header_field> header to <header_value>
    And user sets body to {"name": "testName", "email": "testemail@mail.com", "address": "testAddress", "phone": "7444466665", "website": "website.com"}
    When user makes POST request on the endpoint /users
    Then response code should be <response_code>
    Examples:
      | header_field | header_value     | response_code |
      | Accept       | wrongHeaderValue | 406           |
      | Accept       | $%%%%%           | 406           |
      | Content-Type | text/cmd         | 415           |
      | JWT-Token    | invalidToken     | 403           |
#   Currently the test API is not giving any negative response to check the negative scenarios

  @get_request_with_invalid_endpoint
  Scenario: Validate the POST Request by updating an existing user data with missing mandatory value
    Given user sets Accept header to application/json
    And user sets body to {"name": , "email": "testemail@mail.com", "address": "testAddress", "phone": "7444466665", "website": "website.com"}
    When user makes POST request on the endpoint /users////
    Then response code should be 404
#   Currently the test API is not giving any negative response to check the negative scenarios

