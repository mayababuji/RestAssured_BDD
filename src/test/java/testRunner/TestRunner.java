package testRunner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepdefinitions", "hooks"},
    //tags = "@run",
    plugin = {"pretty", "html:target/cucumber-reports.html",
    		"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
    		"json:target/cucumber-reports/cucumber.json",
    		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
    		"com.aventstack.chaintest.plugins.ChainTestCucumberListener:"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
	
}