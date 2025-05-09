package com.acroteq.domain.exception;

import com.acroteq.domain.valueobject.EntityId;
import lombok.NonNull;

public class EntityNotFoundException extends NotFoundException {

  private static final String I18N_CODE = "problem.entity.not.found";
  private static final String MESSAGE = "Entity not found %s";

  private final EntityId entityId;

  public EntityNotFoundException(@NonNull final EntityId entityId) {
    super(String.format(MESSAGE, entityId));
    this.entityId = entityId;
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ entityId.toString() };
  }
}
