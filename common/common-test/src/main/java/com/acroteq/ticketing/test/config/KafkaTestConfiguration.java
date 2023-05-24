package com.acroteq.ticketing.test.config;

import com.acroteq.ticketing.test.kafka.TestKafkaProducer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({ TestKafkaProducer.class })
public class KafkaTestConfiguration {}
