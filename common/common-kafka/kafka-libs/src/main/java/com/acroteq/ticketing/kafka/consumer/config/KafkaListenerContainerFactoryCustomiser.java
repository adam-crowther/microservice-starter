package com.acroteq.ticketing.kafka.consumer.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.config.AbstractKafkaListenerContainerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(prefix = "spring.kafka.consumer", name = "auto-offset-reset")
@Component
public class KafkaListenerContainerFactoryCustomiser {

  public KafkaListenerContainerFactoryCustomiser(final AbstractKafkaListenerContainerFactory<?, ?, ?> defaultFactory,
                                                 final DefaultErrorHandler kafkaErrorHandler) {

    defaultFactory.setContainerCustomizer(container -> container.setCommonErrorHandler(kafkaErrorHandler));
  }
}
