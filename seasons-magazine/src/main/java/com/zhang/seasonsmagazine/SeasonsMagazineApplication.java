package com.zhang.seasonsmagazine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.zhang.seasonsmagazine.mapper")
public class SeasonsMagazineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeasonsMagazineApplication.class, args);
	}

}
