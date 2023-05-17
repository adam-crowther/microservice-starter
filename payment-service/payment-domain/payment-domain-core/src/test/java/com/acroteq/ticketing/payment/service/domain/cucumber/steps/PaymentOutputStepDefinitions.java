package com.acroteq.ticketing.payment.service.domain.cucumber.steps;

import static com.acroteq.ticketing.domain.valueobject.PaymentStatus.CANCELLED;
import static com.acroteq.ticketing.domain.valueobject.PaymentStatus.COMPLETED;
import static com.acroteq.ticketing.domain.valueobject.PaymentStatus.FAILED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import com.acroteq.ticketing.payment.service.domain.valueobject.PaymentOutput;
import io.cucumber.java.en.Then;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class PaymentOutputStepDefinitions {

  @Setter
  @Getter
  private PaymentOutput paymentOutput;

  @Then("the payment is completed successfully")
  public void thePaymentIsCompleted() {
    final Payment payment = paymentOutput.getPayment();
    assertThat(payment.getStatus(), is(COMPLETED));
  }

  @Then("the payment failed")
  public void thePaymentIsFailed() {
    final Payment payment = paymentOutput.getPayment();
    assertThat(payment.getStatus(), is(FAILED));
  }

  @Then("the payment is cancelled successfully")
  public void thePaymentIsCancelledSuccessfully() {
    final Payment payment = paymentOutput.getPayment();
    assertThat(payment.getStatus(), is(CANCELLED));
  }

  @Then("the payment cancellation failed")
  public void thePaymentCancellationFailed() {
    final Payment payment = paymentOutput.getPayment();
    assertThat(payment.getStatus(), is(FAILED));
  }

  @Then("the payment validation result is {approvalStatus}")
  public final void theValidationResultIs(final OrderApprovalStatus approvalStatus) {
    final ValidationResult result = paymentOutput.getValidationResult();
    assertThat(result.getApprovalStatus(), is(approvalStatus));
  }
}
