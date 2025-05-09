package com.acroteq.ticketing.payment.service.domain.resolver;

import com.acroteq.application.resolver.Resolver;
import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import com.acroteq.ticketing.payment.service.domain.exception.CustomerNotFoundException;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CustomerRepository;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class CustomerResolver implements Resolver<CustomerId, Customer> {

  private final CustomerRepository customerRepository;

  @NonNull
  @Override
  public Customer resolve(@NonNull final Long id) {
    final CustomerId customerId = CustomerId.of(id);
    return resolve(customerId);
  }

  @NonNull
  @Override
  public Customer resolve(@NonNull final CustomerId id) {
    return customerRepository.findById(id)
                             .orElseThrow(() -> new CustomerNotFoundException(id));
  }

  @NonNull
  @Override
  public Customer resolve(@NonNull final String userName) {
    return customerRepository.findByUserName(userName)
                             .orElseThrow(() -> new CustomerNotFoundException(userName));
  }

  @Named("optional")
  @Nullable
  @Override
  public Customer resolveOptional(@Nullable final Long id) {
    return Optional.ofNullable(id)
                   .map(CustomerId::of)
                   .flatMap(customerRepository::findById)
                   .orElse(null);
  }

  @Named("optional")
  @Nullable
  @Override
  public Customer resolveOptional(@Nullable final CustomerId id) {
    return Optional.ofNullable(id)
                   .flatMap(customerRepository::findById)
                   .orElse(null);
  }

  @Named("optional")
  @Nullable
  @Override
  public Customer resolveOptional(@Nullable final String userName) {
    return Optional.ofNullable(userName)
                   .flatMap(customerRepository::findByUserName)
                   .orElse(null);
  }
}
