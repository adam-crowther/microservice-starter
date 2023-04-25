package com.acroteq.ticketing.airline.service.domain.ports.input.service;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineResponseDto;
import com.acroteq.ticketing.airline.service.domain.dto.get.AirlineDto;
import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateAirlineCommandDto;
import jakarta.validation.Valid;

public interface AirlineApplicationService {

  AirlineDto getAirline(Long airlineId);

  CreateAirlineResponseDto createAirline(@Valid CreateAirlineCommandDto createAirlineCommandDto);

  void updateAirline(@Valid UpdateAirlineCommandDto createAirlineCommandDto);

  void deleteAirline(Long airlineId);
}
