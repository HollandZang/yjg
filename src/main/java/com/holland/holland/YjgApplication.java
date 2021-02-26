package com.holland.holland;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.holland.holland.mapper")
@SpringBootApplication
public class YjgApplication {

	public static void main(String[] args) {
		SpringApplication.run(YjgApplication.class, args);
	}

}
