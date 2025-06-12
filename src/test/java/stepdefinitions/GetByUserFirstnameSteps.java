package stepdefinitions;

import java.util.Map;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.APIHelper;
import utilities.JsonReader;
import utilities.TestDataStore;

public class GetByUserFirstnameSteps {
	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;
	 APIHelper apihelper = new APIHelper();
	 
	 @Given("Admin set the GET request by user firstName")
	 public void admin_set_the_get_request_by_user_first_name() {
		 request = apihelper.validAuth();
	 }

	 @When("Admin sends the GET  request by User FirstName")
	 public Response admin_sends_the_get_request_by_user_first_name() {
		 testData = JsonReader.getScenarioData("valid userFirstName");
			String user_first_name = TestDataStore.getUserFirstName();
			System.out.println("Retrieved User FirstName to send a GET by user first name request: " + user_first_name);
			String endpoint = testData.get("endpoint").toString().replace("{{user_first_name}}", user_first_name);
			response = request.when().get(endpoint);
			return response;
	 }

	 @Then("The response status code should be  {string} Status code for UserFirstName")
	 public void the_response_status_code_should_be_status_code_for_user_first_name(String statusCode) {
		 apihelper.setResponse(response);
		 apihelper.validateStatusCode(statusCode, true);
	 }

	 @When("Admin sends GET request with invalid userfirstName")
	 public Response admin_sends_get_request_with_invalid_userfirst_name() {
		 testData = JsonReader.getScenarioData("invalid userfirstname");
			String endpoint = testData.get("endpoint").toString();
			response = request.when().get(endpoint);
			return response;
	 }



}
