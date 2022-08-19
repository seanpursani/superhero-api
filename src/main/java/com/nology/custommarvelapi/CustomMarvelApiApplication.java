package com.nology.custommarvelapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CustomMarvelApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomMarvelApiApplication.class, args);
	}

}
