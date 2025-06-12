Feature: User Management API - Get by User First Name

Scenario: Verify GET user by valid USER FirstName
    Given Admin set the GET request by user firstName
    When Admin sends the GET  request by User FirstName
    Then The response status code should be  "200" Status code for UserFirstName
    
    Scenario: Verify GET user  by invalid USER FirstName
		Given Admin set the GET request by user firstName
		When  Admin sends GET request with invalid userfirstName
		Then The response status code should be  "404" Status code for UserFirstName
		
		Scenario:  Verify GET by user FirstName with invalid request type
		Given Admin set the GET request by user firstName
		When  Admin sends GET Request with invalid requestType by User firstName
		Then The response status code should be  "405" Status code for UserFirstName
		
		
		
		Scenario: Verify GET user by USER FirstName with invaid Basic Auth
		Given Admin set the GET request by user firstName with invalid autorization
		When  Admin sends the GET  request by User FirstName
		Then The response status code should be  "401" Status code for UserFirstName
		
		Scenario: Verify GET user by USER FirstName  with No Auth
		Given Admin set the GET request by user firstName with No Auth
		When  Admin sends the GET  request by User FirstName
		Then The response status code should be  "401" Status code for UserFirstName
		
		