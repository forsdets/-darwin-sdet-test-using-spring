@darwin_api_tests
Feature: Validate the JSON Placeholder API's GET end point by email - Happy Path Scenarios

  Background:
    Given baseUri is https://jsonplaceholder.typicode.com

  @get_user_by_email
  Scenario: Validate the GET Request by retrieving an existing user by user email
    Given user sets Accept header to application/json
    When user makes GET request on the endpoint /users?email=Sincere@april.biz
    Then response code should be 200
    And response code should not be 401
    And response header Content-Type should exist
    And response body path $.[0].name should be Leanne Graham
    And response body path $.[0].email should be Sincere@april.biz
    And response body path $.[0].address should be {street=Kulas Light, suite=Apt. 556, city=Gwenborough, zipcode=92998-3874, geo={lat=-37.3159, lng=81.1496}}
    And response body path $.[0].phone should be 1-770-736-8031 x56442
    And response body path $.[0].website should be hildegard.org
    And response body path $.[0].id should be 1
