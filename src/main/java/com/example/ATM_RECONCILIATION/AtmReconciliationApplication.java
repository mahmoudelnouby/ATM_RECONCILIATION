package com.example.ATM_RECONCILIATION;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"com.example.ATM_RECONCILIATION"})
@EntityScan("com.example.ATM_RECONCILIATION.*")
@EnableJpaRepositories({"com.example.ATM_RECONCILIATION.*"})
public class AtmReconciliationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmReconciliationApplication.class, args);
	}

}
