package com.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class projectApplication {


	public static void main(String[] args) {




		SpringApplication.run(projectApplication.class, args);
	}

}
/*

@SpringBootApplication
public class projectApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(projectApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(projectApplication.class);
	}
}
*/
