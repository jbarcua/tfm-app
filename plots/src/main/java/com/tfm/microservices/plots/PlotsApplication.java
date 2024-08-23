package com.tfm.microservices.plots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PlotsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlotsApplication.class, args);
	}

}
