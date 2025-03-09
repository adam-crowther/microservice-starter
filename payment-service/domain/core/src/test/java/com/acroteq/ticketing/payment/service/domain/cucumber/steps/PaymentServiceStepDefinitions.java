package com.acroteq.ticketing.payment.service.domain.cucumber.steps;

import static lombok.AccessLevel.PACKAGE;

import com.acroteq.ticketing.payment.service.domain.PaymentDomainService;
import com.acroteq.ticketing.payment.service.domain.PaymentDomainServiceImpl;
import com.acroteq.ticketing.payment.service.domain.valueobject.PaymentOutput;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentServiceStepDefinitions {

  public final CustomerEntityStepDefinitions customerEntitySteps;
  public final CreditBalanceEntityStepDefinitions creditBalanceEntitySteps;
  private final PaymentEntityStepDefinitions paymentEntitySteps;
  private final PaymentOutputStepDefinitions paymentOutputSteps;
  private final CreditBalanceOutputStepDefinitions creditBalanceOutputSteps;

  @Getter(PACKAGE)
  private PaymentDomainService service;

  @Before(order = 1)
  public final void initialiseService() {
    service = new PaymentDomainServiceImpl();
  }

  @When("the payment is validated")
  public void thePaymentIsValidated() {
    final PaymentOutput paymentOutput = service.validatePayment(paymentEntitySteps.getPayment(),
                                                                creditBalanceEntitySteps.getCreditBalance(),
                                                                creditBalanceEntitySteps.getCreditHistory());
    paymentOutputSteps.setPaymentOutput(paymentOutput);
    creditBalanceOutputSteps.setCreditBalance(paymentOutput.getCreditBalance());
    paymentOutput.getCreditChange()
                 .ifPresent(creditBalanceOutputSteps::setCreditChange);
  }

  @When("the payment cancellation is requested")
  public void thePaymentCancellationRequested() {
    final PaymentOutput paymentOutput = service.cancelPayment(paymentEntitySteps.getPayment(),
                                                              creditBalanceEntitySteps.getCreditBalance());
    paymentOutputSteps.setPaymentOutput(paymentOutput);
    creditBalanceOutputSteps.setCreditBalance(paymentOutput.getCreditBalance());
    paymentOutput.getCreditChange()
                 .ifPresent(creditBalanceOutputSteps::setCreditChange);
  }
}
