package com.acroteq.ticketing.kafka.config;

import com.acroteq.ticketing.kafka.consumer.properties.KafkaConsumerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@EnableConfigurationProperties(KafkaConsumerConfig.class)
public class KafkaConfiguration {}
