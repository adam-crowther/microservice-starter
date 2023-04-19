package com.acroteq.ticketing.customer.service.data.access.customer.repository;

import com.acroteq.ticketing.customer.service.data.access.customer.entity.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {}
