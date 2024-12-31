package com.truxxkart.promotionservice_v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PromotionserviceV1Application {

	public static void main(String[] args) {
		SpringApplication.run(PromotionserviceV1Application.class, args);
	}

}
