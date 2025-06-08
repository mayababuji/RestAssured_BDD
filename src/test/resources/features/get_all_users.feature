Feature: User Management API - Get All Users
  As an API consumer
  I want to retrieve all users
  So that I can view the complete user list

  Scenario: Get all users with valid authentication
    Given Admin has valid admin credentials
    When Admin sends a GET request to endpoint
    Then The response status code should be "200"
  