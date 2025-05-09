package com.acroteq.ticketing.order.service.data.access.order.repository;

import com.acroteq.ticketing.order.service.data.access.order.entity.OrderItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemJpaRepository extends JpaRepository<OrderItemJpaEntity, Long> {

}
