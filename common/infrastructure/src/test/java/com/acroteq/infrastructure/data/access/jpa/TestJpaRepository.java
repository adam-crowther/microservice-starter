package com.acroteq.infrastructure.data.access.jpa;

import com.acroteq.infrastructure.data.access.entity.TestJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestJpaRepository extends JpaRepository<TestJpaEntity, Long> {}
