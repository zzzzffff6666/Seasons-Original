package com.zhang.seasonswork;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.zhang.seasonswork.mapper")
public class SeasonsWorkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeasonsWorkApplication.class, args);
	}

}
