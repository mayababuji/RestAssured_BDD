Feature: User Management API - Update  User with Patch request

  #@stateChanging # heroapp 503 issue work around  fix
  @patch
  Scenario: Verify patch update for user firstname
    Given Admin set the PATCH request with the valid request body
    When Admin sends PATCH Request with request Body
    Then The response status code should be "200" Status code for patch update user
    @patch
    Scenario:  Verify patch update for user lastname
    Given Admin set the PATCH request with the valid request body
    When Admin sends PATCH Request with request Body for user lastname
    Then The response status code should be "200" Status code for patch update user
     @patch
     Scenario:  Verify patch update for user phone number
    Given Admin set the PATCH request with the valid request body
    When Admin sends PATCH Request with request Body for user phone number
    Then The response status code should be "200" Status code for patch update user