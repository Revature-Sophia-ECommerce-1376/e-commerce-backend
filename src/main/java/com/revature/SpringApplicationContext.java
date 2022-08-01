package com.revature;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.revature.services.AmazonSES;
import com.revature.utilities.JwtTokenManager;

@Configuration
@ComponentScan(basePackages = {"com.revature"})
public class SpringApplicationContext implements ApplicationContextAware{

	private static ApplicationContext CONTEXT;
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		CONTEXT = context;
	}
	
	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}
	
	
	
}
