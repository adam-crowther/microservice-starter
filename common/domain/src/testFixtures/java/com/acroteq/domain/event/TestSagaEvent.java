package com.acroteq.ticketing.domain.event;

import com.acroteq.domain.event.SagaEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class TestSagaEvent extends SagaEvent {


}
