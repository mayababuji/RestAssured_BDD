package stepdefinitions;

import java.util.Map;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.APIHelper;
import utilities.JsonReader;
import utilities.TestDataStore;
public class DeleteByUserIdSteps {
	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;
	 APIHelper apihelper = new APIHelper();
	 @Given("Admin set the DELETE request by user id")
	 public void admin_set_the_delete_request_by_user_id() {
		 request = apihelper.validAuth();
	 }

	 @When("Admin sends the DELETE request by User ID")
	 public Response admin_sends_the_delete_request_by_user_id() {
		 testData = JsonReader.getScenarioData("valid delete by userid");
		 
			String userId = TestDataStore.getUserId();
			System.out.println("Retrieved User ID for delete user: " + userId);
			String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
			response = request.when().delete(endpoint);
			return response;
	 }
	 @When("Admin sends invalid requestType for Delete by userID")
	 public Response admin_sends_invalid_request_type_for_delete_by_user_id() {
		 	//testData = JsonReader.getScenarioData("delete_with_invalid_request");
		 	 testData = JsonReader.getScenarioData("valid delete by userid");
		 	System.out.println("This is thetestData  for invalid requestType for Delete by userID");
		 	System.out.println(testData);
			 System.out.println(testData.get("endpoint"));
			 System.out.println("This is thetestData  for invalid requestType for Delete by userID");
			String userId = TestDataStore.getUserId();
			System.out.println("Retrieved User ID for invalid requestType for Delete by userID : " + userId);
			String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
			response = request.when().post(endpoint);
			return response;
	 }
	 @Then("The response status code should be {string} for delete USER ID")
	 public void the_response_status_code_should_be_for_delete_user_id(String statusCode) {
		 apihelper.setResponse(response);
		 apihelper.validateStatusCode(statusCode, true);
			String actualMessage = response.jsonPath().getString("message");
			String expectedMessage = "User is deleted successfully";
			Assert.assertEquals(actualMessage, expectedMessage);
	 }
	 
	 @Given("Admin set the DELETE request by user id with No Auth")
	 public void admin_set_the_delete_request_by_user_id_with_no_auth() {
		 
		 request = apihelper.noAuth();
	 }
	 @Then("The response status code should be {string} status code")
	 public void the_response_status_code_should_be_status_code(String statusCode) {
		 apihelper.setResponse(response);
		 apihelper.validateStatusCode(statusCode, true);
	 }
	 
	 
	 @Given("Admin set the DELETE request by user id with invalid basic auth")
	 public void admin_set_the_delete_request_by_user_id_with_invalid_basic_auth() {
		request = apihelper.invalidAuth("numpy@gmail.com", "invalid");
	 }

	

	 @When("Admin sends DELETE  Request with invalid userID")
	 public Response admin_sends_delete_request_with_invalid_user_id() {
		 testData = JsonReader.getScenarioData("delete by invalid userid");
			String userId = TestDataStore.getUserId();
			System.out.println("Retrieved User ID: " + userId);
			String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
			response = request.when().delete(endpoint);
			return response;
	 }
	 
	 @When("Admin sends DELETE  Request with already deleted userID")
	 public Response admin_sends_delete_request_with_already_deleted_user_id() {
		 testData = JsonReader.getScenarioData("delete by already deletedId");
			String userId = TestDataStore.getUserId();
			System.out.println("Retrieved User ID: " + userId);
			String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
			response = request.when().delete(endpoint);
			return response;
	 }
	 
	 @When("Admin sends DELETE  Request without end point")
	 public Response admin_sends_delete_request_without_end_point() {
		 testData = JsonReader.getScenarioData("delete without endpoint");
			String userId = TestDataStore.getUserId();
			System.out.println("Retrieved User ID: " + userId);
			String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
			response = request.when().delete(endpoint);
			return response;
	 }

}
