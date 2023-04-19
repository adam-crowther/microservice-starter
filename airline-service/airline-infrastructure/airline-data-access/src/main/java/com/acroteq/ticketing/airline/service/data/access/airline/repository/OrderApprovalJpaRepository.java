package com.acroteq.ticketing.airline.service.data.access.airline.repository;

import com.acroteq.ticketing.airline.service.data.access.airline.entity.OrderApprovalJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderApprovalJpaRepository extends JpaRepository<OrderApprovalJpaEntity, Long> {


}
