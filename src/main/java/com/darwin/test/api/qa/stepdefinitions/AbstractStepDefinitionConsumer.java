package com.darwin.test.api.qa.stepdefinitions;

import com.darwin.test.api.qa.scope.ScenarioScope;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@SuppressWarnings("unchecked")
abstract class AbstractStepDefinitionConsumer {

    Map<String, Object> body;
    private final RestTemplate template;
    private final HttpHeaders headers;
    private final Map<String, String> queryParams;
    private ResponseEntity<String> responseEntity;
    private final ObjectMapper objectMapper;
    private final ScenarioScope scenarioScope;
    String baseUri;

    AbstractStepDefinitionConsumer() {
        template = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        objectMapper = new ObjectMapper();
        scenarioScope = new ScenarioScope();
        headers = new HttpHeaders();
        queryParams = new HashMap<>();
    }

    void setHeader(String name, String value) {
        Assert.notNull(name, "Name is null");
        Assert.notNull(value, "Value is null");
        headers.set(name, value);
    }

    void addQueryParameters(Map<String, String> newParams) {
        Assert.notNull(newParams, "Param is null");
        Assert.isTrue(!newParams.isEmpty(), "Name is empty");
        queryParams.putAll(newParams);
    }

    void addHeaders(Map<String, String> newHeaders) {
        Assert.notNull(newHeaders, "Header is null");
        Assert.isTrue(!newHeaders.isEmpty(), "Header is empty");
        newHeaders.forEach((key, value) -> {

            List<String> headerValues = this.headers.get(key);
            if (headerValues == null) {
                headerValues = Collections.singletonList(value);
            } else {
                headerValues.add(value);
            }
            this.headers.put(key, headerValues);
        });
    }

    void setBody(String body) throws IOException {
        Assert.notNull(body, "Body is null");
        Assert.isTrue(!body.isEmpty(), "Body is empty");
        this.body = objectMapper.readValue(body, Map.class);
    }

    void request(String resource, HttpMethod method) {
        Assert.notNull(resource, "Resource is null");
        Assert.isTrue(!resource.isEmpty(), "Resource is empty");

        Assert.notNull(method, "Method is null");

        boolean writeMode = !HttpMethod.GET.equals(method)
                && !HttpMethod.DELETE.equals(method)
                && !HttpMethod.OPTIONS.equals(method)
                && !HttpMethod.HEAD.equals(method);

        if (!resource.contains("/")) {
            resource = "/" + resource;
        }

        HttpEntity httpEntity;

        if (writeMode) {
            Assert.notNull(body, "Body is null");
            httpEntity = new HttpEntity(body, headers);
        } else {
            httpEntity = new HttpEntity(headers);
        }
        responseEntity = this.template.exchange(baseUri + resource, method, httpEntity, String.class, queryParams);
        Assert.notNull(responseEntity, "ResponseEntity is null");
    }


    void checkStatus(int status, boolean isNot) {
        Assert.isTrue(status > 0, "Status should not be 0");
        Assert.isTrue(isNot == (responseEntity.getStatusCodeValue() != status), "Status code not matched");
    }


    List<String> checkHeaderExists(String headerName, boolean isNot) {
        Assert.notNull(headerName, "Header is null");
        Assert.isTrue(!headerName.isEmpty(), "Header is empty");
        Assert.notNull(responseEntity.getHeaders(), "Response header is empty");
        if (!isNot) {
            Assert.notNull(responseEntity.getHeaders().get(headerName), "Response header is null");
            return responseEntity.getHeaders().get(headerName);
        } else {
            Assert.isNull(responseEntity.getHeaders().get(headerName), "Response header is null");
            return null;
        }
    }

    void checkHeaderEqual(String headerName, String headerValue, boolean isNot) {
        Assert.notNull(headerName, "Header is nul");
        Assert.isTrue(!headerName.isEmpty(), "Header is empty");

        Assert.notNull(headerValue, "Header value is nul");
        Assert.isTrue(!headerValue.isEmpty(), "Header value is empty");

        Assert.notNull(responseEntity.getHeaders());

        if (!isNot) {
            Assert.isTrue(Objects.requireNonNull(responseEntity.getHeaders().get(headerName)).contains(headerValue), "Header value is not available");
        } else {
            Assert.isTrue(!Objects.requireNonNull(responseEntity.getHeaders().get(headerName)).contains(headerValue), "Header value is not available");
        }
    }

    void checkJsonBody() throws IOException {
        String body = responseEntity.getBody();
        Assert.notNull(body, "Body is null");
        Assert.isTrue(!body.isEmpty(), "Body is empty");

        // Check body json structure is valid
        objectMapper.readValue(body, Map.class);
    }

    void checkBodyContains(String bodyValue) {
        Assert.notNull(bodyValue, "Header is null");
        Assert.isTrue(!bodyValue.isEmpty(), "Header is empty");

        Assert.isTrue(responseEntity.getBody().contains(bodyValue), "Body is not available");
    }

    Object checkJsonPathExists(String jsonPath) {
        return getJsonPath(jsonPath);
    }

    void checkJsonPath(String jsonPath, String jsonValue, boolean isNot) {
        Object pathValue = checkJsonPathExists(jsonPath);
        Assert.isTrue(!String.valueOf(pathValue).isEmpty(), "Path value is empty");

        if (!isNot) {
            Assert.isTrue(String.valueOf(pathValue).equals(jsonValue), "json path value and json values are not equal");
        } else {
            Assert.isTrue(!String.valueOf(pathValue).equals(jsonValue), "json path  is not matched");
        }
    }

    void checkJsonPathIsArray(String jsonPath, int length) {
        Object pathValue = getJsonPath(jsonPath);
        Assert.isTrue(pathValue instanceof Collection, " Path value is not available");
        if (length != -1) {
            Assert.isTrue(((Collection) pathValue).size() == length, "Path value is not matched");
        }
    }

    void checkJsonPathArraySize(String jsonPath, int length) {
        Object pathValue = getJsonPath(jsonPath);
        if (length != -1) {
            Assert.isTrue(((LinkedHashMap) pathValue).size() == length, "Json path array size is not matched");
        }
    }

    void storeHeader(String headerName, String headerAlias) {

        Assert.notNull(headerName, "Header is null");
        Assert.isTrue(!headerName.isEmpty(), "Header is empty");

        Assert.notNull(headerAlias, "Header Alias is null");
        Assert.isTrue(!headerAlias.isEmpty(), "Header Alias is empty");

        List<String> headerValues = checkHeaderExists(headerName, false);
        Assert.notNull(headerValues, "Header value is null");
        Assert.isTrue(!headerValues.isEmpty(), "Header value is empty");

        scenarioScope.getHeaders().put(headerAlias, headerValues);
    }

    void storeJsonPath(String jsonPath, String jsonPathAlias) {
        Assert.notNull(jsonPath, "Json Path is null");
        Assert.isTrue(!jsonPath.isEmpty(), "Json Path is empty");

        Assert.notNull(jsonPathAlias, "jsonPathAlias is null");
        Assert.isTrue(!jsonPathAlias.isEmpty(), "jsonPathAlias is empty");

        Object pathValue = getJsonPath(jsonPath);
        scenarioScope.getJsonPaths().put(jsonPathAlias, pathValue);
    }

    void checkScenarioVariable(String property, String value) {
        Assert.isTrue(scenarioScope.checkProperty(property, value), "Property or Value is not available");
    }

    private ReadContext getBodyDocument() {
        ReadContext ctx = JsonPath.parse(responseEntity.getBody());
        Assert.notNull(ctx, "Context should not be null");

        return ctx;
    }

    private Object getJsonPath(String jsonPath) {

        Assert.notNull(jsonPath, "Json Path is null");
        Assert.isTrue(!jsonPath.isEmpty(), "Json Path is empty");

        ReadContext ctx = getBodyDocument();
        Object pathValue = ctx.read(jsonPath);

        Assert.notNull(pathValue, "Json pathValue is null");

        return pathValue;
    }
}
