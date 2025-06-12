Feature: User Management API - Update  User

  #@stateChanging # heroapp 503 issue work around  fix
  Scenario: Verify update a user with all valid credentials
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body
    Then The response status code should be "200" Status code for update user

  Scenario: Verify update a user with only firstname update
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body to update only firstname
    Then The response status code should be "200" Status code update

  Scenario: Verify update a user with only lastname update
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body to update only lastname
    Then The response status code should be "200" Status code update

  Scenario: Verify update a user with only phone numer update
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body to update only phone numer
    Then The response status code should be "200" Status code update

  Scenario: Verify update a user with only email update
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body to update only email
    Then The response status code should be "200" Status code update

  Scenario: Verify update a user with only plot number update
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body to update only plot
    Then The response status code should be "200" Status code update

  Scenario: Verify update a user with only street update
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body to update only street
    Then The response status code should be "200" Status code update

  Scenario: Verify update a user with only state update
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body to update only state
    Then The response status code should be "200" Status code update

  Scenario: Verify update a user with only country update
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body to update only country
    Then The response status code should be "200" Status code update

  Scenario: Verify update a user with only zipcode update
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body to update only zipcode
    Then The response status code should be "200" Status code update

  Scenario: Verify update a user with existing email
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body with existing email
    Then The response status code should be "400" Status code update

  Scenario: Verify update a user with existing contact number
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body with existing contact number
    Then The response status code should be "400" Status code update

  Scenario: Verify update a user with first name numeric
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body with first name numeric
    Then The response status code should be "400" Status code update

  Scenario: Verify update a user with first name numeric
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body with first name numeric
    Then The response status code should be "400" Status code update

  Scenario: Verify update a user with last name numeric
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body with last name numeric
    Then The response status code should be "400" Status code update

  Scenario: Verify update a user with emailid  numeric
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body with emailid numeric
    Then The response status code should be "400" Status code update
    
    Scenario: Verify update a user with emailid  numeric
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body with emailid numeric
    Then The response status code should be "400" Status code update
    
    Scenario: Verify update a user with empty  firstname
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body  with empty  firstname
    Then The response status code should be "400" Status code update
    
    Scenario: Verify update a user with empty  lastname
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body  with empty  lastname
    Then The response status code should be "400" Status code update
    
     Scenario: Verify update a user with empty  contact number
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body  with empty  contact number
    Then The response status code should be "400" Status code update
    
      Scenario: Verify update a user with empty  emailid 
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body  with empty  emailid 
    Then The response status code should be "400" Status code update
    
      Scenario: Verify update a user with invalid  emailid 
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body  with invalid  emailid 
    Then The response status code should be "400" Status code update
    
      Scenario: Verify update a user with invalid  phone number 
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body  with invalid   phone number  
    Then The response status code should be "400" Status code update
    
       Scenario: Verify update a user with invalid  state 
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body  with invalid  state 
    Then The response status code should be "400" Status code update
    
        Scenario: Verify update a user with invalid  country 
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body  with invalid  country 
    Then The response status code should be "400" Status code update
    
          Scenario: Verify update a user with invalid  endpoint 
    Given Admin set the PUT request with the valid request body
    When Admin sends PUT Request with request Body  with invalid  endpoint 
    Then The response status code should be "404" Status code update
    

  Scenario: Check if Admin is able to update a user with No Auth
    Given Admin set the PUT request with the valid request body with no Auth
    When Admin sends HTTPS Request and update request Body with no or invalid auth
    Then The response status code should be "401" Status code update
