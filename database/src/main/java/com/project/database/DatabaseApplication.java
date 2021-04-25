package com.project.database;


import com.project.database.serviceHibernate.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);
//        String hashedAdmin = passwordEncoder.encode("admin");
//        System.out.println(hashedAdmin);

//        System.out.println((String.join(" ","123", "234", "345").toString()));
        ApplicationContext applicationContext = SpringApplication.run(DatabaseApplication.class, args);
        test(applicationContext);
    }

    private static void test(ApplicationContext applicationContext) {

        StudentServiceH studentServiceH = applicationContext.getBean(StudentServiceH.class);
        System.out.println(studentServiceH.findAllStudentMarks(
                1,
                3,
                1,
                "",
                true,
                1,
                20).getContent());


    }
}

