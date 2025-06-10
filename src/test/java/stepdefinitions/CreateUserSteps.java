package stepdefinitions;

import java.util.Map;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.APIHelper;
import utilities.ConfigReader;
import utilities.JsonReader;
import utilities.TestDataStore;

public class CreateUserSteps {
	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;
	 APIHelper apihelper = new APIHelper();
	@Given("Admin set the POST request with the valid request body")
	public void admin_set_the_post_request_with_the_valid_request_body() {
		request = apihelper.validAuth();
	}

	@When("Admin sends a POST request with body to endpoint")
	public Response admin_sends_a_post_request_with_body_to_endpoint() {
		response = apihelper.createRequestBody("valid create user", "valid");
		// Store the User ID and User First Name in TestData
				TestDataStore.setUserId(response.jsonPath().getString("user_id"));
				System.out.println("Stored User ID in TestDataStore: " + TestDataStore.getUserId());
				return response;
	}
	@Then("The response status code should be {string} for create user")
	public void the_response_status_code_should_be_for_create_user(String statusCode) {
		apihelper.setResponse(response);
		apihelper.schemaValidation();
		apihelper.validateStatusCode(statusCode, false);
		apihelper.validateResponseData(false);
	}
	@When("Admin sends a POST request with body to endpoint with mandatory fields only")
	public Response admin_sends_a_post_request_with_body_to_endpoint_with_mandatory_fields_only() {
		response= apihelper.createUserWithMandatoryFields("valid only mandatory");
		return response;
	}
	@Then("The response status code should be  {string} for create user mandatory")
	public void the_response_status_code_should_be_for_create_user_mandatory(String statusCode) {
		apihelper.setResponse(response);
		apihelper.validateStatusCode(statusCode, true);
	}
	@When("Admin sends HTTPS Request and request Body with invalid endpoint")
	public Response admin_sends_https_request_and_request_body_with_invalid_endpoint() {
		response = apihelper.createRequestBody("create user invalid endpoint", "valid");
		return response;
	}

	@Then("The response status code should be {string} Status")
	public void the_response_status_code_should_be_status(String statusCode) {
		apihelper.setResponse(response);
		apihelper.validateStatusCode(statusCode, true);
	}
	
	@Given("Admin set the POST request with the invalid contentType")
	public void admin_set_the_post_request_with_the_invalid_content_type() {
		request = RestAssured.given()
		        .baseUri(ConfigReader.getProperty("baseURl"))
		        .auth().preemptive().basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
		        .header("Accept", "application/json")
		        .header("Content-Type", "text/plain"); 
	}

	@When("Admin sends POST Request and request Body with invalid content type")
	public Response admin_sends_post_request_and_request_body_with_invalid_content_type() {
		testData = JsonReader.getScenarioData("valid create user");
		request.body("Invalid request format").log().all();
		String endpoint = testData.get("endpoint").toString();
		response = request.when().post(endpoint);
		return response;
	}
	
	@When("Admin sends GET Request and request Body with invalid request type")
	public Response admin_sends_get_request_and_request_body_with_invalid_request_type(){
		response = apihelper.sendRequestWithBody("valid create user", "valid", "GET");
	    return response;
	}
	
	@When("Admin sends POST Request and request Body with same contact number")
	public Response admin_sends_post_request_and_request_body_with_same_contact_number() {
		response = apihelper.sendRequestWithBody("invalid same contact number", "valid", "POST");
	    return response;
	}

	@Then("The response status code should bes {string} status User already exist with same contact number")
	public void the_response_status_code_should_bes_status_user_already_exist_with_same_contact_number(String statusCode) {
		apihelper.setResponse(response);
		apihelper.validateStatusCode(statusCode, true);
		String actualMessage = response.jsonPath().getString("message");
		String expectedMessage = "User already exist with same contact number";
		Assert.assertEquals(actualMessage, expectedMessage);
	}






}
