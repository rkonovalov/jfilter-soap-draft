package com.example.producingwebservice;

import com.jfilter.EnableJsonFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableJsonFilter
@SpringBootApplication
public class SoapWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoapWebApplication.class, args);
	}

}
