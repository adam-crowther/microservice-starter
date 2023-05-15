package com.acroteq.ticketing.infrastructure.data.access.mapper;

import com.acroteq.ticketing.infrastructure.data.access.entity.TestEntity;
import com.acroteq.ticketing.infrastructure.data.access.entity.TestJpaEntity;
import com.acroteq.ticketing.infrastructure.mapper.JpaToDomainMapper;

public interface TestJpaToDomainMapper extends JpaToDomainMapper<TestJpaEntity, TestEntity> {}
