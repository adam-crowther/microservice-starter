package com.acroteq.application.mapper;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG;
import static org.mapstruct.ReportingPolicy.ERROR;

import org.mapstruct.MapperConfig;

@MapperConfig(unmappedTargetPolicy = ERROR,
              componentModel = SPRING,
              injectionStrategy = CONSTRUCTOR,
              mappingInheritanceStrategy = AUTO_INHERIT_ALL_FROM_CONFIG)
public interface MapstructConfig {}
