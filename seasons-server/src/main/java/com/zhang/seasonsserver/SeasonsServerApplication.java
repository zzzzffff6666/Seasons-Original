package com.zhang.seasonsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SeasonsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeasonsServerApplication.class, args);
	}

}
