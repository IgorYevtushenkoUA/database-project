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


        ApplicationContext applicationContext = SpringApplication.run(DatabaseApplication.class, args);
        test(applicationContext);
    }

    private static void test(ApplicationContext applicationContext) {

        System.out.println(132);
        StatementServiceH statementServiceH = applicationContext.getBean(StatementServiceH.class);
//        System.out.println(statementServiceH.getStatementInfo(1));
        VidomistRepository vidomistRepository = applicationContext.getBean(VidomistRepository.class);
        List<Object> obj = vidomistRepository.getStatementHeader(1);

//          SubjectServiceH subjectServiceH = applicationContext.getBean(SubjectServiceH.class);
//        subjectServiceH.findSubjectAverageMark(1, 2);


//        ParserServiceH parserServiceH = applicationContext.getBean(ParserServiceH.class);
//        StatementParser parser = new StatementParser();
//        StatementsReport statementsReport = parser.getStatementsReportByRoot("/pdfs/ios_good_2DONE.pdf");
//        System.out.println(statementsReport);
//        parserServiceH.insertVidomist(statementsReport.getStatementInfo());

    }
}

