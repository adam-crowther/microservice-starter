Feature: Payment Validation
  The payment should only be allowed to complete if the Customer has enough credit

  Scenario: New customer has enough credit for the requested payment
    Given a customer with a credit limit of CHF 2000.00
    And a credit balance of CHF 2000.00
    And a credit limit update credit change of CHF 2000.00
    And a pending payment value of CHF 250.00
    When the payment is validated
    Then the payment is completed successfully
    And the payment validation result is approved
    And the updated credit balance is CHF 1750.00
    And the new credit change is payment of CHF -250.0

  Scenario: Existing customer with credit history has enough credit for the requested payment
    Given a customer with a credit limit of CHF 2000
    And a credit balance of CHF 1500
    And a credit limit update credit change of CHF 2000
    And a payment credit change of CHF -300
    And a payment credit change of CHF -200
    And a pending payment value of CHF 250
    When the payment is validated
    Then the payment is completed successfully
    And the payment validation result is approved
    And the updated credit balance is CHF 1250
    And the new credit change is payment of CHF -250.0

  Scenario: New customer does not have enough credit for the requested payment
    Given a customer with a credit limit of CHF 200
    And a credit balance of CHF 200
    And a credit limit update credit change of CHF 200
    And a pending payment value of CHF 250
    When the payment is validated
    Then the payment failed
    And the payment validation result is rejected
    And the updated credit balance is CHF 200
    And there is no new credit change

  Scenario: Existing customer does not have enough credit for the requested payment
    Given a customer with a credit limit of CHF 2000
    And a credit balance of CHF 1000
    And a credit limit update credit change of CHF 2000
    And a payment credit change of CHF -300
    And a payment credit change of CHF -250
    And a credit limit update credit change of CHF -1000
    And a payment credit change of CHF -200
    And a payment credit change of CHF -100
    And a pending payment value of CHF 250
    When the payment is validated
    Then the payment failed
    And the payment validation result is rejected
    And the updated credit balance is CHF 1000
    And there is no new credit change

  Scenario: Existing customer has a mismatch in current credit and credit history
    Given a customer with a credit limit of CHF 2000
    And a credit balance of CHF 2000
    And a credit limit update credit change of CHF 2000
    And a payment credit change of CHF -100
    And a pending payment value of CHF 250
    When the payment is validated
    Then the payment failed
    And the payment validation result is rejected
    And the updated credit balance is CHF 2000
    And there is no new credit change

  Scenario: New customer has no credit
    Given a customer with a credit limit of CHF 0
    And a credit balance of CHF 0
    And a pending payment value of CHF 250.00
    When the payment is validated
    Then the payment failed
    And the payment validation result is rejected
    And the updated credit balance is CHF 0
    And there is no new credit change

  Scenario: Customer has negative credit
    Given a customer with a credit limit of CHF 2000.00
    And a credit balance of CHF -2000.00
    And a credit limit update credit change of CHF 2000.00
    And a credit limit update credit change of CHF -4000.00
    And a pending payment value of CHF 250.00
    When the payment is validated
    Then the payment failed
    And the payment validation result is rejected
    And the updated credit balance is CHF -2000.00
    And there is no new credit change

  Scenario: Payment amount is zero
    Given a customer with a credit limit of CHF 2000
    And a credit balance of CHF 2000
    And a credit limit update credit change of CHF 2000
    And a pending payment value of CHF 0
    When the payment is validated
    Then the payment failed
    And the payment validation result is rejected
    And the updated credit balance is CHF 2000
    And there is no new credit change
