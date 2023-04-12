package com.meta.ale;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.meta.ale.mapper")
public class AleApplication {
	public static void main(String[] args) {
		SpringApplication.run(AleApplication.class, args);
	}

}
