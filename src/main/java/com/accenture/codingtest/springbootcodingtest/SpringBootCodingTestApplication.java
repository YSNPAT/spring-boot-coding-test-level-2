package com.accenture.codingtest.springbootcodingtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootCodingTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCodingTestApplication.class, args);
	}

}
