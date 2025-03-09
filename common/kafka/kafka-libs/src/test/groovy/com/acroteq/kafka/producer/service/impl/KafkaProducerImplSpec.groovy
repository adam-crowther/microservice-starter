package com.acroteq.ticketing.kafka.producer.service.impl

import com.acroteq.ticketing.kafka.producer.exception.SynchronousKafkaProducerException
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer
import groovy.transform.CompileDynamic
import groovy.util.logging.Slf4j
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.KafkaException
import org.apache.kafka.common.TopicPartition
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import spock.lang.Specification

import java.util.concurrent.CompletableFuture

@CompileDynamic
class KafkaProducerImplSpec extends Specification {

  static final String TOPIC = 'topic-name'
  static final Long ID = 236
  static final String KEY = '236'
  static final Integer PARTITION = 3
  static final Long OFFSET = 43253

  def topicPartition = new TopicPartition(TOPIC, PARTITION)
  def metadata = new RecordMetadata(topicPartition, OFFSET, 0, 0, 0, 0)

  KafkaTemplate<String, SpecificRecord> kafkaTemplate = Mock(KafkaTemplate)
  KafkaProducer<SpecificRecord> producer = new KafkaProducerImpl<>(kafkaTemplate)

  def 'when a message is sent successfully, the callback should be invoked'() {
    given:
    def callbackHandler = Spy(new CallbackHandler())
    def message = Mock(SpecificRecord)
    def future = new CompletableFuture()
    kafkaTemplate.send(TOPIC, KEY, message) >> future

    def sendResult = Mock(SendResult)
    sendResult.recordMetadata >> metadata

    when:
    producer.send(TOPIC, ID, message, callbackHandler::callback)
    def futureComplete = future.complete(sendResult)

    then:
    futureComplete
    !callbackHandler.exceptionThrown
    1 * callbackHandler.callback(sendResult, null)
  }

  def 'a synchronous exception while sending the message should just be bubbled up'() {
    given:
    def callbackHandler = Spy(new CallbackHandler())
    def message = Mock(SpecificRecord)
    kafkaTemplate.send(TOPIC, KEY, message) >> { throw new KafkaException() }

    def sendResult = Mock(SendResult)
    when:
    producer.send(TOPIC, ID, message, callbackHandler::callback)

    then:
    thrown(SynchronousKafkaProducerException)
    !callbackHandler.exceptionThrown
    0 * callbackHandler.callback(sendResult, null)
  }

  def 'when there was an asynchronous exception sending the message, the callback should be invoked with the exception'() {
    given:
    def callbackHandler = Spy(new CallbackHandler())
    def message = Mock(SpecificRecord)
    def future = new CompletableFuture()
    kafkaTemplate.send(TOPIC, KEY, message) >> future

    def sendResult = Mock(SendResult)
    sendResult.recordMetadata >> metadata

    def exception = Mock(RuntimeException)

    when:
    producer.send(TOPIC, ID, message, callbackHandler::callback)
    def futureComplete = future.completeExceptionally(exception)

    then:
    futureComplete
    callbackHandler.exceptionThrown
    0 * callbackHandler.callback(sendResult, null)
  }

  def 'before the class is destroyed it should close/destroy the kafka template'() {
    when:
    producer.close()
    then:
    1 * kafkaTemplate.destroy()
  }
}

@CompileDynamic
@Slf4j
class CallbackHandler {

  boolean exceptionThrown = false

  void callback(SendResult<String, SpecificRecord> sendResult, Throwable exception) {
    if (exception == null) {
      log.info 'Message from {}', sendResult.recordMetadata.topic()
    } else {
      log.info 'Exception thrown: {}',  exception.class.simpleName
      exceptionThrown = true
    }
  }
}
