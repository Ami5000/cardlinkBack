package com.api.cardlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CardlinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardlinkApplication.class, args);
	}

}
  