package com.acroteq.ticketing.application.mapper.id

import com.acroteq.ticketing.domain.valueobject.OrderId
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class OrderIdMapperSpec extends Specification {

  static final String STRING_ID = "6789"
  static final Long LONG_ID = 6789
  static final OrderId ORDER_ID = OrderId.of(LONG_ID)

  OrderIdMapper mapper = Mappers.getMapper(OrderIdMapper)

  def "long id is converted correctly"() {
    when:
      def orderId = mapper.convertLongToId(LONG_ID)
    then:
      orderId.getValue() == LONG_ID
  }

  def "string id is converted correctly"() {
    when:
      def orderId = mapper.convertStringToId(STRING_ID)
    then:
      orderId.getValue() == LONG_ID
  }

  def "id is converted to long correctly"() {
    when:
      def longId = mapper.convertEntityIdToLong(ORDER_ID)
    then:
      longId == LONG_ID
  }

  def "id is converted to string correctly"() {
    when:
      def stringId = mapper.convertEntityIdToString(ORDER_ID)
    then:
      stringId == STRING_ID
  }

  def "null string input is converted to null"() {
    when:
      def orderId = mapper.convertStringToId(null)
    then:
      orderId == null
  }

  def "null long input is converted to null"() {
    when:
      def orderId = mapper.convertLongToId(null)
    then:
      orderId == null
  }

  def "null id input is converted to null long"() {
    when:
      def longId = mapper.convertEntityIdToLong(null)
    then:
      longId == null
  }

  def "null id input is converted to null string"() {
    when:
      def stringId = mapper.convertEntityIdToString(null)
    then:
      stringId == null
  }
}
