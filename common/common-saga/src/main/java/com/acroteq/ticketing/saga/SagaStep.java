package com.acroteq.ticketing.saga;

import com.acroteq.ticketing.application.dto.DataTransferObject;
import com.acroteq.ticketing.domain.event.Event;

public interface SagaStep<SuccessResponseT extends DataTransferObject, SuccessEventT extends Event,
    FailureResponseT extends DataTransferObject, FailureEventT extends Event> {

  SuccessEventT process(SuccessResponseT data);

  FailureEventT rollback(FailureResponseT data);
}
