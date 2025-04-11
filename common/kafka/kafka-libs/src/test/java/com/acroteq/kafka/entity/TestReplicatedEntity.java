package com.acroteq.kafka.entity;

import com.acroteq.domain.entity.ReplicatedEntity;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class TestReplicatedEntity extends ReplicatedEntity<TestEntityId> {}
