package com.project.database;

import com.project.database.service.StudentService;
import com.project.database.serviceHibernate.StudentServiceH;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);
//        String hashedAdmin = passwordEncoder.encode("admin");
//        System.out.println(hashedAdmin);


        ApplicationContext applicationContext = SpringApplication.run(DatabaseApplication.class, args);
        test(applicationContext);
    }

    private static void test(ApplicationContext applicationContext) {

        StudentServiceH studentServiceH = applicationContext.getBean(StudentServiceH.class);
//        Page page = studentServiceH.findStudentsWithRating(null,null,null,null,null,null,"",true, 1,20);
        System.out.println(studentServiceH.findStudentsWithRatingDefault(
                "", true, 2, 2));

    }

}

