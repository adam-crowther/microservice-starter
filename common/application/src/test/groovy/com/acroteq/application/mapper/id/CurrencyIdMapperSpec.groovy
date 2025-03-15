package com.acroteq.application.mapper.id

import com.acroteq.domain.valueobject.CurrencyId
import groovy.transform.CompileDynamic
import org.mapstruct.factory.Mappers
import spock.lang.Specification

@CompileDynamic
class CurrencyIdMapperSpec extends Specification {

  static final String STRING_ID = '6789'
  static final CurrencyId CURRENCY_ID = CurrencyId.of(STRING_ID)

  CurrencyIdMapper mapper = Mappers.getMapper(CurrencyIdMapper)

  def 'string id is converted correctly'() {
    when:
    def currencyId = mapper.convertStringToId(STRING_ID)
    then:
    currencyId.value == STRING_ID
  }

  def 'id is converted to string correctly'() {
    when:
    def stringId = mapper.convertEntityIdToString(CURRENCY_ID)
    then:
    stringId == STRING_ID
  }

  def 'null string input is converted to null'() {
    when:
    def currencyId = mapper.convertStringToId(null)
    then:
    currencyId == null
  }

  def 'null id input is converted to null string'() {
    when:
    def stringId = mapper.convertEntityIdToString(null)
    then:
    stringId == null
  }
}
