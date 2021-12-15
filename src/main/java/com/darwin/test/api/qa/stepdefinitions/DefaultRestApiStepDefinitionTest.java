package com.darwin.test.api.qa.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;

/**
 * Default step definition for consuming a rest api
 * {@link ContextConfiguration} ContextConfiguration and {@link SpringBootTest} @SpringBootTest annotation
 * are mandatory to be able to run cucumber unit test on spring rest controllers
 */
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DefaultRestApiStepDefinitionTest extends AbstractStepDefinitionConsumer {

    @Given("^baseUri is (.*)$")
    public void baseUri(String uri) {
        Assert.notNull(uri, "URI is not available");
        Assert.isTrue(!uri.isEmpty(), "URI is empty");
        baseUri = uri;
    }

    @Given("^user sets body to (.*)$")
    public void setBodyTo(String body) throws IOException {
        this.setBody(body);
    }

    @Given("^user sets (.*) header to (.*)$")
    public void header(String headerName, String headerValue) {
        this.setHeader(headerName, headerValue);
    }

    @Given("^I set query parameters to:$")
    public void queryParameters(Map<String, String> parameters) {
        this.addQueryParameters(parameters);
    }

    @Given("^I set headers to:$")
    public void headers(Map<String, String> parameters) {
        this.addHeaders(parameters);
    }

    @When("^user makes GET request on the endpoint (.*)$")
    public void get(String resource) {
        this.request(resource, HttpMethod.GET);
    }

    @When("^user makes POST request on the endpoint (.*)$")
    public void post(String resource) {
        this.request(resource, HttpMethod.POST);
    }

    @When("^user makes PUT request on the endpoint (.*)$")
    public void put(String resource) {
        this.request(resource, HttpMethod.PUT);
    }

    @When("^user makes DELETE request on the endpoint (.*)$")
    public void delete(String resource) {
        this.request(resource, HttpMethod.DELETE);
    }

    @When("^user makes PATCH request on the endpoint (.*)$")
    public void patch(String resource) {
        this.request(resource, HttpMethod.PATCH);
    }

    @When("^I request OPTIONS for $resource$")
    public void options(String resource) {
        this.request(resource, HttpMethod.OPTIONS);
    }

    @When("^I request HEAD for $resource$")
    public void head(String resource) {
        this.request(resource, HttpMethod.HEAD);
    }

    @Then("^response code should be (\\d+)$")
    public void responseCode(Integer status) {
        this.checkStatus(status, false);
    }

    @Then("^response code should not be (\\d+)$")
    public void notResponseCode(Integer status) {
        this.checkStatus(status, true);
    }

    @Then("^response header (.*) should exist$")
    public void headerExists(String headerName) {
        this.checkHeaderExists(headerName, false);
    }

    @Then("^response header (.*) should not exist$")
    public void headerNotExists(String headerName) {
        this.checkHeaderExists(headerName, true);
    }

    @Then("^response header (.*) should be (.*)$")
    public void headerEqual(String headerName, String headerValue) {
        this.checkHeaderEqual(headerName, headerValue, false);
    }

    @Then("^response header (.*) should not be (.*)$")
    public void headerNotEqual(String headerName, String headerValue) {
        this.checkHeaderEqual(headerName, headerValue, true);
    }

    @Then("^response body should be valid json$")
    public void bodyIsValid() throws IOException {
        this.checkJsonBody();
    }

    @Then("^response body should contain (.*)$")
    public void bodyContains(String bodyValue) {
        this.checkBodyContains(bodyValue);
    }

    @Then("^response body path (.*) should exists$")
    public void bodyPathExists(String jsonPath) {
        this.checkJsonPathExists(jsonPath);
    }

    @Then("^response body path (.*) should be (.*)$")
    public void bodyPathEqual(String jsonPath, String value) {
        this.checkJsonPath(jsonPath, value, false);
    }

    @Then("^response body path (.*) should not be (.*)$")
    public void bodyPathNotEqual(String jsonPath, String value) {
        this.checkJsonPath(jsonPath, value, true);
    }

    @Then("^response body is typed as array for path (.*)$")
    public void bodyPathIsArray(String jsonPath) {
        this.checkJsonPathIsArray(jsonPath, -1);
    }

    @Then("^response body is typed as array using path (.*) with length (\\d+)$")
    public void bodyPathIsArrayWithLength(String jsonPath, int length) {
        this.checkJsonPathArraySize(jsonPath, length);
    }

    @Then("^I store the value of response header (.*) as (.*) in scenario scope$")
    public void storeResponseHeader(String headerName, String headerAlias) {
        this.storeHeader(headerName, headerAlias);
    }

    @Then("^I store the value of body path (.*) as (.*) in scenario scope$")
    public void storeResponseJsonPath(String jsonPath, String jsonPathAlias) {
        this.storeJsonPath(jsonPath, jsonPathAlias);
    }

    @Then("^value of scenario variable (.*) should be (.*)$")
    public void scenarioVariableIsValid(String property, String value) {
        this.checkScenarioVariable(property, value);
    }
}
