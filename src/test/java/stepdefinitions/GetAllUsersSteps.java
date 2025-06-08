package stepdefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import utilities.APIHelper;
import utilities.JsonDataReader;

import java.util.List;
import java.util.Map;

public class GetAllUsersSteps {
	private RequestSpecification request;
    private Response response;
    private String username;
    private String password;
    private Map<String, Object> testData;
    
    APIHelper apihelper = new APIHelper();

    @Given("Admin has valid admin credentials")
    public void admin_has_valid_admin_credentials() {
        // Set your actual admin credentials here
       // this.username = "Numpy@gmail.com";
        //this.password = "userapi@2025";
        
//        request = given()
//            .auth()
//            .preemptive()
//            .basic(username, password)
//            .baseUri("https://userserviceapp-f5a54828541b.herokuapp.com");
        request = apihelper.validAuth();
    }

    @When("Admin sends a GET request to endpoint")
    public Response admin_sends_a_get_request_to_endpoint() {
        //response = request.when().get(endpoint);
    	testData = JsonDataReader.getScenarioData("valid all users");
		String endpoint = testData.get("endpoint").toString();
		response = request.when().get(endpoint);
		return response;
    }

    @Then("The response status code should be {string}")
    public void the_response_status_code_should_be(String statusCode) {
    	//int expectedStatus = Integer.parseInt(statusCode.trim());
        //response.then().statusCode(expectedStatus);
    	apihelper.setResponse(response);
    	apihelper.validateStatusCode(statusCode, false);
 
    }



}
