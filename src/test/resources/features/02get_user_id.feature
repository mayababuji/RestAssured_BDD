Feature: User Management API - Get  User ID

     @stateChanging # heroapp 503 issue work around
    Scenario: Verify Get  userid with valid userID
    Given Admin sets get request with valid credentials to get userid
    When Admin sends the GET userID request 
    Then The response status code should be "200" with valid user get userid
    
     @stateChanging # heroapp 503 issue work around
    Scenario: Verify Get userid with invalid USER ID
		Given Admin sets get request with valid credentials to get userid
		When  Admin sends GET Request with invalid userID
		Then The response status code should be "404" Status code with valid user details

		 @stateChanging # heroapp 503 issue work around
		Scenario: Verify Get userid  with invalid request type
		Given Admin sets get request with valid credentials to get userid
		When  Admin sends POST Request with invalid requestType
		Then The response status code should be "405" Status code with valid user details
    
     @stateChanging # heroapp 503 issue work around
    Scenario: Verify Get userid  with invaid Basic Auth
		Given Admin sets the get request by user id with invalid basic auth
		When  Admin sends the GET userID request
		Then The response status code should be "401" Status code with valid user details
		@stateChanging
		Scenario:  Verify Get userid  with No Auth
		Given Admin sets the get request by user id with No Auth
		When  Admin sends the GET userID request
		Then The response status code should be "401" Status code with valid user details