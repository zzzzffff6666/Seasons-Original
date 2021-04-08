package com.msjf.seasons;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.msjf.seasons.mapper")
@SpringBootApplication
public class SeasonsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeasonsApplication.class, args);
    }

}
