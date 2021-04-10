package com.project.database;

import com.project.database.repository.StudentRepository;
import com.project.database.service.StudentService;
import com.project.database.serviceHibernate.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;


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
        StudentServiceH studentServiceH = applicationContext.getBean(StudentServiceH.class);
        SubjectServiceH subjectServiceH = applicationContext.getBean(SubjectServiceH.class);
        System.out.println(subjectServiceH.findAverageSubjectMarks());

//        System.out.println(studentServiceH.findDebtorsRatingDefault(
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                "student_surname",
//                true
////                , 1, 20
//        ));


    }

}

