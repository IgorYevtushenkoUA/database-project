package com.project.database;

import com.project.database.repository.StudentRepository;
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

        StudentServiceH studentServiceH = applicationContext.getBean(StudentServiceH.class);
        System.out.println(studentServiceH.findStudentRatingDefault());

        //        System.out.println(studentServiceH.findAverageStudentMarksTrimCourse(
//                1,null, null, "2020-2021", "student_surname", true
//        ));


    }


}
