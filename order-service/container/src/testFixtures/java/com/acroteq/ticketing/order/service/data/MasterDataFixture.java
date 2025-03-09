package com.acroteq.ticketing.order.service.data;

import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import com.acroteq.ticketing.kafka.airline.avro.model.Flight;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MasterDataFixture {

  private final TestDataGenerator testDataGenerator;
  private final MasterDataUploader<AirlineEventMessage> airlineMasterDataUploader;
  private final MasterDataUploader<CustomerEventMessage> customerMasterDataUploader;

  @Getter
  private AirlineEventMessage airline;
  @Getter
  private CustomerEventMessage customer;

  public void prepareMasterData() {
    airline = testDataGenerator.getAirlineEventMessage();
    airlineMasterDataUploader.upload(airline.getId(), airline);

    customer = testDataGenerator.getCustomerEventMessage();
    customerMasterDataUploader.upload(customer.getId(), customer);
  }

  public Long getAirlineId() {
    return airline.getId();
  }

  public Flight getFlight(final int index) {
    return airline.getFlights()
                  .get(index);
  }

  public Long getFlightId(final int index) {
    return airline.getFlights()
                  .get(index)
                  .getId();
  }

  public Long getCustomerId() {
    return customer.getId();
  }

}
