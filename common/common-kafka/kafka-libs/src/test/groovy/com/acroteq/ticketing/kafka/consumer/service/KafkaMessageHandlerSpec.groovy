package com.acroteq.ticketing.kafka.consumer.service

import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper
import com.acroteq.ticketing.kafka.consumer.exception.MessageHandlerParameterCountMismatchException
import com.acroteq.ticketing.kafka.consumer.exception.UnsupportedMessageTypeException
import com.acroteq.ticketing.kafka.dto.TestDto
import groovy.transform.CompileDynamic
import org.apache.avro.Schema
import org.apache.avro.specific.SpecificRecord
import org.springframework.kafka.listener.BatchListenerFailedException
import spock.lang.Shared
import spock.lang.Specification

import java.util.function.Consumer

@CompileDynamic
class KafkaMessageHandlerSpec extends Specification {

  static final String KNOWN_MESSAGE_TYPE = 'message-type'
  static final String UNKNOWN_MESSAGE_TYPE = 'unknown-message-type'

  static final String KEY = 'key'
  static final Integer PARTITION = 3
  static final Long OFFSET = 345345

  @Shared final SpecificRecord message = Mock(SpecificRecord)

  MessageToDtoMapper<SpecificRecord, TestDto> mapper = Mock()
  Consumer<TestDto> consumer = Mock()

  KafkaMessageHandler messageHandler = KafkaMessageHandler.builder()
        .addMessageType(KNOWN_MESSAGE_TYPE, mapper, consumer)
        .build()

  def 'The consumer should be invoked for each message that has a known message type'() {
    given:
    def record = Mock(SpecificRecord)
    def schema = Mock(Schema)
    record.schema >> schema
    schema.name >> KNOWN_MESSAGE_TYPE

    def dto = Mock(TestDto)
    4 * mapper.convertMessageToDto(record, PARTITION, OFFSET) >> dto

    def records = [record, record, record, record]
    def keys = [KEY, KEY, KEY, KEY]
    def partitions = [PARTITION, PARTITION, PARTITION, PARTITION]
    def offsets = [OFFSET, OFFSET, OFFSET, OFFSET]

    when:
    messageHandler.processMessages(records, keys, partitions, offsets)

    then:
    4 * consumer.accept(dto)
  }

  def 'A mismatch in the number of keys, partitions or offsets should throw an exception'() {
    given:
    def record = Mock(SpecificRecord)
    def schema = Mock(Schema)
    record.schema >> schema
    schema.name >> KNOWN_MESSAGE_TYPE

    when:
    messageHandler.processMessages([record], keys, partitions, offsets)

    then:
    thrown(MessageHandlerParameterCountMismatchException)

    where:
    keys  | partitions  | offsets
    []    | [PARTITION] | [OFFSET]
    [KEY] | []          | [OFFSET]
    [KEY] | [PARTITION] | []
  }

  def 'A null key should not throw an exception'() {
    given:
    def record = Mock(SpecificRecord)
    def schema = Mock(Schema)
    record.schema >> schema
    schema.name >> KNOWN_MESSAGE_TYPE

    def dto = Mock(TestDto)
    1 * mapper.convertMessageToDto(record, _, _) >> dto

    when:
    messageHandler.processMessages([record], [null], [PARTITION], [OFFSET])

    then:
    1 * consumer.accept(dto)
  }

  def 'A null partition or offset should throw an exception'() {
    given:
    def record = Mock(SpecificRecord)
    def schema = Mock(Schema)
    record.schema >> schema
    schema.name >> KNOWN_MESSAGE_TYPE

    when:
    messageHandler.processMessages([record], [KEY], [partition], [offset])

    then:
    def exception = thrown(BatchListenerFailedException)
    exception.cause instanceof NullPointerException

    where:
    partition | offset
    null      | OFFSET
    PARTITION | null
  }

  def 'An unknown message type should throw an exception'() {
    given:
    def record = Mock(SpecificRecord)
    def schema = Mock(Schema)
    record.schema >> schema
    schema.name >> UNKNOWN_MESSAGE_TYPE

    def dto = Mock(TestDto)
    mapper.convertMessageToDto(record, PARTITION, OFFSET) >> dto

    def records = [record]
    def keys = [KEY]
    def partitions = [PARTITION]
    def offsets = [OFFSET]

    when:
    messageHandler.processMessages(records, keys, partitions, offsets)

    then:
    def exception = thrown(BatchListenerFailedException)
    exception.cause instanceof UnsupportedMessageTypeException
  }

  def 'An invocation with no records should not do anything'() {
    given:
    def records = []
    def keys = []
    def partitions = []
    def offsets = []

    when:
    messageHandler.processMessages(records, keys, partitions, offsets)

    then:
    0 * consumer.accept(_)
  }

  def 'A null message should throw an exception'() {
    when:
    messageHandler.processMessages([null], [KEY], [PARTITION], [OFFSET])

    then:
    def exception = thrown(BatchListenerFailedException)
    exception.cause instanceof UnsupportedMessageTypeException
  }

  def 'A null input parameter should throw an exception'() {
    when:
    messageHandler.processMessages(messages, keys, partitions, offsets)

    then:
    thrown(NullPointerException)

    where:
    messages  | keys  | partitions  | offsets
    null      | [KEY] | [PARTITION] | [OFFSET]
    [message] | null  | [PARTITION] | [OFFSET]
    [message] | [KEY] | null        | [OFFSET]
    [message] | [KEY] | [PARTITION] | null
  }
}
