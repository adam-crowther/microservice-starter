Feature: Credit Balance Management
  Credit balance is updated by events when a customer is created, and by Saga steps when payments are processed.

  Scenario: New Customer created
    Given a customer with a credit limit of CHF 2000.00
    When a new credit balance is created
    Then the updated credit balance is CHF 2000.00
    And the new credit change is credit limit update of CHF 2000.00

  Scenario: Existing customer credit limit increased
    Given a customer with a credit limit of CHF 2000
    And a credit balance of CHF 1000
    And a payment credit change of CHF -1000
    When the credit limit is updated to CHF 3000
    Then the updated credit balance is CHF 2000
    And the new credit change is credit limit update of CHF +1000.00

  Scenario: Existing customer credit limit decreased
    Given a customer with a credit limit of CHF 2000
    And a credit balance of CHF 1000
    And a payment credit change of CHF -1000
    When the credit limit is updated to CHF 1000
    Then the updated credit balance is CHF 0
    And the new credit change is credit limit update of CHF -1000.00
