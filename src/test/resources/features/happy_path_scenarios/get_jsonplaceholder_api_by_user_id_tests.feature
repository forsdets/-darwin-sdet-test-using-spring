@darwin_api_tests @get @happy_path
Feature: Validate the JSON Placeholder API's GET end point by id - Happy Path Scenarios

  Background:
    Given baseUri is available

  @get_user_by_id
  Scenario: Validate the GET Request by retrieving an existing by user id
    Given user sets Accept header to application/json
    When user makes GET request on the endpoint /users/1
    Then response code should be 200
    And response code should not be 401
    And response header Content-Type should exist
    And response body should be valid json
    And response body path $.name should be Leanne Graham
    And response body path $.email should be Sincere@april.biz
    And response body path $.address should be {street=Kulas Light, suite=Apt. 556, city=Gwenborough, zipcode=92998-3874, geo={lat=-37.3159, lng=81.1496}}
    And response body path $.phone should be 1-770-736-8031 x56442
    And response body path $.website should be hildegard.org
    And response body path $.id should be 1
