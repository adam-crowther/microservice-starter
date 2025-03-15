package com.acroteq.application.mapper;

import static com.acroteq.domain.validation.ValidationResult.pass;
import static java.util.Collections.emptyList;

import com.acroteq.domain.validation.ValidationResult;
import jakarta.annotation.Nullable;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(config = MapstructConfig.class)
public interface ValidationResultMapper {

  @Nullable
  default ValidationResult convert(@Nullable final List<String> failureMessages) {
    return Optional.ofNullable(failureMessages)
                   .map(ValidationResult::of)
                   .orElse(pass());
  }

  @Nullable
  default List<String> convert(@Nullable final ValidationResult result) {
    return Optional.ofNullable(result)
                   .map(ValidationResult::getFailures)
                   .orElse(emptyList());
  }
}
