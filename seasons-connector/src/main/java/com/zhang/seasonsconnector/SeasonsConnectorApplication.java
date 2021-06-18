package com.zhang.seasonsconnector;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.zhang.seasonsconnector.mapper")
public class SeasonsConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeasonsConnectorApplication.class, args);
	}

}
