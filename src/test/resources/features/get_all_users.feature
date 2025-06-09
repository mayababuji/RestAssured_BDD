Feature: User Management API - Get All Users


  Scenario: Verify Get all users with valid authentication
    Given Admin sets get request with valid credentials
    When Admin sends a GET request to endpoint
    Then The response status code should be "200"
    
   Scenario: Verify Get All User List without endpoint
    Given Admin sets get request with valid credentials
    When Admin sends a GET request to invalid endpoint
    Then The response status code should be "404" 
    
        
   Scenario: Verify GET request missing standard headers ( no host headers)
    Given Admin sets get request with valid credentials
    When Admin sends a GET request with invalid value for host header
    Then The response status code should be "400"
    
     Scenario: Verify request with malformed "Accept" header is rejected
    Given Admin sets get request with valid credentials
    When Admin sends a GET request with invalid value for accept header "application//json"
    Then The response status code should be "406"
    
      Scenario: Verify request with "Accept: application/xml" is rejected
    Given Admin sets get request with valid credentials
    When Admin sends a GET request with invalid value for accept header "application/xml"
    Then The response status code should be "406"
    
    Scenario: Validate request with "Accept: application/json" returns JSON
  Given Admin sets get request with valid credentials
  When Admin sets GET request with Accept header as "application/json"
  Then The response content type should be "application/json"
  
       Scenario: Verify  request without Accept header returns default (JSON)
    Given Admin sets get request with valid credentials
    When Admin sends GET request without Accept header
    Then  The response content type should be "application/json"
    
        Scenario: Validate request with case sensitive "Accept: APPLICATION/JSON" returns JSON
  Given Admin sets get request with valid credentials
  When Admin sets GET request with Accept header as "APPLICATION/JSON"
  Then The response content type should be "application/json"
  
  
   Scenario: Validate custom header like X-Request-ID and confirm it does not break functionality
  Given Admin sets get request with valid credentials
  When Admin sets GET request with custom header as "application/xml"
  Then The response content type should be "application/json"
  
     Scenario: Validate sending POST request to GET /users endpoint returns 405 error
  Given Admin sets get request with valid credentials
  When Admin sends POST request to  endpoint
  Then The response status code should be "405"
  
  Scenario: Validate  Get All User List with invalid endpoint does not retrieve any value
Given  Admin sets get request with valid credentials
When Admin sends HTTPS Request with invalid endpoint
Then The response status code should be "404" 
  
  
  Scenario: Validate  Get  all user with No Auth
Given Admin set the get request without Auth
When Admin sends a GET request to endpoint
Then The response status code should be "401" 


Scenario: Validate Get  all user with Invalid Basic Auth
Given Admin set the get request with Invalid Basic Auth
When Admin sends a GET request to endpoint
Then The response status code should be "401" 
  