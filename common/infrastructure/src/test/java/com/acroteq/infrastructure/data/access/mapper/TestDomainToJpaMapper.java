package com.acroteq.infrastructure.data.access.mapper;

import com.acroteq.infrastructure.data.access.entity.TestEntity;
import com.acroteq.infrastructure.data.access.entity.TestJpaEntity;
import com.acroteq.infrastructure.mapper.DomainToJpaMapper;

public interface TestDomainToJpaMapper extends DomainToJpaMapper<TestEntity, TestJpaEntity> {}
