Feature: User Management API - Create new User

  @stateChanging # heroapp 503 issue work around  fix
  Scenario: Validate create a user with all valid credentials
    Given Admin set the POST request with the valid request body
    When Admin sends a POST request with body to endpoint
    Then The response status code should be "201" for create user

  Scenario: Validate create a user with  only mandatory fields
    Given Admin set the POST request with the valid request body
    When Admin sends a POST request with body to endpoint with mandatory fields only
    Then The response status code should be  "201" for create user mandatory

  Scenario: Validate create a user with invalid content Type
    Given Admin set the POST request with the invalid contentType
    When Admin sends POST Request and request Body with invalid content type
    Then The response status code should be "415" Status

  Scenario: Validate create a user with with invalid endpoint
    Given Admin set the POST request with the valid request body
    When Admin sends HTTPS Request and request Body with invalid endpoint
    Then The response status code should be "404" Status

  Scenario: Validate create a user with invalid request type
    Given Admin set the POST request with the valid request body
    When Admin sends GET Request and request Body with invalid request type
    Then The response status code should be "405" Status
@stateChanging
  Scenario: Validate create a user  with same contact number
    Given Admin set the POST request with the valid request body
    When Admin sends POST Request and request Body with same contact number
    Then The response status code should bes "409" status User already exist with same contact number
    
    @stateChanging
		 Scenario:  Validate create a user with same email
		 Given Admin set the POST request with the valid request body
		 When Admin sends POST Request and request Body with same email
		 Then The response status code should be "409" Status  User already exist with same email
    
   
    @stateChanging # heroapp 503 issue work around  fix
    Scenario: Validate create a user with No Auth
    Given Admin set the POST request with the valid request body with no Auth
    When Admin sends POST Request and request Body with invalid auth
    Then The response status code should be "401" Status
    
    @stateChanging # heroapp 503 issue work around  fix
    Scenario:  Validate create a user with invalid first name 
    Given Admin set the POST request with the valid request body
    When Admin sends POST Request with request Body invalid firstname
    Then The response status code should be "400" Status	
    
    @stateChanging # heroapp 503 issue work around  fix
    Scenario: Validate create a user with invalid last name 
    Given Admin set the POST request with the valid request body
    When Admin sends POST Request and request Body with last name as numeric 
    Then The response status code should be "400" Status	
    
    
     @stateChanging # heroapp 503 issue work around  fix
    Scenario: Validate create a user with invalid contact number 
    Given Admin set the POST request with the valid request body
    When Admin sends POST Request and request Body with invalid contact number 
    Then The response status code should be "400" Status	
    
     #TO TEST worked with heroka fix
     @stateChanging # heroapp 503 issue work around  fix
    Scenario: Validate create a user with invalid  email format
    Given Admin set the POST request with the valid request body
    When Admin sends POST Request and request Body with invalid email format
    Then The response status code should be "400" Status	
    
    @stateChanging # heroapp 503 issue work around  fix
    Scenario: Validate create a user  with invalid plot number
    Given Admin set the POST request with the valid request body
    When Admin sends POST Request and request Body with invalid plot number
    Then The response status code should be "400" Status
    	
     @stateChanging # heroapp 503 issue work around  fix
    Scenario: Validate create a user with invalid street 
    Given Admin set the POST request with the valid request body
    When Admin sends POST Request and request Body with invalid Street 
    Then The response status code should be "400" Status
    
    @stateChanging # heroapp 503 issue work around  fix
    Scenario: Validate create a user with invalid State 
    Given Admin set the POST request with the valid request body
    When Admin sends POST Request and request Body with invalid state 
    Then The response status code should be "400" Status
    
        @stateChanging # heroapp 503 issue work around  fix
    Scenario: Validate create a user with invalid country 
    Given Admin set the POST request with the valid request body
    When Admin sends POST Request and request Body with invalid country 
    Then The response status code should be "400" Status
    
     #@stateChanging # heroapp 503 issue work around  fix  ==>> not working coz of zipcode int pojo
    #Scenario: Validate create a user with invalid zipcode 
    #Given Admin set the POST request with the valid request body
    #When Admin sends POST Request and request Body with invalid zipcode 
    #Then The response status code should be "400" Status
    
