package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import utilities.APIHelper;

import utilities.TestDataStore;
import io.restassured.specification.RequestSpecification;

public class PatchUserSteps {
	private Response response;
	private RequestSpecification request;
	APIHelper apihelper = new APIHelper();

	@Given("Admin set the PATCH request with the valid request body")
	public void admin_set_the_patch_request_with_the_valid_request_body() {
		request = apihelper.validAuth();
	}

	@When("Admin sends PATCH Request with request Body")
	public Response admin_sends_patch_request_with_request_body() {

		response = apihelper.sendRequest("patchfirstname", "valid", "PATCH");
		System.out.println("Raw Response: " + response.asString());
		TestDataStore.setUserId(response.jsonPath().getString("userId"));
		System.out.println("Stored User ID in TestDataStore: " + TestDataStore.getUserId());
		return response;
	}

	@Then("The response status code should be {string} Status code for patch update user")
	public void the_response_status_code_should_be_status_code_for_patch_update_user(String statusCode) {
		apihelper.setResponse(response);
		apihelper.validateStatusCode(statusCode, true);
		;
	}

	@When("Admin sends PATCH Request with request Body for user lastname")
	public Response admin_sends_patch_request_with_request_body_for_user_lastname() {

		response = apihelper.sendRequest("patchlastname", "valid", "PATCH");
		System.out.println("Raw Response: " + response.asString());
		TestDataStore.setUserId(response.jsonPath().getString("userId"));
		System.out.println("Stored User ID in TestDataStore: " + TestDataStore.getUserId());
		return response;
	}

	@When("Admin sends PATCH Request with request Body for user phone number")
	public Response admin_sends_patch_request_with_request_body_for_user_phone_number() {

		response = apihelper.sendRequest("patchphonenumber", "valid", "PATCH");
		System.out.println("Raw Response: " + response.asString());
		TestDataStore.setUserId(response.jsonPath().getString("userId"));
		System.out.println("Stored User ID in TestDataStore: " + TestDataStore.getUserId());
		return response;
	}

	@When("Admin sends PATCH Request with request Body for user email id")
	public Response admin_sends_patch_request_with_request_body_for_user_email_id() {

		response = apihelper.sendRequest("patchEmailId", "valid", "PATCH");
		System.out.println("Raw Response: " + response.asString());
		TestDataStore.setUserId(response.jsonPath().getString("userId"));
		System.out.println("Stored User ID in TestDataStore: " + TestDataStore.getUserId());
		return response;
	}

	@When("Admin sends PATCH Request with request Body for user plotNumber")
	public Response admin_sends_patch_request_with_request_body_for_user_plot_number() {

		response = apihelper.sendRequest("patchPlotNumber", "valid", "PATCH");
		System.out.println("Raw Response: " + response.asString());
		TestDataStore.setUserId(response.jsonPath().getString("userId"));
		System.out.println("Stored User ID in TestDataStore: " + TestDataStore.getUserId());
		return response;
	}

	@When("Admin sends PATCH Request with request Body for user street")
	public Response admin_sends_patch_request_with_request_body_for_user_street() {

		response = apihelper.sendRequest("patchStreet", "valid", "PATCH");
		System.out.println("Raw Response: " + response.asString());
		TestDataStore.setUserId(response.jsonPath().getString("userId"));
		System.out.println("Stored User ID in TestDataStore: " + TestDataStore.getUserId());
		return response;
	}

	@When("Admin sends PATCH Request with request Body for user state")
	public Response admin_sends_patch_request_with_request_body_for_user_state() {

		response = apihelper.sendRequest("patchState", "valid", "PATCH");
		System.out.println("Raw Response: " + response.asString());
		TestDataStore.setUserId(response.jsonPath().getString("userId"));
		System.out.println("Stored User ID in TestDataStore: " + TestDataStore.getUserId());
		return response;
	}

	@When("Admin sends PATCH Request with request Body for user country")
	public Response admin_sends_patch_request_with_request_body_for_user_country() {

		response = apihelper.sendRequest("patchCountry", "valid", "PATCH");
		System.out.println("Raw Response: " + response.asString());
		TestDataStore.setUserId(response.jsonPath().getString("userId"));
		System.out.println("Stored User ID in TestDataStore: " + TestDataStore.getUserId());
		return response;
	}

	@When("Admin sends PATCH Request with request Body for user zipCode")
	public Response admin_sends_patch_request_with_request_body_for_user_zip_code() {

		response = apihelper.sendRequest("patchZipCode", "valid", "PATCH");
		System.out.println("Raw Response: " + response.asString());
		TestDataStore.setUserId(response.jsonPath().getString("userId"));
		System.out.println("Stored User ID in TestDataStore: " + TestDataStore.getUserId());
		return response;
	}

	@When("Admin sends PATCH Request with request Body for user firstname and lastname")
	public Response admin_sends_patch_request_with_request_body_for_user_firstname_and_lastname() {

		response = apihelper.sendRequest("patchFirstLastName", "valid", "PATCH");
		System.out.println("Raw Response: " + response.asString());
		TestDataStore.setUserId(response.jsonPath().getString("userId"));
		System.out.println("Stored User ID in TestDataStore: " + TestDataStore.getUserId());
		return response;
	}

}
