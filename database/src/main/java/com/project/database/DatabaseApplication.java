package com.project.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DatabaseApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(DatabaseApplication.class, args);
		test(applicationContext);
	}
	private static void test(ApplicationContext applicationContext){


	}

}
