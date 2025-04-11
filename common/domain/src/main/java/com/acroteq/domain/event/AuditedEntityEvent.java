package com.acroteq.domain.event;

import com.acroteq.domain.valueobject.Audit;

import java.util.Optional;

public interface AuditedEntityEvent extends EntityEvent {

  Optional<Audit> getAudit();
}
