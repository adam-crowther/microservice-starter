package com.acroteq.ticketing.infrastructure.data.access.entity;

import com.acroteq.ticketing.domain.entity.PrimaryEntity;
import com.acroteq.ticketing.infrastructure.data.access.valueobject.TestId;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class TestEntity extends PrimaryEntity<TestId> {}
