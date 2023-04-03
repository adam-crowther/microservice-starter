package com.acroteq.food.ordering.system.common.application.mapper;

import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ValidationResultMapper {

  default ValidationResult convert(final List<String> failureMessages) {
    return ValidationResult.of(failureMessages);
  }

  default List<String> convert(final ValidationResult result) {
    return result.getFailureMessages();
  }
}
