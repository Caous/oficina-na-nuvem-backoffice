package com.oficinaapp.oficina_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OficinaAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OficinaAppApplication.class, args);
	}

}
