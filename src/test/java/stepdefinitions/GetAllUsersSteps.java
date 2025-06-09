package stepdefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import utilities.APIHelper;
import utilities.JsonReader;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

public class GetAllUsersSteps {
	private RequestSpecification request;
    private Response response;
    private String username;
    private String password;
    private Map<String, Object> testData;
    
    APIHelper apihelper = new APIHelper();

    @Given("Admin sets get request with valid credentials")
    public void admin_sets_get_request_with_valid_credentials() {
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
    	testData = JsonReader.getScenarioData("get all users valid end point");
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
    
    @When("Admin sends a GET request to invalid endpoint")
    public Response admin_sends_a_get_request_to_invalid_endpoint() {
   
    	response = request.when().get("");
		return response;
    }

    @When("Admin sends a GET request with invalid value for host header")
    public Response admin_sends_a_get_request_with_invalid_value_for_host_header() {
     // request = given().header("Host", "");
    	Map<String, String> invalidHeaders = new HashMap<>();
    	invalidHeaders.put("Host", "");
    	testData = JsonReader.getScenarioData("get all users valid end point");
		String endpoint = testData.get("endpoint").toString();
    	response = apihelper.withCustomHeaders(invalidHeaders, true)
    			.log().headers()
                .when()
                .get(endpoint);
    	return response;
    }
    
    @When("Admin sends a GET request with invalid value for accept header {string}")
    public Response admin_sends_a_get_request_with_invalid_value_for_accept_header(String invalidAcceptHeader) {
    	Map<String, String> invalidHeaders = new HashMap<>();
    	invalidHeaders.put("Accept", invalidAcceptHeader);
    	testData = JsonReader.getScenarioData("get all users valid end point");
		String endpoint = testData.get("endpoint").toString();
    	response = apihelper.withCustomHeaders(invalidHeaders, true)
    			.log().headers()
                .when()
                .get(endpoint);
    	return response;
    }
    
    @When("Admin sets GET request with Accept header as {string}")
    public Response admin_sets_get_request_with_accept_header_as(String acceptHeader) {
    	 Map<String, String> headers = new HashMap<>();
    	    headers.put("Accept", acceptHeader);
    	    testData = JsonReader.getScenarioData("get all users valid end point");
    	    String endpoint = testData.get("endpoint").toString();
    	    response = apihelper.withCustomHeaders(headers, true)
    	    		.log().headers()
    	                        .when()
    	                        .get(endpoint);
    	    return response;
    }

    @Then("The response content type should be {string}")
    public void the_response_content_type_should_be(String expectedContentType) {
    	String actualContentType = response.getContentType();
        
    	assertThat("Content-Type header mismatch", actualContentType, containsString(expectedContentType));

    }
    
    @When("Admin sends GET request without Accept header")
    public Response admin_sends_get_request_without_accept_header() {
    	Map<String, String> headers = new HashMap<>();
    	headers.put("Accept", "");
    	testData = JsonReader.getScenarioData("get all users valid end point");
    	String endpoint = testData.get("endpoint").toString();
    	response = apihelper.withCustomHeaders(headers, true)
                .log().headers()
                .when()
                .get(endpoint);
    
    	    return response;
    }
    
    @When("Admin sets GET request with custom header as {string}")
    public Response admin_sets_get_request_with_custom_header_as(String customHeader) {
    	Map<String, String> headers = new HashMap<>();
	    headers.put("X-Request-ID", customHeader);
	    testData = JsonReader.getScenarioData("get all users valid end point");
	    String endpoint = testData.get("endpoint").toString();
	    response = apihelper.withCustomHeaders(headers, true)
	    				.log().headers()
	                        .when()
	                        .get(endpoint);
	    return response;
    }

    
    @When("Admin sends POST request to  endpoint")
    public Response admin_sends_post_request_to_endpoint() {
    	testData = JsonReader.getScenarioData("get all users valid end point");
	    String endpoint = testData.get("endpoint").toString();
	    response = request.when().post(endpoint);
	    String allowHeader = response.getHeader("Allow");
	    System.out.println("The actual allowed method is "+allowHeader);
	    System.out.println("Response body is : " + response.getBody().asString());
return response;
}
    
    @When("Admin sends HTTPS Request with invalid endpoint")
    public Response admin_sends_https_request_with_invalid_endpoint() {
    	testData = JsonReader.getScenarioData("get all users invalid endpoint");
		String endpoint = testData.get("endpoint").toString();
		response = request.when().get(endpoint);
		return response;
    }
    
    @Given("Admin set the get request without Auth")
    public void admin_set_the_get_request_without_auth() {
    	request = apihelper.noAuth();
    }

    @Given("Admin set the get request with Invalid Basic Auth")
    public void admin_set_the_get_request_with_invalid_basic_auth() {
    	testData = JsonReader.getScenarioData("get all users invalid Basic Auth");
		String username = testData.get("username").toString();
		String password = testData.get("password").toString();
		System.out.println("The username is "+username);
		System.out.println("The password is "+password);
		request = apihelper.invalidAuth(username,password);
		
    }

    



}
