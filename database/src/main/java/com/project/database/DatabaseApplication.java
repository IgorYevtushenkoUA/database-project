package com.project.database;

import com.project.database.dao.impl.StudentDaoImpl;
import com.project.database.dao.inter.StudentDao;
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
//		StudentDao student = new StudentDaoImpl();
//		student.findAllStudent(1,20);
	}

}
