package com.sheetvision.sheetvision.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.sheetvision.sheetvision.api.model")
@EnableJpaRepositories(basePackages = "com.sheetvision.sheetvision.api.repository")
@ConfigurationPropertiesScan(basePackages = "com.sheetvision.sheetvision.api.config")
public class SheetVisionApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(SheetVisionApiApplication.class, args);
	}
}