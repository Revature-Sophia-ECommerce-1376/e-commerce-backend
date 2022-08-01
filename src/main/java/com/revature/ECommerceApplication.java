package com.revature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import com.revature.security.AppProperties;
import com.revature.security.SecurityConstants;
import com.revature.services.AmazonSES;

@SpringBootApplication
@ComponentScan(basePackages = {"com.revature"})
public class ECommerceApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}
	
	@Bean
	public AmazonSES getAmazonSES() {
		return new AmazonSES();
	}
	
	
	
	
	@Bean(name="AppProperties")
	public AppProperties getAppProperties()
	{
		return new AppProperties();
	}
	
	

}
