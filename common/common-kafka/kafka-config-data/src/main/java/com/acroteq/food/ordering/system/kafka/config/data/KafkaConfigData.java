package com.acroteq.food.ordering.system.kafka.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-config")
@EnableKafka
public class KafkaConfigData {
    private String bootstrapServers;
    private String schemaRegistryUrlKey;
    private String schemaRegistryUrl;
    private Integer numOfPartitions;
    private Short replicationFactor;
}
