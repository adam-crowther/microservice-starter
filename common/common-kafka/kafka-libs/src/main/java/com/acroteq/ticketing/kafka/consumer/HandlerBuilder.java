package com.acroteq.ticketing.kafka.consumer;

public interface HandlerBuilder<BuilderT extends HandlerBuilder<BuilderT>> {

  BuilderT self();
}
