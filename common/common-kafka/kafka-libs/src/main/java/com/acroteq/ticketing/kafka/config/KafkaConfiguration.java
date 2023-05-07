package com.acroteq.ticketing.kafka.config;

import com.acroteq.ticketing.kafka.consumer.properties.KafkaConsumerConfig;
import com.acroteq.ticketing.kafka.producer.properties.KafkaProducerConfig;
import com.acroteq.ticketing.kafka.properties.KafkaConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@EnableConfigurationProperties({ KafkaConfig.class, KafkaConsumerConfig.class, KafkaProducerConfig.class })
public class KafkaConfiguration {}
