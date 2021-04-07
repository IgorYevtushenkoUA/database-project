package com.project.database;

import com.project.database.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

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

        StudentService studentService = applicationContext.getBean(StudentService.class);

        System.out.println(studentService.findAllByYearSubjectGroupTeacherTrimCourse(
                "2020-2021",
                "predmet",
                null,
                null,
                null,
                "3",
                "student_surname",
                true,
                1, 20
        ));


    }

}