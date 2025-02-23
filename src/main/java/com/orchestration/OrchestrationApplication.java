package com.orchestration;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.Orchestration")
public class OrchestrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrchestrationApplication.class, args);
	}

	//to access swagger : http://localhost:8080/orchestration/swagger-ui/index.html

}
