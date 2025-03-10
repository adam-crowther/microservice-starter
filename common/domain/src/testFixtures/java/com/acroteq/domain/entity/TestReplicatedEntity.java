package com.acroteq.ticketing.domain.entity;

import com.acroteq.domain.entity.ReplicatedEntity;
import com.acroteq.ticketing.domain.valueobject.TestId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class TestReplicatedEntity extends ReplicatedEntity<TestId> {}
