package com.acroteq.ticketing.application.mapper.id

import com.acroteq.ticketing.domain.valueobject.FlightId
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class FlightIdMapperSpec extends Specification {

  static final String STRING_ID = "6789"
  static final Long LONG_ID = 6789
  static final FlightId FLIGHT_ID = FlightId.of(LONG_ID)

  FlightIdMapper mapper = Mappers.getMapper(FlightIdMapper)

  def "long id is converted correctly"() {
    when:
      def flightId = mapper.convertLongToId(LONG_ID)
    then:
      flightId.getValue() == LONG_ID
  }

  def "string id is converted correctly"() {
    when:
      def flightId = mapper.convertStringToId(STRING_ID)
    then:
      flightId.getValue() == LONG_ID
  }

  def "id is converted to long correctly"() {
    when:
      def longId = mapper.convertEntityIdToLong(FLIGHT_ID)
    then:
      longId == LONG_ID
  }

  def "id is converted to string correctly"() {
    when:
      def stringId = mapper.convertEntityIdToString(FLIGHT_ID)
    then:
      stringId == STRING_ID
  }

  def "null string input is converted to null"() {
    when:
      def flightId = mapper.convertStringToId(null)
    then:
      flightId == null
  }

  def "null long input is converted to null"() {
    when:
      def flightId = mapper.convertLongToId(null)
    then:
      flightId == null
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
