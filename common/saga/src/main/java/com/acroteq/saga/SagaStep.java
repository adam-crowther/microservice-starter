package com.acroteq.saga;

import com.acroteq.application.dto.DataTransferObject;
import com.acroteq.domain.event.Event;

public interface SagaStep<SuccessResponseT extends DataTransferObject, SuccessEventT extends Event,
    FailureResponseT extends DataTransferObject, FailureEventT extends Event> {

  SuccessEventT process(SuccessResponseT data);

  FailureEventT rollback(FailureResponseT data);
}
