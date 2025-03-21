package com.acroteq.infrastructure.data.access.entity;

import com.acroteq.domain.entity.PrimaryEntity;
import com.acroteq.infrastructure.data.access.valueobject.TestId;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class TestEntity extends PrimaryEntity<TestId> {}
