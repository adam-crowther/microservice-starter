package com.acroteq.infrastructure.mapper

import groovy.transform.CompileDynamic
import org.mapstruct.factory.Mappers
import spock.lang.Specification

@CompileDynamic
class EventIdDtoMapperSpec extends Specification {

  static final Integer PARTITION = 3L
  static final Long OFFSET = 7447

  EventIdDtoMapper mapper = Mappers.getMapper(EventIdDtoMapper)

  def 'all attributes in eventId should be converted from domain to jpa model'() {
    when:
    def dto = mapper.convert(PARTITION, OFFSET)

    then:
    dto.partition == PARTITION
    dto.offset == OFFSET
  }
}
