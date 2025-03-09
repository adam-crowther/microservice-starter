package com.acroteq.ticketing.application.mapper.id

import com.acroteq.application.mapper.id.PaymentIdMapper
import com.acroteq.domain.valueobject.PaymentId
import groovy.transform.CompileDynamic
import org.mapstruct.factory.Mappers
import spock.lang.Specification

@CompileDynamic
class PaymentIdMapperSpec extends Specification {

  static final String STRING_ID = '6789'
  static final Long LONG_ID = 6789
  static final PaymentId PAYMENT_ID = PaymentId.of(LONG_ID)

  PaymentIdMapper mapper = Mappers.getMapper(PaymentIdMapper)

  def 'long id is converted correctly'() {
    when:
    def paymentId = mapper.convertLongToId(LONG_ID)
    then:
    paymentId.value == LONG_ID
  }

  def 'string id is converted correctly'() {
    when:
    def paymentId = mapper.convertStringToId(STRING_ID)
    then:
    paymentId.value == LONG_ID
  }

  def 'id is converted to long correctly'() {
    when:
    def longId = mapper.convertEntityIdToLong(PAYMENT_ID)
    then:
    longId == LONG_ID
  }

  def 'id is converted to string correctly'() {
    when:
    def stringId = mapper.convertEntityIdToString(PAYMENT_ID)
    then:
    stringId == STRING_ID
  }

  def 'null string input is converted to null'() {
    when:
    def paymentId = mapper.convertStringToId(null)
    then:
    paymentId == null
  }

  def 'null long input is converted to null'() {
    when:
    def paymentId = mapper.convertLongToId(null)
    then:
    paymentId == null
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
