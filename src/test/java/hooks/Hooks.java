package hooks;

import io.cucumber.java.Before;
import io.restassured.RestAssured;//This is a class from the Rest Assured library used for testing REST APIs.

//A public class named Hooks which contains Cucumber lifecycle methods.
public class Hooks {
    //This annotation from Cucumber tells the framework to run the method before each scenario.
    @Before
    public void setup() {
        // Global setup if needed
  //This is a REST Assured configuration that enables detailed logging The complete HTTP request (method, URL, headers, body) 
    	//The complete HTTP response (status code, headers, body) But only when a test fails
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}