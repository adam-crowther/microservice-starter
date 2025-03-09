package com.acroteq.ticketing.application.mapper.id

import com.acroteq.application.mapper.id.CustomerIdMapper
import com.acroteq.domain.valueobject.CustomerId
import groovy.transform.CompileDynamic
import org.mapstruct.factory.Mappers
import spock.lang.Specification

@CompileDynamic
class CustomerIdMapperSpec extends Specification {

  static final String STRING_ID = '6789'
  static final Long LONG_ID = 6789
  static final CustomerId CUSTOMER_ID = CustomerId.of(LONG_ID)

  CustomerIdMapper mapper = Mappers.getMapper(CustomerIdMapper)

  def 'long id is converted correctly'() {
    when:
    def customerId = mapper.convertLongToId(LONG_ID)
    then:
    customerId.value == LONG_ID
  }

  def 'string id is converted correctly'() {
    when:
    def customerId = mapper.convertStringToId(STRING_ID)
    then:
    customerId.value == LONG_ID
  }

  def 'id is converted to long correctly'() {
    when:
    def longId = mapper.convertEntityIdToLong(CUSTOMER_ID)
    then:
    longId == LONG_ID
  }

  def 'id is converted to string correctly'() {
    when:
    def stringId = mapper.convertEntityIdToString(CUSTOMER_ID)
    then:
    stringId == STRING_ID
  }

  def 'null string input is converted to null'() {
    when:
    def customerId = mapper.convertStringToId(null)
    then:
    customerId == null
  }

  def 'null long input is converted to null'() {
    when:
    def customerId = mapper.convertLongToId(null)
    then:
    customerId == null
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
