package stepdefinitions;

import java.util.Map;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utilities.APIHelper;
import utilities.TestDataStore;

public class UpdateUserSteps {
	private Response response;
	 APIHelper apihelper = new APIHelper();
	
	@Given("Admin set the PUT request with the valid request body")
	public void admin_set_the_put_request_with_the_valid_request_body() {
		apihelper.validAuth();
	}

	@When("Admin sends PUT Request with request Body")
	public Response admin_sends_put_request_with_request_body() {
		//response = apihelper.sendRequestWithBody("valid update user", "valid","PUT");
		response = apihelper.updateRequestBody("valid update user", "valid");
		
		// Extract the first name from response and store it in TestDataStore so that we can use it in subsequent delete request
//				String userFirstName = response.jsonPath().getString("userFirstName");
//				TestDataStore.setUserFirstName(userFirstName);
//				System.out.println("Stored User First Name in TestDataStore: " + TestDataStore.getUserFirstName());
				String userId = response.jsonPath().getString("userId");
				TestDataStore.setUserId(userId);
				System.out.println("Stored User ID TestDataStore: " + TestDataStore.getUserId());

				return response;
	}

	@Then("The response status code should be {string} Status code for update user")
	public void the_response_status_code_should_be_status_code_for_update_user(String statusCode) {
		apihelper.validateStatusCode(statusCode, true);
		apihelper.schemaValidation();
		apihelper.validateResponseData(true);
	}
	
	@Given("Admin set the PUT request with the valid request body with no Auth")
	public void admin_set_the_put_request_with_the_valid_request_body_with_no_auth() {
		apihelper.noAuth();
	}

	@When("Admin sends HTTPS Request and update request Body with no or invalid auth")
	public Response admin_sends_https_request_and_update_request_body_with_no_or_invalid_auth() {
		response = apihelper.updateRequestBody("valid update user", "invalid");
		return response;
	}

	@Then("The response status code should be {string} Status code update")
	public void the_response_status_code_should_be_status_code_update(String statusCode) {
		apihelper.validateStatusCode(statusCode, true);
	}
	
	@When("Admin sends PUT Request with request Body to update only firstname")
	public Response admin_sends_put_request_with_request_body_to_update_only_firstname() {
		response = apihelper.updateRequestBody("update firstName", "valid");
		// Extract the first name from response and store it in TestDataStore so that we can use it in subsequent delete request
		String userFirstName = response.jsonPath().getString("userFirstName");
		TestDataStore.setUserFirstName(userFirstName);
		System.out.println("Stored User First Name in TestDataStore: " + TestDataStore.getUserFirstName());
		String userId = response.jsonPath().getString("userId");
		TestDataStore.setUserId(userId);
		System.out.println("Stored User ID TestDataStore: " + TestDataStore.getUserId());
		return response;
	}
	
	@When("Admin sends PUT Request with request Body to update only lastname")
	public Response admin_sends_put_request_with_request_body_to_update_only_lastname() {
		response = apihelper.updateRequestBody("update lastName", "valid");
		return response;
	}
	@When("Admin sends PUT Request with request Body to update only phone numer")
	public Response admin_sends_put_request_with_request_body_to_update_only_phone_numer() {
		response = apihelper.updateRequestBody("update contact number", "valid");
		return response;
	}
	
	@When("Admin sends PUT Request with request Body to update only email")
	public Response admin_sends_put_request_with_request_body_to_update_only_email() {
		response = apihelper.updateRequestBody("update email", "valid");
		return response;
	}
	
	@When("Admin sends PUT Request with request Body to update only plot")
	public Response admin_sends_put_request_with_request_body_to_update_only_plot() {
		response = apihelper.updateRequestBody("update plot number", "valid");
		return response;
	}
	
	@When("Admin sends PUT Request with request Body to update only street")
	public Response admin_sends_put_request_with_request_body_to_update_only_street() {
		response = apihelper.updateRequestBody("update street", "valid");
		return response;
	}

	@When("Admin sends PUT Request with request Body to update only state")
	public Response admin_sends_put_request_with_request_body_to_update_only_state() {
		response = apihelper.updateRequestBody("update state", "valid");
		return response;
	}

	@When("Admin sends PUT Request with request Body to update only country")
	public Response admin_sends_put_request_with_request_body_to_update_only_country() {
		response = apihelper.updateRequestBody("update Country", "valid");
		return response;
	}

	@When("Admin sends PUT Request with request Body to update only zipcode")
	public Response admin_sends_put_request_with_request_body_to_update_only_zipcode() {
		response = apihelper.updateRequestBody("update ZipCode", "valid");
		return response;
	}
	@When("Admin sends PUT Request with request Body with existing email")
	public Response admin_sends_put_request_with_request_body_with_existing_email() {
		response = apihelper.updateRequestBody("update with existing emailid", "valid");
		return response;
	}
	
	@When("Admin sends PUT Request with request Body with existing contact number")
	public Response admin_sends_put_request_with_request_body_with_existing_contact_number() {
		response = apihelper.updateRequestBody("update with existing contact number", "valid");
		return response;
	}
	
	@When("Admin sends PUT Request with request Body with first name numeric")
	public Response admin_sends_put_request_with_request_body_with_first_name_numeric() {
	    response = apihelper.updateRequestBody("update with numeric firstname", "valid");
		return response;
	}
	
	@When("Admin sends PUT Request with request Body with last name numeric")
	public Response admin_sends_put_request_with_request_body_with_last_name_numeric() {
		response = apihelper.updateRequestBody("update with numeric lastname", "valid");
		return response;
	}

	@When("Admin sends PUT Request with request Body with emailid numeric")
	public Response admin_sends_put_request_with_request_body_with_emailid_numeric() {
		response = apihelper.updateRequestBody("update with numeric emailid", "valid");
		return response;
	}
	
	@When("Admin sends PUT Request with request Body  with empty  firstname")
	public Response admin_sends_put_request_with_request_body_with_empty_firstname() {
		response = apihelper.updateRequestBody("update with empty firstname", "valid");
		return response;
	}

	@When("Admin sends PUT Request with request Body  with empty  lastname")
	public Response admin_sends_put_request_with_request_body_with_empty_lastname() {
		response = apihelper.updateRequestBody("update with empty lastname", "valid");
		return response;
	}
	///////here
	@When("Admin sends PUT Request with request Body  with empty  contact number")
	public Response admin_sends_put_request_with_request_body_with_empty_contact_number() {
		response = apihelper.updateRequestBody("update with empty contact number", "valid");
		return response;
	}

	@When("Admin sends PUT Request with request Body  with empty  emailid")
	public Response admin_sends_put_request_with_request_body_with_empty_emailid() {
	    response = apihelper.updateRequestBody("update with empty emailid", "valid");
		return response;
	}

	@When("Admin sends PUT Request with request Body  with invalid  emailid")
	public Response admin_sends_put_request_with_request_body_with_invalid_emailid() {
		response = apihelper.updateRequestBody("update with invalid emailid", "valid");
		return response;
	}

	@When("Admin sends PUT Request with request Body  with invalid   phone number")
	public Response admin_sends_put_request_with_request_body_with_invalid_phone_number() {
		response = apihelper.updateRequestBody("update with invalid phone number", "valid");
		return response;
	}

	@When("Admin sends PUT Request with request Body  with invalid  state")
	public Response admin_sends_put_request_with_request_body_with_invalid_state() {
		response = apihelper.updateRequestBody("update with invalid state", "valid");
		return response;
	}

	@When("Admin sends PUT Request with request Body  with invalid  country")
	public Response admin_sends_put_request_with_request_body_with_invalid_country() {
		response = apihelper.updateRequestBody("update with invalid country", "valid");
		return response;
	}

	@When("Admin sends PUT Request with request Body  with invalid  endpoint")
	public Response admin_sends_put_request_with_request_body_with_invalid_endpoint() {
		response = apihelper.updateRequestBody("update with invalid endpoint", "valid");
		return response;
	}



	








}
