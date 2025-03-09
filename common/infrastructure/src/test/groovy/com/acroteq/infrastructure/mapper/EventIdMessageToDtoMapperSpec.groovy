package com.acroteq.ticketing.infrastructure.mapper

import com.acroteq.infrastructure.mapper.EventIdMessageToDtoMapper
import groovy.transform.CompileDynamic
import org.mapstruct.factory.Mappers
import spock.lang.Specification

@CompileDynamic
class EventIdMessageToDtoMapperSpec extends Specification {

  static final Integer PARTITION = 3L
  static final Long OFFSET = 7447

  EventIdMessageToDtoMapper mapper = Mappers.getMapper(EventIdMessageToDtoMapper)

  def 'all attributes in eventId should be converted from domain to jpa model'() {
    when:
    def dto = mapper.convertMessageToDto(PARTITION, OFFSET)

    then:
    dto.partition == PARTITION
    dto.offset == OFFSET
  }
}
