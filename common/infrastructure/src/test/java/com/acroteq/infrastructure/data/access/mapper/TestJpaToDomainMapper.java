package com.acroteq.infrastructure.data.access.mapper;

import com.acroteq.infrastructure.data.access.entity.TestEntity;
import com.acroteq.infrastructure.data.access.entity.TestJpaEntity;
import com.acroteq.infrastructure.mapper.JpaToDomainMapper;

public interface TestJpaToDomainMapper extends JpaToDomainMapper<TestJpaEntity, TestEntity> {}
