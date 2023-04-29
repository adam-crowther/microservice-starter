package com.acroteq.ticketing.order.service.presentation.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@Configuration
public class OrderServiceSwaggerUi implements WebMvcConfigurer {

  public static void main(final String[] args) {
    SpringApplication.run(OrderServiceSwaggerUi.class, args);
  }

  @Override
  public void addViewControllers(final ViewControllerRegistry registry) {
    registry.addViewController("/")
            .setViewName("redirect:/index.html");
  }
}
