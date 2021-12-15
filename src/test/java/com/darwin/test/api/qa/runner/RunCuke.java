package com.darwin.test.api.qa.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty:target/cucumber-pretty.txt",
                "html:target/cucumber-html-report",
                "json:target/cucumber.json",
                "junit:target/cucumber-result.xml",
                "usage:usage/cucumber-usage.json"},
        features = "src/test/resources/features",
        glue = {"com.darwin.test.api.qa.stepdefinitions"},
        tags = "@darwin_api_tests")
public class RunCuke {
}
