package com.acroteq.ticketing.infrastructure.data.access.entity;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public final class AuditJpaEmbedded {

  @CreatedDate
  @Column(name = "created_timestamp", nullable = false, updatable = false)
  private Instant createdTimestamp;

  @CreatedBy
  @Column(name = "created_by", updatable = false)
  private String createdBy;

  @LastModifiedDate
  @Column(name = "last_modified_timestamp", nullable = false)
  private Instant lastModifiedTimestamp;

  @LastModifiedBy
  @Column(name = "lasted_modified_by")
  private String lastModifiedBy;
}
