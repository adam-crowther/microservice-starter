package com.acroteq.ticketing.order.service.data.access.airline.repository;

import com.acroteq.ticketing.order.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.order.service.data.access.airline.entity.AirlineJpaEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirlineJpaRepository extends JpaRepository<AirlineJpaEntity, AirlineJpaEntityId> {

  Optional<List<AirlineJpaEntity>> findByAirlineId(Long airlineId);

  Optional<AirlineJpaEntity> findByFlightId(Long airlineId);
}
