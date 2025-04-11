package com.acroteq.application.mapper;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Optional;

@Mapper(config = MapstructConfig.class)
public class OptionalMapper {

  @NotNull
  @Named("mandatory")
  public <T> T getMandatoryValue(@NotNull final Optional<T> input) {
    return input.orElseThrow(NullPointerException::new);
  }

  @Nullable
  @Named("optional")
  public <T> T getOptionalValue(@NotNull final Optional<T> input) {
    return input.orElse(null);
  }
}
