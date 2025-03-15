package com.acroteq.application.mapper.id

import com.acroteq.domain.valueobject.AirlineId
import groovy.transform.CompileDynamic
import org.mapstruct.factory.Mappers
import spock.lang.Specification

/**
 * Mapstruct removes boilerplate and makes code easier to read, but the tradeoff is that we need to verify the contract.
 **/
@CompileDynamic
class AirlineIdMapperSpec extends Specification {

  static final String STRING_ID = '6789'
  static final Long LONG_ID = 6789
  static final AirlineId AIRLINE_ID = AirlineId.of(LONG_ID)

  AirlineIdMapper mapper = Mappers.getMapper(AirlineIdMapper)

  def 'long id is converted correctly'() {
    when:
    def airlineId = mapper.convertLongToId(LONG_ID)
    then:
    airlineId.value == LONG_ID
  }

  def 'string id is converted correctly'() {
    when:
    def airlineId = mapper.convertStringToId(STRING_ID)
    then:
    airlineId.value == LONG_ID
  }

  def 'id is converted to long correctly'() {
    when:
    def longId = mapper.convertEntityIdToLong(AIRLINE_ID)
    then:
    longId == LONG_ID
  }

  def 'id is converted to string correctly'() {
    when:
    def stringId = mapper.convertEntityIdToString(AIRLINE_ID)
    then:
    stringId == STRING_ID
  }

  def 'null string input is converted to null'() {
    when:
    def airlineId = mapper.convertStringToId(null)
    then:
    airlineId == null
  }

  def 'null long input is converted to null'() {
    when:
    def airlineId = mapper.convertLongToId(null)
    then:
    airlineId == null
  }

  def 'null id input is converted to null long'() {
    when:
    def longId = mapper.convertEntityIdToLong(null)
    then:
    longId == null
  }

  def 'null id input is converted to null string'() {
    when:
    def stringId = mapper.convertEntityIdToString(null)
    then:
    stringId == null
  }
}
