# jsonplaceholder-api-tests

## API Scenario Structure

- The API test Feature files are available in location:

```> src/test/resources/features```

- Here I have used Gradle as a build tool with Java-BDD Cucumber framework along with these mainly I have used Spring-boot for API's requests and responses
- As this is a Java based QA Repository to validate the API functionality of jsonplaceholder-api
- The scenarios have been divided into 2 parts, one is Happy path and another one is Negative path scenarios for all the requests mentioned in the SDET question
- The feature level common tag '@darwin_api_tests' which can help to run all the scenarios (both happy and negative path)
- The feature level tag '@get' can help to run all the 'get' request scenarios(both happy and negative path), similarly for all other requests 
- The feature level tag '@happy_path' can help to run happy path scenarios alone for all the requests similarly @negative_path can help to run negative scenarios

#### Run the API Tests through gradle task or Pipeline or standalone Jenkins job

## Run the API Tests

#### Run the API Tests through Cucumber Runner class

Steps to follow:

- Import gradle Dependencies

- Check the BDD Feature files are available in:

```> src/test/resources/features```

- In IDE Go to **src> test> java> runner> RunCuke**

- Right click and Run by setting the below runtime parameter

```bash
-Dservice.uri=https://jsonplaceholder.typicode.com
```
- The Run time parameter needs to be updated based on which environment we are running if we are running the scenarios locally.
- If we integrated this with the CI/CD pipeline then the environment parameter will be passed by the Jenkins or Teamcity

- The 'tags' parameter will give the option to mention the feature or scenario tag to run

#### Run the API Tests through gradle task or Pipeline or standalone Jenkins job

From IDE's terminal or gitbash or  we can use the below parameters to run API scenarios
Syntax:
```bash
./gradle clean cucumber -Dservice.uri=https://jsonplaceholder.typicode.com
```
Example:
```bash
./gradle clean cucumber -Dservice.uri=https://jsonplaceholder.typicode.com
```

Similarly, we can run for other environments by configuring/providing the above 2 parameters in runtime on Pipeline or standalone Jenkins Job.

These tests can be integrated easily with CI/CD Pipeline by creating a new test stage, and it can be executed whenever we are making a change in this API.


*****************************************
Note:
*****************************************
- The APIs are not giving the expected results for negative scenarios but the same can be necessary for real-time APIs.
- We can add/do the schema(request, response) validation against the swagger setup, I tried but received some un-related issues. 
  So it's worth to add this kind of validation in Contract testing or unit level testing.

Example code what I tried,
```bash
  // GIVEN
  RestAssured.given().baseUri("https://jsonplaceholder.typicode.com/auth")
  .body(jsonStringPayload)
  // WHEN
  .when()
  .post()
  // THEN
  .then()
  .assertThat()
  .statusCode(200)
  .body("token", Matchers.notNullValue())
  .body(JsonSchemaValidator.matchesJsonSchema("SchemaFilePath")));
  }
  ```
*****************************************