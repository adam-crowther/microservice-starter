package com.acroteq.ticketing.order.service.config;

import com.acroteq.ticketing.order.service.data.AirlineMasterDataGenerator;
import com.acroteq.ticketing.order.service.data.CreateOrderCommandGenerator;
import com.acroteq.ticketing.order.service.data.CustomerMasterDataGenerator;
import com.acroteq.ticketing.order.service.data.FlightMasterDataGenerator;
import com.acroteq.ticketing.test.config.RandomMasterDataGeneratorConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({ RandomMasterDataGeneratorConfiguration.class,
          AirlineMasterDataGenerator.class,
          FlightMasterDataGenerator.class,
          CustomerMasterDataGenerator.class,
          CreateOrderCommandGenerator.class })
public class MasterDataGeneratorConfiguration {}
