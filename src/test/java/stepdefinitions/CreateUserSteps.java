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
		//response = apihelper.sendRequestWithBody("valid create user", "valid","POST");
		System.out.println("Raw Response: " + response.asString());
		// Store the User ID and User First Name in TestData
				TestDataStore.setUserId(response.jsonPath().getString("userId"));
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
	
	@When("Admin sends POST Request and request Body with same email")
	public Response admin_sends_post_request_and_request_body_with_same_email() {
		
		response = apihelper.sendRequestWithBody("invalid same email id", "valid", "POST");
	    return response;
	}

	@Then("The response status code should be {string} Status  User already exist with same email")
	public void the_response_status_code_should_be_status_user_already_exist_with_same_email(String statusCode) {
		apihelper.setResponse(response);
		apihelper.validateStatusCode(statusCode, true);
		String actualMessage = response.jsonPath().getString("message");
		String expectedMessage = "User already exist with same email id";
		Assert.assertEquals(actualMessage, expectedMessage);
	}
	
	@Given("Admin set the POST request with the valid request body with no Auth")
	public void admin_set_the_post_request_with_the_valid_request_body_with_no_auth() {
		request = apihelper.noAuth();
	}

	@When("Admin sends POST Request and request Body with invalid auth")
	public Response admin_sends_post_request_and_request_body_with_invalid_auth() {
		response = apihelper.sendRequestWithBody("no auth", "none","POST");
		return response;
	}
	
	@When("Admin sends POST Request with request Body invalid firstname")
	public Response admin_sends_post_request_with_request_body_invalid_firstname() {
		response = apihelper.sendRequestWithBody("invalid firstName as numeric","valid", "POST");
		return response;
	}
	
	@When("Admin sends POST Request and request Body with last name as numeric")
	public Response admin_sends_post_request_and_request_body_with_last_name_as_numeric() {
		response = apihelper.sendRequestWithBody("invalid last name as numeric", "valid","POST");
		return response;
	}
	@When("Admin sends POST Request and request Body with invalid contact number")
	public Response admin_sends_post_request_and_request_body_with_invalid_contact_number() {
		response = apihelper.sendRequestWithBody("invalid contact number", "valid","POST");
		return response;
	}
	@When("Admin sends POST Request and request Body with invalid email format")
	public Response admin_sends_post_request_and_request_body_with_invalid_email_format() {
		response = apihelper.sendRequestWithBody("invalid email", "valid","POST");
		return response;
	}
	@When("Admin sends POST Request and request Body with invalid plot number")
	public void admin_sends_post_request_and_request_body_with_invalid_plot_number() {
		response = apihelper.sendRequestWithBody("invalid plot number", "valid","POST");
	}
	@When("Admin sends POST Request and request Body with invalid Street")
	public void admin_sends_post_request_and_request_body_with_invalid_street() {
		response = apihelper.sendRequestWithBody("invalid street as numeric", "valid","POST");
	}
	@When("Admin sends POST Request and request Body with invalid state")
	public void admin_sends_post_request_and_request_body_with_invalid_state() {
	    response = apihelper.sendRequestWithBody("invalid state", "valid","POST");
	}
	@When("Admin sends POST Request and request Body with invalid country")
	public void admin_sends_post_request_and_request_body_with_invalid_country() {
	    response = apihelper.sendRequestWithBody("invalid country", "valid","POST");
	}
	@When("Admin sends POST Request and request Body with invalid zipcode")
	public void admin_sends_post_request_and_request_body_with_invalid_zipcode() {
		 response = apihelper.sendRequestWithBody("invalid zipcode", "valid","POST");
	}








}
