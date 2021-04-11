package com.project.database;

import com.project.database.entities.BigunetsEntity;
import com.project.database.entities.StudentEntity;
import com.project.database.service.StudentService;
import com.project.database.serviceHibernate.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

        BigunetsServiceH bigunetsServiceH = applicationContext.getBean(BigunetsServiceH.class);
        Page<BigunetsEntity> page = bigunetsServiceH.findAllByTutorNo(1, 1, 20);
        System.out.println(page.getContent());

//        StudentService studentService = applicationContext.getBean(StudentService.class);
//        StudentServiceH studentServiceH = applicationContext.getBean(StudentServiceH.class);
//        SubjectServiceH subjectServiceH = applicationContext.getBean(SubjectServiceH.class);
//        Page<StudentEntity> page = studentServiceH.findAll("", true, 0, 3);
//        List<StudentEntity> list = page.getContent();
//        System.out.println(list);
//        System.out.println(page);
//        System.out.println(page.getTotalElements());
    }

}

