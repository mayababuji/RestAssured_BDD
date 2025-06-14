Feature: User Management API - Delete by User ID

  Scenario: Verify DELETE user by valid USER ID
    Given Admin set the DELETE request by user id
    When Admin sends the DELETE request by User ID
    Then The response status code should be "200" for delete USER ID

  Scenario: Verify DELETE user by USER ID with No Auth
    Given Admin set the DELETE request by user id with No Auth
    When Admin sends the DELETE request by User ID
    Then The response status code should be "401" status code

  Scenario: Verify DELETE user by USER ID with invaid Basic Auth
    Given Admin set the DELETE request by user id with invalid basic auth
    When Admin sends the DELETE request by User ID
    Then The response status code should be "401" status code

  Scenario: Verify DELETE user by USER ID  with invalid request type
    Given Admin set the DELETE request by user id
    When Admin sends invalid requestType for Delete by userID
    Then The response status code should be "405" status code

  Scenario: Verify DELETE user by USER ID  invalid USER ID
    Given Admin set the DELETE request by user id
    When Admin sends DELETE  Request with invalid userID
    Then The response status code should be "404" status code

  Scenario: Verify DELETE user by USER ID for already deleted USER ID
    Given Admin set the DELETE request by user id
    When Admin sends DELETE  Request with already deleted userID
    Then The response status code should be "404" status code

  Scenario: Verify DELETE user by USER ID without end point
    Given Admin set the DELETE request by user id
    When Admin sends DELETE  Request without end point
    Then The response status code should be "404" status code
