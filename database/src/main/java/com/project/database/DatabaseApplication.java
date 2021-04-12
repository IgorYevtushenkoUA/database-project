package com.project.database;


import com.project.database.repository.VidomistRepository;
import com.project.database.serviceHibernate.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


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

        StatementServiceH statementServiceH = applicationContext.getBean(StatementServiceH.class);
        System.out.println(statementServiceH.getStatementInfo(1));

//        VidomistRepository v = applicationContext.getBean(VidomistRepository.class);
//        System.out.println(v.getStatementFooter(1));


    }
}

