package com.acroteq.infrastructure.data.access.mapper;

import com.acroteq.infrastructure.data.access.entity.TestEntity;
import com.acroteq.infrastructure.data.access.entity.TestJpaEntity;
import com.acroteq.infrastructure.mapper.JpaMapper;

public abstract class TestJpaMapper implements JpaMapper<TestEntity, TestJpaEntity> {}
