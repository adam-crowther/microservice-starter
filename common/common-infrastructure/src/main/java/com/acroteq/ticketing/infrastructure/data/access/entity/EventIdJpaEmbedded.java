package com.acroteq.ticketing.infrastructure.data.access.entity;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class EventIdJpaEmbedded {

  @Column(name = "kafka_partition", nullable = false)
  private Integer partition;

  @Column(name = "kafka_offset", nullable = false)
  private Long offset;
}
