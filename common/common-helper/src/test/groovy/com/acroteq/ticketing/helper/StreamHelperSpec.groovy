package com.acroteq.ticketing.helper

import com.acroteq.ticketing.exception.MoreThanOneItemInStreamException
import groovy.transform.CompileDynamic
import spock.lang.Specification

import java.util.stream.Stream

import static com.acroteq.ticketing.helper.StreamHelper.toSingleItem
import static com.acroteq.ticketing.helper.StreamHelper.withCounter

@CompileDynamic
class StreamHelperSpec extends Specification {

  static final String ITEM_1 = 'item-1'
  static final String ITEM_2 = 'item-2'

  def 'when there is exactly one item in the stream, reduce(toSingleItem()) should return an Optional with that item'() {
    given:
    def stream = Stream.of(ITEM_1)
    when:
    def item = stream.reduce { toSingleItem() }
    then:
    item.isPresent()
    item.get() == ITEM_1
  }

  def 'when there is more than one item in the stream, reduce(toSingleItem()) should throw an exception'() {
    given:
    def stream = Stream.of(ITEM_1, ITEM_2)
    when:
    stream.reduce(toSingleItem())
    then:
    thrown(MoreThanOneItemInStreamException)
  }

  def 'when there the stream is empty, reduce(toSingleItem()) should return an empty Optional'() {
    given:
    def stream = Stream.of()
    when:
    def item = stream.reduce(toSingleItem())
    then:
    item.isEmpty()
  }

  def 'withCounter adds an integer count parameter to the stream'() {
    given:
    def stream = Stream.of(ITEM_1, ITEM_2)
    def result = [:]

    when:
    stream.forEach withCounter { count, item -> result[count] = item }

    then:
    result[0] == ITEM_1
    result[1] == ITEM_2
  }
}
