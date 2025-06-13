package utilities;
import java.util.Map;
import utilities.RandomGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.response.Response;
import utilities.JsonReader;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import pojo.CreateUser;
public class APIHelper {
	private RequestSpecification request;
	private ResponseSpecBuilder responseSpecBuilder;
	private Response response;
	private Map<String, Object> testData;
//	private String userEmail;
//	private String userContactNumberUpdate;
//	private String userEmailUpdate;
//	private String userContactNumber;
	 private String userEmail;
	    private String userEmailUpdate;
	    private long   userContactNumber;
	    private long   userContactNumberUpdate;
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
	
	public Response createRequestBody(String scenario, String authType) {
		testData = JsonReader.getScenarioData(scenario);
		System.out.println("this is the testdata");
		System.out.println(testData);
		System.out.println("this is the testdata");
		
		if (testData == null) {
			throw new RuntimeException("Test data is null for scenario: " + scenario);
		}

		// Choose authentication based on authType parameter
		RequestSpecification authRequest;
		switch (authType.toLowerCase()) {
		case "valid":
			authRequest = validAuth();
			break;
		case "invalid":
			authRequest = invalidAuth("numpy@gmail.com", "invalid");
			break;
		case "none":
			authRequest = noAuth();
			break;
		default:
			throw new IllegalArgumentException("Invalid auth type provided: " + authType);
		}

		RandomGenerator generator = new RandomGenerator();
		//String randomContactNumber = generator.generateRandomContactNumber();
		long randomContactNumber = generator.generateRandomContactNumber();
		this.userContactNumber = randomContactNumber;
		
		//testData.put("randomContactNumber", randomContactNumber);
		//You must store the generated random email back into your testData map after generating 
		//it so that it can be used as the expected value during validation.
		String randomEmail = generator.generateRandomEmail();
		//testData.put("userEmailId", randomEmail);
		this.userEmail         = randomEmail;

		// Replace placeholders in test data
//		String userContactNumber = testData.get("user_contact_number").toString().replace("{{randomContactNumber}}",
//				randomContactNumber);
		String userContactNumberStr = testData.get("userContactNumber").toString()
			    .replace("{{randomContactNumber}}", String.valueOf(randomContactNumber));
		//this.userContactNumber = Long.parseLong(userContactNumberStr);
		Long userContactNumber = Long.parseLong(userContactNumberStr);//Convert to Long
		String userEmailstr = testData.get("userEmailId").toString().replace("{{randomEmail}}", randomEmail);
		//this.userEmail = userEmailstr;

		// Prepare request body with POJO class and set values from JSON data
		CreateUser createUser = new CreateUser();
		createUser.setUserFirstName(testData.get("userFirstName").toString());
		createUser.setUserLastName(testData.get("userLastName").toString());
		createUser.setUserContactNumber(this.userContactNumber);
		createUser.setUserEmailId(this.userEmail = userEmailstr);
		CreateUser.UserAddress userAddress = new CreateUser.UserAddress();
		userAddress.setPlotNumber(testData.get("plotNumber").toString());
		userAddress.setStreet(testData.get("street").toString());
		userAddress.setState(testData.get("state").toString());
		userAddress.setCountry(testData.get("country").toString());
		//userAddress.setZipCode(testData.get("zipCode").toString());
		userAddress.setZipCode(Integer.parseInt(testData.get("zipCode").toString()));

		createUser.setUserAddress(userAddress);

		// Convert the POJO to JSON string using ObjectMapper
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody = objectMapper.writeValueAsString(createUser);
			System.out.println("Request Body JSON: " + requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Get the endpoint from test data and send the POST request
		String endpoint = testData.get("endpoint").toString();
		response = authRequest.body(createUser).when().post(endpoint);
		// System.out.println("Response Body: " + response.getBody().asString());
		return response;
	}
	public void schemaValidation() {
		if (response != null) {
			response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/userSchema.json"));
			System.out.println("Schema Validation Passed and is as expected!");
		} else {
			System.out.println("Error: Response is null.Cannot validate the schema");
		}

	}
	
//	public void validateResponseData(boolean isUpdate) {
//
//		// Extract response fields
//		String responseFirstName = response.jsonPath().getString("userFirstName");
//		String responseLastName = response.jsonPath().getString("userLastName");
//		String responseContactNumber = response.jsonPath().getString("userContactNumber");
//		String responseEmail = response.jsonPath().getString("userEmailId");
//
//		// Extract address fields
//		String responsePlotNumber = response.jsonPath().getString("userAddress.plotNumber");
//		String responseStreet = response.jsonPath().getString("userAddress.street");
//		String responseState = response.jsonPath().getString("userAddress.state");
//		String responseCountry = response.jsonPath().getString("userAddress.country");
//		String responseZipCode = response.jsonPath().getString("userAddress.zipCode");
//
////		// Choose correct data for validation
////		String expectedContactNumber = isUpdate ? userContactNumberUpdate : userContactNumber;
////		String expectedEmail = isUpdate ? userEmailUpdate : userEmail;
//		//old
////		long expectedContactNumber = Long.parseLong(testData.get("randomContactNumber").toString());
//		long actualContactNumber = response.jsonPath().getLong("userContactNumber");
//		//old
//		//new for update
//		long expectedContactNumber = isUpdate
//		        ? Long.parseLong(testData.get("randomContactNumberUpdate").toString())
//		        : Long.parseLong(testData.get("randomContactNumber").toString());
//		    String expectedEmail = isUpdate ? testData.get("userEmailIdUpdate").toString() : testData.get("userEmailId").toString();
//			//new for update
//
//		System.out.println("Expected: " + expectedContactNumber);
//		System.out.println("Actual: " + actualContactNumber);
//
//		Assert.assertEquals(actualContactNumber, expectedContactNumber, "Contact Number Mismatch!");
//		//String expectedEmail = testData.get("userEmailId").toString();
//		String actualEmail = response.jsonPath().getString("userEmailId");
//
//		System.out.println("Expected Email: " + expectedEmail);
//		System.out.println("Actual Email: " + actualEmail);
//
//		Assert.assertEquals(actualEmail, expectedEmail, "Email ID Mismatch!");
//
//
//
//
//
//
//		// Validate response data against request data
//		Assert.assertEquals(responseFirstName, testData.get("userFirstName").toString(), "First Name Mismatch!");
//		Assert.assertEquals(responseLastName, testData.get("userLastName").toString(), "Last Name Mismatch!");
//		//Assert.assertEquals(responseContactNumber, expectedContactNumber, "Contact Number Mismatch!");
//		//Assert.assertEquals(responseEmail, expectedEmail, "Email ID Mismatch!");
//
//		// Validate Address Fields
//		Assert.assertEquals(responsePlotNumber, testData.get("plotNumber").toString(), "Plot Number Mismatch!");
//		Assert.assertEquals(responseStreet, testData.get("street").toString(), "Street Mismatch!");
//		Assert.assertEquals(responseState, testData.get("state").toString(), "State Mismatch!");
//		Assert.assertEquals(responseCountry, testData.get("country").toString(), "Country Mismatch!");
//		Assert.assertEquals(responseZipCode, testData.get("zipCode").toString(), "Zip Code Mismatch!");
//
//		System.out.println("All data validation checks passed successfully!");
//	}
	

	public Response createUserWithMandatoryFields(String scenario) {
	    testData = JsonReader.getScenarioData(scenario);
	    System.out.println("Test data keys: " + testData.keySet());

	    if (testData == null) {
	        throw new RuntimeException("Test data is null for scenario: " + scenario);
	    }

	    // Generate random values
	    RandomGenerator generator = new RandomGenerator();
	    long randomContactNumber = generator.generateRandomContactNumber();
	    String randomEmail = generator.generateRandomEmail();
	    testData.put("randomContactNumber", randomContactNumber);
	    testData.put("userEmailId", randomEmail);

	    // Replace placeholders
	    String userContactNumberStr = testData.get("userContactNumber").toString()
	            .replace("{{randomContactNumber}}", String.valueOf(randomContactNumber));
	    Long userContactNumber = Long.parseLong(userContactNumberStr);
	    String userEmail = testData.get("userEmailId").toString()
	            .replace("{{randomEmail}}", randomEmail);

	    // Build POJO with mandatory fields
	    CreateUser createUser = new CreateUser();
	    createUser.setUserFirstName(testData.get("userFirstName").toString());
	    createUser.setUserLastName(testData.get("userLastName").toString());
	    createUser.setUserContactNumber(userContactNumber);
	    createUser.setUserEmailId(userEmail);

	    // Log the request body
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        String json = objectMapper.writeValueAsString(createUser);
	        System.out.println("Request Body JSON: " + json);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    // Initialize RequestSpecification with valid auth
	    RequestSpecification authRequest = validAuth(); // Use the same method you use in createRequestBody()

	    // Send request
	    String endpoint = testData.get("endpoint").toString();
	    Response response = authRequest.body(createUser).when().post(endpoint);

	    return response;
	}
	
	public Response sendRequestWithBody(String scenario, String authType, String method) {
	    testData = JsonReader.getScenarioData(scenario);
	    if (testData == null) {
	        throw new RuntimeException("Test data is null for scenario: " + scenario);
	    }

	    System.out.println("Loaded test data for scenario: " + scenario);

	    // Setup auth
	    RequestSpecification authRequest;
	    switch (authType.toLowerCase()) {
	        case "valid":
	            authRequest = validAuth();
	            break;
	        case "invalid":
	            authRequest = invalidAuth("numpy@gmail.com", "invalid");
	            break;
	        case "none":
	            authRequest = noAuth();
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid auth type: " + authType);
	    }

	    RandomGenerator generator = new RandomGenerator();

	    // Prepare random contact number
	    long randomContactNumber = generator.generateRandomContactNumber();
	    testData.put("randomContactNumber", randomContactNumber);

	    // Handle email
	    boolean useRandomEmail = testData.containsKey("useRandomEmail") &&
	                             Boolean.parseBoolean(testData.get("useRandomEmail").toString());

	    String userEmail = null;
	    if (testData.containsKey("userEmailId") && testData.get("userEmailId") != null) {
	        String userEmailTemplate = testData.get("userEmailId").toString();
	        if (useRandomEmail) {
	            userEmail = generator.generateRandomEmail();
	        } else if (userEmailTemplate.contains("{{randomEmail}}")) {
	            String randomEmail = generator.generateRandomEmail();
	            userEmail = userEmailTemplate.replace("{{randomEmail}}", randomEmail);
	        } else {
	            userEmail = userEmailTemplate;
	        }
	    }

	    // Handle contact number
	    Long userContactNumber = null;
	    if (testData.containsKey("userContactNumber") && testData.get("userContactNumber") != null) {
	        String userContactNumberStr = testData.get("userContactNumber").toString();
	        if (userContactNumberStr.contains("{{randomContactNumber}}")) {
	            userContactNumberStr = userContactNumberStr.replace("{{randomContactNumber}}", String.valueOf(randomContactNumber));
	        }
	        try {
	            userContactNumber = Long.parseLong(userContactNumberStr);
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid userContactNumber format: " + userContactNumberStr);
	        }
	    }

	    // Build POJO - set only fields present in testData (for PATCH especially)
	    CreateUser createUser = new CreateUser();

	    if (testData.containsKey("userFirstName") && testData.get("userFirstName") != null) {
	        createUser.setUserFirstName(testData.get("userFirstName").toString());
	    }
	    if (testData.containsKey("userLastName") && testData.get("userLastName") != null) {
	        createUser.setUserLastName(testData.get("userLastName").toString());
	    }
	    if (userContactNumber != null) {
	        createUser.setUserContactNumber(userContactNumber);
	    }
	    if (userEmail != null && !userEmail.isEmpty()) {
	        createUser.setUserEmailId(userEmail);
	    }

	    // Address - only add if at least one address field exists
	    boolean hasAddress = testData.containsKey("plotNumber") ||
	                         testData.containsKey("street") ||
	                         testData.containsKey("state") ||
	                         testData.containsKey("country") ||
	                         testData.containsKey("zipCode");

	    if (hasAddress) {
	        CreateUser.UserAddress userAddress = new CreateUser.UserAddress();

	        if (testData.containsKey("plotNumber") && testData.get("plotNumber") != null) {
	            userAddress.setPlotNumber(testData.get("plotNumber").toString());
	        }
	        if (testData.containsKey("street") && testData.get("street") != null) {
	            userAddress.setStreet(testData.get("street").toString());
	        }
	        if (testData.containsKey("state") && testData.get("state") != null) {
	            userAddress.setState(testData.get("state").toString());
	        }
	        if (testData.containsKey("country") && testData.get("country") != null) {
	            userAddress.setCountry(testData.get("country").toString());
	        }
	        if (testData.containsKey("zipCode") && testData.get("zipCode") != null) {
	            try {
	                userAddress.setZipCode(Integer.parseInt(testData.get("zipCode").toString()));
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid zip code format: " + testData.get("zipCode"));
	                userAddress.setZipCode(-1);
	            }
	        }
	        createUser.setUserAddress(userAddress);
	    }

	    // Serialize request body for logging
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        String requestBodyJson = objectMapper.writeValueAsString(createUser);
	        System.out.println("Request Body JSON: " + requestBodyJson);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    // Get endpoint, replace user_id placeholder for PUT, PATCH
	    String endpoint = testData.get("endpoint").toString();

	    Response response;

	    switch (method.toUpperCase()) {
	        case "POST":
	            response = authRequest.body(createUser).when().post(endpoint);
	            break;

	        case "GET":
	            // GET requests usually don't have a body - you might want to remove .body(createUser)
	            response = authRequest.when().get(endpoint);
	            break;

	        case "PUT":
	            String userIdPut = TestDataStore.getUserId();
	            System.out.println("Retrieved User ID for PUT: " + userIdPut);
	            String endpointPut = endpoint.replace("{{user_id}}", userIdPut);
	            response = authRequest.body(createUser).when().put(endpointPut);
	            break;

	        case "PATCH":
	            String userIdPatch = TestDataStore.getUserId();
	            System.out.println("Retrieved User ID for PATCH: " + userIdPatch);
	            String endpointPatch = endpoint.replace("{{user_id}}", userIdPatch);
	            response = authRequest.body(createUser).when().patch(endpointPatch);
	            break;

	        case "DELETE":
	            response = authRequest.when().delete(endpoint);
	            break;

	        default:
	            throw new IllegalArgumentException("Invalid HTTP method: " + method);
	    }

	    return response;
	}

	
	public Response updateRequestBody(String scenario, String authType) {
		testData = JsonReader.getScenarioData(scenario);

		// Choose authentication based on authType parameter
		RequestSpecification authRequest;
		switch (authType.toLowerCase()) {
		case "valid":
			authRequest = validAuth();
			break;
		case "invalid":
			authRequest = invalidAuth("numpy@gmail.com", "invalid");;
		case "none":
			authRequest = noAuth();
			break;
		default:
			throw new IllegalArgumentException("Invalid auth type provided: " + authType);
		}
		RandomGenerator generator = new RandomGenerator();
		long randomContactNumber = generator.generateRandomContactNumber();
		String randomEmail = generator.generateRandomEmail();

		// Replace placeholders in test data
		String userContactNumberUpdatestr = testData.get("userContactNumber").toString()
			    .replace("{{randomUpdateContactNumber}}", String.valueOf(randomContactNumber));
		this.userContactNumberUpdate = Long.parseLong(userContactNumberUpdatestr);
		//Long userContactNumberUpdate = Long.parseLong(userContactNumberUpdatestr);
		String userEmailUpdatestr = testData.get("userEmailId").toString().replace("{{randomUpdateEmail}}", randomEmail);
		this.userEmailUpdate = userEmailUpdatestr;

		// Prepare request body with POJO class and set values from JSON data
		CreateUser updateUser = new CreateUser();
		updateUser.setUserFirstName(testData.get("userFirstName").toString());
		updateUser.setUserLastName(testData.get("userLastName").toString());
		updateUser.setUserContactNumber(this.userContactNumberUpdate);
		updateUser.setUserEmailId(this.userEmailUpdate);
		CreateUser.UserAddress userAddress = new CreateUser.UserAddress();
		userAddress.setPlotNumber(testData.get("plotNumber").toString());
		userAddress.setStreet(testData.get("street").toString());
		userAddress.setState(testData.get("state").toString());
		userAddress.setCountry(testData.get("country").toString());
		userAddress.setZipCode(Integer.parseInt(testData.get("zipCode").toString()));
		updateUser.setUserAddress(userAddress);

		// Convert the POJO to JSON string using ObjectMapper
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody = objectMapper.writeValueAsString(updateUser);
			System.out.println("Request Body JSON: " + requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String userId = TestDataStore.getUserId();
		// System.out.println("Retrieved User ID: " + userId);

		// Get the endpoint from test data and send the PUT request
		String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
		response = authRequest.body(updateUser).when().put(endpoint);
		return response;
	}
	public void validateResponseData(boolean isUpdate) {

		// Extract response fields
		String responseFirstName = response.jsonPath().getString("userFirstName");
		String responseLastName = response.jsonPath().getString("userLastName");
		String responseContactNumber = response.jsonPath().getString("userContactNumber");
		String responseEmail = response.jsonPath().getString("userEmailId");

		// Extract address fields
		String responsePlotNumber = response.jsonPath().getString("userAddress.plotNumber");
		String responseStreet = response.jsonPath().getString("userAddress.street");
		String responseState = response.jsonPath().getString("userAddress.state");
		String responseCountry = response.jsonPath().getString("userAddress.country");
		String responseZipCode = response.jsonPath().getString("userAddress.zipCode");

		// Choose correct data for validation
		long expectedContactNumber = isUpdate ? userContactNumberUpdate : userContactNumber;
		String expectedEmail = isUpdate ? userEmailUpdate : userEmail;

		// Validate response data against request data
		Assert.assertEquals(responseFirstName, testData.get("userFirstName").toString(), "First Name Mismatch!");
		Assert.assertEquals(responseLastName, testData.get("userLastName").toString(), "Last Name Mismatch!");
		//Assert.assertEquals(responseContactNumber, expectedContactNumber, "Contact Number Mismatch!");
		Assert.assertEquals(responseEmail, expectedEmail, "Email ID Mismatch!");

		// Validate Address Fields
		Assert.assertEquals(responsePlotNumber, testData.get("plotNumber").toString(), "Plot Number Mismatch!");
		Assert.assertEquals(responseStreet, testData.get("street").toString(), "Street Mismatch!");
		Assert.assertEquals(responseState, testData.get("state").toString(), "State Mismatch!");
		Assert.assertEquals(responseCountry, testData.get("country").toString(), "Country Mismatch!");
		Assert.assertEquals(responseZipCode, testData.get("zipCode").toString(), "Zip Code Mismatch!");

		System.out.println("All data validation checks passed successfully!");
	}

	


	
	



}
