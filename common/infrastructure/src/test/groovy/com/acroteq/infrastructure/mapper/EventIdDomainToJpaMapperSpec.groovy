package com.acroteq.infrastructure.mapper

import com.acroteq.domain.valueobject.EventId
import groovy.transform.CompileDynamic
import org.mapstruct.factory.Mappers
import spock.lang.Specification

@CompileDynamic
class EventIdDomainToJpaMapperSpec extends Specification {

  static final Integer PARTITION = 3L
  static final Long OFFSET = 7447

  EventIdJpaMapper mapper = Mappers.getMapper(EventIdJpaMapper)

  def 'all attributes in eventId should be converted from domain to jpa model'() {
    given:
    def eventId = EventId.builder()
          .partition(PARTITION)
          .offset(OFFSET)
          .build()
    when:
    def jpaEvent = mapper.convert(eventId)

    then:
    jpaEvent.partition == PARTITION
    jpaEvent.offset == OFFSET
  }

  def 'when convertMessageToDto is given a null reference, it should return null'() {
    when:
    def jpaEvent = mapper.convert(null)

    then:
    jpaEvent == null
  }
}
