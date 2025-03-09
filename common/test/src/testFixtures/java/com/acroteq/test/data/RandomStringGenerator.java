package com.acroteq.test.data;

import static java.nio.charset.Charset.defaultCharset;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


@RequiredArgsConstructor
@Component
public class RandomStringGenerator {

  private static final String WORD_LIST_FILE_NAME = "/word_list.txt";
  private static final List<String> WORD_LIST = loadWordList();
  private final RandomIntegerGenerator randomInteger;

  @SneakyThrows
  private static List<String> loadWordList() {

    try (InputStream inputStream = RandomStringGenerator.class.getResourceAsStream(WORD_LIST_FILE_NAME);
         InputStreamReader streamReader = new InputStreamReader(inputStream, defaultCharset());
         BufferedReader bufferedReader = new BufferedReader(streamReader)) {
      return bufferedReader.lines()
                           .toList();
    }
  }

  public String getRandomString() {
    final int wordCount = WORD_LIST.size();
    final int index = randomInteger.getPositiveWithUpperBound(wordCount);
    return WORD_LIST.get(index);
  }
}
