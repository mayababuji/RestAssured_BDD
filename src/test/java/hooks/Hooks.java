package hooks;

import io.cucumber.java.Before;
import io.restassured.RestAssured;


public class Hooks {
    
    @Before
    public void setup() {
        // Global setup if needed
  
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}