package utilities;
import java.util.Map;

import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.response.Response;
public class APIHelper {
	private RequestSpecification request;
	private ResponseSpecBuilder responseSpecBuilder;
	private Response response;
	private int parseStatusCode(String statusCodeStr, SoftAssert softAssert) {
	    try {
	        return Integer.parseInt(statusCodeStr.trim());
	    } catch (NumberFormatException e) {
	        softAssert.fail("Invalid status code format: " + statusCodeStr);
	        return -1;
	    }
	}

	private void validateResponseStatus(int expectedStatusCode, SoftAssert softAssert) {
	    try {
	        ResponseSpecification responseSpec = new ResponseSpecBuilder()
	                .expectStatusCode(expectedStatusCode)
	                .build();
	        response.then().spec(responseSpec);
	    } catch (AssertionError e) {
	        System.err.println("Status Code Mismatch: " + e.getMessage());
	    }

	    softAssert.assertEquals(response.getStatusCode(), expectedStatusCode, " Status Code Validation Failed!");
	}

	private String extractUserId() {
	    String userId = response.jsonPath().getString("userId");
	    if (userId != null && !userId.isEmpty()) {
	        System.out.println("Extracted User ID: " + userId);
	    }
	    return userId;
	}

	private boolean shouldDeleteUser(int expectedStatusCode, int actualStatusCode, String userId) {
	    return actualStatusCode != expectedStatusCode && actualStatusCode == 201 && userId != null;
	}

	private void deleteUser(String userId, SoftAssert softAssert) {
	    String deleteEndpoint = ConfigReader.getProperty("baseURI") + "/uap/deleteuser/" + userId;

	    Response deleteResponse = RestAssured.given()
	            .auth().basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
	            .header("Accept", "application/json")
	            .when().delete(deleteEndpoint);

	    softAssert.assertEquals(deleteResponse.getStatusCode(), 200, "User deletion failed!");
	    System.out.println(" User with ID " + userId + " deleted due to mismatch.");
	}

	public APIHelper() {
		

		// Initialize RequestSpecBuilder
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri(ConfigReader.getProperty("baseURl")).setContentType("application/json")
				.addHeader("Accept", "application/json");
		request = requestSpecBuilder.build();

	}
	public RequestSpecification validAuth() {
		return RestAssured.given().spec(request).auth().basic(ConfigReader.getProperty("username"),
				ConfigReader.getProperty("password"));
	}
	public RequestSpecification withCustomHeaders(Map<String, String> headers, boolean withAuth) {
	    RequestSpecification customRequest = RestAssured.given().spec(request);

	    if (withAuth) {
	        customRequest.auth().basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));
	    }

	    for (Map.Entry<String, String> header : headers.entrySet()) {
	        customRequest.header(header.getKey(), header.getValue());
	    }

	    return customRequest;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
	public void validateStatusCode(String statusCodeStr, boolean checkAndDeleteUser) {
	    SoftAssert softAssert = new SoftAssert();

	    // Validate input response
	    if (response == null) {
	        System.err.println("Error: Response is null. Cannot validate status code.");
	        return;
	    }

	    int expectedStatusCode = parseStatusCode(statusCodeStr, softAssert);
	    int actualStatusCode = response.getStatusCode();
	    System.out.println("The actual Status Code is: " + response.getStatusCode());
	    // Validate response status
	    validateResponseStatus(expectedStatusCode, softAssert);

	    if (actualStatusCode == 200 || actualStatusCode == 201) {
	        String userId = extractUserId();
	        if (checkAndDeleteUser && shouldDeleteUser(expectedStatusCode, actualStatusCode, userId)) {
	            deleteUser(userId, softAssert);
	        }
	    }


	    // Final soft assertion
	    softAssert.assertAll();
	}
	public RequestSpecification noAuth() {
		return RestAssured.given().spec(request).auth().none();

	}
	
	public RequestSpecification invalidAuth(String username,String password) {
		return RestAssured.given().spec(request).auth().basic(username, password);
	}


}
