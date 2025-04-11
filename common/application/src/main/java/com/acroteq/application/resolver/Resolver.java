package com.acroteq.application.resolver;

public interface Resolver<KeyT, TypeT> {

  TypeT resolve(KeyT key);
}
