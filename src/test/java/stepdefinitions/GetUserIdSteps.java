package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import utilities.APIHelper;
import utilities.ConfigReader;
import utilities.JsonReader;
import utilities.TestDataStore;

public class GetUserIdSteps {
	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;
	 APIHelper apihelper = new APIHelper();
	@Given("Admin sets get request with valid credentials to get userid")
	public void admin_sets_get_request_with_valid_credentials_to_get_userid() {
		request = apihelper.validAuth();
	}
	@When("Admin sends the GET userID request")
	public Response admin_sends_the_get_user_id_request() {
		testData = JsonReader.getScenarioData("valid userid");
		String userId = TestDataStore.getUserId();
		System.out.println("Retrieved User ID: " + userId);
		String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
		response = request.when().get(endpoint);
		return response;
	}
	@Then("The response status code should be {string} with valid user get userid")
	public void the_response_status_code_should_be_with_valid_user_get_userid(String statusCode) {
		apihelper.setResponse(response);
		apihelper.validateStatusCode(statusCode, true);
	}

@When("Admin sends GET Request with invalid userID")
public Response admin_sends_get_request_with_invalid_user_id() {
	testData = JsonReader.getScenarioData("invalid userid");
	String endpoint = testData.get("endpoint").toString();
	response = request.when().get(endpoint);
	return response;
}


@Then("The response status code should be {string} Status code with valid user details")
public void the_response_status_code_should_be_status_code_with_valid_user_details(String statusCode) {
	apihelper.setResponse(response);
	apihelper.validateStatusCode(statusCode, true);
}


@When("Admin sends POST Request with invalid requestType")
public Response admin_sends_post_request_with_invalid_request_type() {
	testData = JsonReader.getScenarioData("invalid request type");
	String userId = TestDataStore.getUserId();
	System.out.println("Retrieved User ID: " + userId);
	String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
	response = request.when().post(endpoint);
	return response;
}
@Given("Admin sets the get request by user id with invalid basic auth")
public void admin_sets_the_get_request_by_user_id_with_invalid_basic_auth() {
	testData = JsonReader.getScenarioData("get all users invalid Basic Auth");
	String username = testData.get("username").toString();
	String password = testData.get("password").toString();
	System.out.println("The username is "+username);
	System.out.println("The password is "+password);
	request = apihelper.invalidAuth(username, password);
}



@Given("Admin sets the get request by user id with No Auth")
public void admin_sets_the_get_request_by_user_id_with_no_auth() {
	request = apihelper.noAuth();
}
}
