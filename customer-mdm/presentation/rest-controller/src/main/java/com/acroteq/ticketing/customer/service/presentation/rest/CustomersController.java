package com.acroteq.ticketing.customer.service.presentation.rest;

import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.ticketing.customer.service.domain.exception.CustomerNotFoundException;
import com.acroteq.ticketing.customer.service.domain.ports.input.service.CustomerApplicationService;
import com.acroteq.ticketing.customer.service.domain.ports.output.repository.CustomerRepository;
import com.acroteq.ticketing.customer.service.presentation.api.CustomersApi;
import com.acroteq.ticketing.customer.service.presentation.exception.MismatchedCustomerIdException;
import com.acroteq.ticketing.customer.service.presentation.mapper.AuditedEntityResponseMapper;
import com.acroteq.ticketing.customer.service.presentation.mapper.CustomerMapper;
import com.acroteq.ticketing.customer.service.presentation.model.AuditedEntityResponse;
import com.acroteq.ticketing.customer.service.presentation.model.CreateCustomer;
import com.acroteq.ticketing.customer.service.presentation.model.Customer;
import com.acroteq.ticketing.customer.service.presentation.model.UpdateCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The REST controller class is for conversion from the API model to the application model, and to bridge the REST API
 * implementation to the service-application layer. The controller should NOT contain business logic.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "${customer-service.permit-cross-origin-from}")
public class CustomersController implements CustomersApi {

  private final CustomerMapper customerMapper;
  private final AuditedEntityResponseMapper auditedResponseMapper;
  private final CustomerApplicationService customerApplicationService;
  private final CustomerRepository customerRepository;

  @Override
  public ResponseEntity<List<Customer>> listCustomers(final Integer limit) {
    final List<Customer> customers = customerApplicationService.loadAllCustomers()
                                                               .stream()
                                                               .map(customerMapper::convert)
                                                               .toList();
    return ResponseEntity.ok(customers);
  }

  @Override
  public ResponseEntity<Customer> getCustomerById(final Long id) {
    final CustomerId customerId = CustomerId.of(id);
    final Customer customer = customerApplicationService.loadCustomer(customerId)
                                                        .map(customerMapper::convert)
                                                        .orElseThrow(() -> new CustomerNotFoundException(customerId));

    return ResponseEntity.ok(customer);
  }

  @Override
  public ResponseEntity<AuditedEntityResponse> createCustomer(final CreateCustomer command) {
    log.info("Creating customer {} {}", command.getFirstName(), command.getLastName());

    final com.acroteq.ticketing.customer.service.domain.entity.Customer customer = customerMapper.convert(command);
    final com.acroteq.ticketing.customer.service.domain.entity.Customer savedCustomer =
        customerApplicationService.createCustomer(customer);
    final AuditedEntityResponse response = auditedResponseMapper.convert(savedCustomer);

    log.info("Customer created with id {}", response.getId());
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> updateCustomerById(final Long id, final UpdateCustomer command) {
    if (!id.equals(command.getId())) {
      throw new MismatchedCustomerIdException(id, command.getId());
    }

    final CustomerId customerId = CustomerId.of(id);
    log.info("Updating customer with id {}", command.getId());

    final com.acroteq.ticketing.customer.service.domain.entity.Customer customer = //
        customerRepository.findById(customerId)
                          .map(customerMapper.convertToExisting(command))
                          .orElseThrow(() -> new CustomerNotFoundException(customerId));
    customerApplicationService.updateCustomer(customer);

    log.info("Customer with id {} updated", command.getId());
    return ResponseEntity.ok()
                         .build();
  }

  @Override
  public ResponseEntity<Void> deleteCustomerById(final Long id) {
    final CustomerId customerId = CustomerId.of(id);
    log.info("Deleting customer with id {}", customerId);

    customerApplicationService.deleteCustomer(customerId);

    log.info("Customer with id {} deleted", customerId);
    return ResponseEntity.ok()
                         .build();
  }
}
