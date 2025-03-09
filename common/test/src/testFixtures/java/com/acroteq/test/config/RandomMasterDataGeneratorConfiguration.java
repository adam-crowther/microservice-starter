package com.acroteq.test.config;

import com.acroteq.test.data.IdGenerator;
import com.acroteq.test.data.RandomBigDecimalGenerator;
import com.acroteq.test.data.RandomHolder;
import com.acroteq.test.data.RandomInstantGenerator;
import com.acroteq.test.data.RandomIntegerGenerator;
import com.acroteq.test.data.RandomLongGenerator;
import com.acroteq.test.data.RandomStringGenerator;
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
