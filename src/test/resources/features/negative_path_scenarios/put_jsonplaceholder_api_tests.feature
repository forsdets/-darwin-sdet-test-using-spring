@darwin_api_tests @put
Feature: Validate the JSON Placeholder API's PUT request - Negative Scenarios

  Background:
    Given baseUri is https://jsonplaceholder.typicode.com

  @put_request_with_missing_mandatory_field
  Scenario: Validate the PUT Request by updating an existing user data with missing mandatory fields
    Given user sets Accept header to application/json
    And user sets body to {"name": "testName"}
    When user makes PUT request on the endpoint /users
    Then response code should be 400
#   Currently the test API is not giving any negative response for missing mandatory fields

  @put_request_with_missing_mandatory_value
  Scenario: Validate the PUT Request by updating an existing user data with missing mandatory value
    Given user sets Accept header to application/json
    And user sets body to {"name": , "email": "testemail@mail.com", "address": "testAddress", "phone": "7444466665", "website": "website.com"}
    When user makes PUT request on the endpoint /users
    Then response code should be 400
#   Currently the test API is not giving any negative response for missing mandatory fields


  @put_request_with_invalid_mandatory_header_values
  Scenario Outline: Validate the PUT Request by updating an existing user data with invalid header fields
    Given user sets <header_field> header to <header_value>
    And user sets body to {"name": "testName", "email": "testemail@mail.com", "address": "testAddress", "phone": "7444466665", "website": "website.com"}
    When user makes PUT request on the endpoint /users
    Then response code should be <response_code>
    Examples:
      | header_field | header_value     | response_code |
      | Accept       | wrongHeaderValue | 406           |
      | Accept       | $%               | 406           |
      | Content-Type | text/cmd         | 415           |
#   Currently the test API is not giving any negative response for missing mandatory fields

