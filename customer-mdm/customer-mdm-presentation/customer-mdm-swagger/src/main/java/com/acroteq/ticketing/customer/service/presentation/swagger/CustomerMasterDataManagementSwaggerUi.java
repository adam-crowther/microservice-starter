package com.acroteq.ticketing.customer.service.presentation.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class CustomerMasterDataManagementSwaggerUi implements WebMvcConfigurer {

  public static void main(final String[] args) {
    SpringApplication.run(CustomerMasterDataManagementSwaggerUi.class, args);
  }

  @Override
  public void addViewControllers(final ViewControllerRegistry registry) {
    registry.addViewController("/")
            .setViewName("redirect:/index.html");
  }
}
