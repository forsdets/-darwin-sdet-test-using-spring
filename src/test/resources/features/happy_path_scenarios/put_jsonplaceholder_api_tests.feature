@darwin_api_tests @put @happy_path
Feature: Validate the JSON Placeholder API's PUT end point - Happy Path Scenarios

  Background:
    Given baseUri is available

  @update_an_existing_user_data_with_valid_put_body
  Scenario: Validate the PUT Request by updating an existing user data
    Given user sets Accept header to application/json
    And user sets body to {"name": "testName", "email": "testemail@mail.com", "address": "testAddress", "phone": "7444466665", "website": "website.com"}
    When user makes PUT request on the endpoint /users/1
    Then response code should be 200
    And response code should not be 401
    And response header Content-Type should exist
    And response body should be valid json
    And response body path $.name should be testName
    And response body path $.email should be testemail@mail.com
    And response body path $.address should be testAddress
    And response body path $.phone should be 7444466665
    And response body path $.website should be website.com
    And response body path $.id should be 1
