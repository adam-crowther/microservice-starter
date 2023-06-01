package com.acroteq.ticketing.test.config;

import com.acroteq.ticketing.test.data.IdGenerator;
import com.acroteq.ticketing.test.data.RandomBigDecimalGenerator;
import com.acroteq.ticketing.test.data.RandomHolder;
import com.acroteq.ticketing.test.data.RandomInstantGenerator;
import com.acroteq.ticketing.test.data.RandomIntegerGenerator;
import com.acroteq.ticketing.test.data.RandomLongGenerator;
import com.acroteq.ticketing.test.data.RandomStringGenerator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({ IdGenerator.class,
          RandomBigDecimalGenerator.class,
          RandomHolder.class,
          RandomInstantGenerator.class,
          RandomIntegerGenerator.class,
          RandomLongGenerator.class,
          RandomStringGenerator.class })
public class RandomMasterDataGeneratorConfiguration {}
