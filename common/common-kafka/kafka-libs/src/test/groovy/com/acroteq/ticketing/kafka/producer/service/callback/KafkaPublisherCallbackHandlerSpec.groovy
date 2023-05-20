package com.acroteq.ticketing.kafka.producer.service.callback

import groovy.transform.CompileDynamic
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.TopicPartition
import org.springframework.kafka.support.SendResult
import spock.lang.Specification

@CompileDynamic
class KafkaPublisherCallbackHandlerSpec extends Specification {

  static final String KEY = 'key'
  static final String TOPIC = 'topic'
  static final Integer PARTITION = 3
  static final Long OFFSET = 43253

  def topicPartition = new TopicPartition(TOPIC, PARTITION)
  def metadata = new RecordMetadata(topicPartition, OFFSET, 0, 0, 0, 0)

  KafkaPublisherCallbackHandler callbackHandler = new KafkaPublisherCallbackHandler()

  def 'when no exception is returned, the callback handler simply logs the response at the appropriate log level'() {
    given:
    def record = Mock(ProducerRecord<Long, SpecificRecord>)
    1 * record.key() >> KEY
    def sendResult = new SendResult(record, metadata)

    when:
    def output = callbackHandler.callback(sendResult, null)

    then:
    noExceptionThrown()
    output == sendResult
  }

  def 'when an exception is returned, the callback handler simply logs the response at the appropriate log level'() {
    given:
    def record = Mock(ProducerRecord<Long, SpecificRecord>)
    1 * record.key() >> KEY
    def sendResult = new SendResult(record, metadata)
    def exception = Mock(Exception)

    when:
    def output = callbackHandler.callback(sendResult, exception)

    then:
    noExceptionThrown()
    output == sendResult
  }

  def 'when the returned record is null, no exception is thrown'() {
    given:
    def exception = Mock(Exception)

    when:
    def output = callbackHandler.callback(null, exception)

    then:
    noExceptionThrown()
    output == null
  }

  def 'when the returned record and exception are both null, no exception is thrown'() {
    when:
    def output = callbackHandler.callback(null, null)

    then:
    noExceptionThrown()
    output == null
  }
}
