package com.acroteq.infrastructure.data.access.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class PrimaryJpaEntity implements JpaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Version
  @Column(name = "version", nullable = false)
  private Long version;

  @Builder.Default
  @Embedded
  private final AuditJpaEmbedded audit = new AuditJpaEmbedded();
}
