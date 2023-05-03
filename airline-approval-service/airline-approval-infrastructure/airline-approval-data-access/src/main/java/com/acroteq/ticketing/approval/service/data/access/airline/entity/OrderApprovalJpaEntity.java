package com.acroteq.ticketing.approval.service.data.access.airline.entity;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus;
import com.acroteq.ticketing.infrastructure.data.access.entity.MasterJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "order_approval")
@Entity
public class OrderApprovalJpaEntity extends MasterJpaEntity {

  @Column(name = "airline_id")
  private Long airlineId;

  @Column(name = "order_id")
  private Long orderId;

  @Column(name = "order_version")
  private Long orderVersion;

  @Column(name = "status")

  @Enumerated(STRING)
  private OrderApprovalStatus status;
}
