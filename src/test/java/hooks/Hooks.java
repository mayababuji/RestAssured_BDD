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
//    @Before("@stateChanging") //# heroapp 503 issue work around  fix
//    public void warmUpApp() {
//        try {
//            System.out.println("Warming up Heroku app before state-changing test...");
//            RestAssured.get("https://userserviceapp-f5a54828541b.herokuapp.com/uap/users/health"); // or any safe GET endpoint
//            Thread.sleep(5000); // Wait for dyno to fully wake up
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
}