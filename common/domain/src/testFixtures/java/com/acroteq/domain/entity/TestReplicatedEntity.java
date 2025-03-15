package com.acroteq.domain.entity;

import com.acroteq.domain.valueobject.TestId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class TestReplicatedEntity extends ReplicatedEntity<TestId> {}
