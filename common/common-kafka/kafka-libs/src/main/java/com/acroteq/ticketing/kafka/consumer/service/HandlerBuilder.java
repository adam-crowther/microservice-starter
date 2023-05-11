package com.acroteq.ticketing.kafka.consumer.service;

public interface HandlerBuilder<BuilderT extends HandlerBuilder<BuilderT>> {

  BuilderT self();
}
