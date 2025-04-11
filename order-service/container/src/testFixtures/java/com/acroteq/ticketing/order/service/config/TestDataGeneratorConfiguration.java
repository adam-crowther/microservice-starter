package com.acroteq.ticketing.order.service.config;

import com.acroteq.test.config.RandomMasterDataGeneratorConfiguration;
import com.acroteq.ticketing.order.service.client.model.CreateOrderGenerator;
import com.acroteq.ticketing.order.service.data.AirlineApprovalApprovedResponseMessageGenerator;
import com.acroteq.ticketing.order.service.data.AirlineMasterDataGenerator;
import com.acroteq.ticketing.order.service.data.CustomerMasterDataGenerator;
import com.acroteq.ticketing.order.service.data.FlightMasterDataGenerator;
import com.acroteq.ticketing.order.service.data.MasterDataFixture;
import com.acroteq.ticketing.order.service.data.PaymentPaidResponseMessageGenerator;
import com.acroteq.ticketing.order.service.data.TestDataGenerator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({ RandomMasterDataGeneratorConfiguration.class,
          AirlineMasterDataGenerator.class,
          FlightMasterDataGenerator.class,
          CustomerMasterDataGenerator.class,
          CreateOrderGenerator.class,
          PaymentPaidResponseMessageGenerator.class,
          AirlineApprovalApprovedResponseMessageGenerator.class,
          TestDataGenerator.class,
          MasterDataFixture.class })
public class TestDataGeneratorConfiguration {}
