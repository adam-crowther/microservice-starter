package com.acroteq.ticketing.infrastructure.data.access.entity;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
@MappedSuperclass
public abstract class ReplicatedJpaEntity implements JpaEntity {

  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "version", nullable = false)
  private Long version;

  @Builder.Default
  @Embedded
  private EventIdJpaEmbedded eventId = new EventIdJpaEmbedded();
}
