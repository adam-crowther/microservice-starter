package com.acroteq.ticketing.saga;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.domain.event.DomainEvent;

public interface SagaStep<SuccessResponseT extends Dto, SuccessEventT extends DomainEvent<?>,
    FailureResponseT extends Dto, FailureEventT extends DomainEvent<?>> {

  SuccessEventT process(SuccessResponseT data);

  FailureEventT rollback(FailureResponseT data);
}
