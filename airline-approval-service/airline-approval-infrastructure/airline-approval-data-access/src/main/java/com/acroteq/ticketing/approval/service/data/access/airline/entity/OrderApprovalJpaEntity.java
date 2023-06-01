package com.acroteq.ticketing.approval.service.data.access.airline.entity;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus;
import com.acroteq.ticketing.infrastructure.data.access.entity.PrimaryJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "order_approvals")
@Entity
public class OrderApprovalJpaEntity extends PrimaryJpaEntity {

  @ManyToOne(cascade = MERGE, optional = false)
  @JoinColumn(name = "airline_id", nullable = false)
  private AirlineJpaEntity airline;

  @Column(name = "order_id", nullable = false)
  private Long orderId;

  @Column(name = "order_version", nullable = false)
  private Long orderVersion;

  @Column(name = "status", nullable = false)
  @Enumerated(STRING)
  private OrderApprovalStatus status;
}
