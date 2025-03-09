Feature: Payment Cancellation
  The payment should only be allowed to complete if the Customer has enough credit

  Scenario: Payment is completed and can be cancelled
    Given a customer with a credit limit of CHF 2000.00
    And a credit balance of CHF 2000.00
    And a completed payment value of CHF 250.00
    When the payment cancellation is requested
    Then the payment is cancelled successfully
    And the payment validation result is approved
    And the updated credit balance is CHF 2250.00

  Scenario: Payment was already cancelled
    Given a customer with a credit limit of CHF 2000
    And a credit balance of CHF 2000
    And a cancelled payment value of CHF 250
    When the payment cancellation is requested
    Then the payment cancellation failed
    And the payment validation result is rejected
    And the updated credit balance is CHF 2000.00

  Scenario: Payment is failed
    Given a customer with a credit limit of CHF 2000
    And a credit balance of CHF 2000
    And a failed payment value of CHF 250
    When the payment cancellation is requested
    Then the payment cancellation failed
    And the payment validation result is rejected
    And the updated credit balance is CHF 2000.00

