package com.acroteq.ticketing.container.properties;

import lombok.NonNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "security.jwt")
public class JwtMappingProperties {

  @NonNull String authorityPrefix;
  @NonNull String principalClaimName;
}
