package com.acroteq.application.mapper

import com.acroteq.domain.valueobject.Audit
import groovy.transform.CompileDynamic
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.time.Instant

@CompileDynamic
class AuditToDtoMapperSpec extends Specification {

  static final Instant CREATED_TIMESTAMP = Instant.ofEpochSecond(1683832517)
  static final Instant LAST_MODIFIED_TIMESTAMP = Instant.ofEpochSecond(1678344706)

  AuditToDtoMapper mapper = Mappers.getMapper(AuditToDtoMapper)

  def 'audit values are converted'() {
    given:
    def audit = Audit.builder().createdTimestamp(CREATED_TIMESTAMP).lastModifiedTimestamp(LAST_MODIFIED_TIMESTAMP).build()
    when:
    def dto = mapper.convertAuditToDto(audit)
    then:
    dto.createdTimestamp == CREATED_TIMESTAMP
    dto.lastModifiedTimestamp == LAST_MODIFIED_TIMESTAMP
  }

  def 'null values accepted'() {
    given:
    def audit = Audit.builder().createdTimestamp(null).lastModifiedTimestamp(null).build()
    when:
    def dto = mapper.convertAuditToDto(audit)
    then:
    dto.createdTimestamp == null
    dto.lastModifiedTimestamp == null
  }

  def 'null input returns null'() {
    when:
    def dto = mapper.convertAuditToDto(null)
    then:
    dto == null
  }
}
