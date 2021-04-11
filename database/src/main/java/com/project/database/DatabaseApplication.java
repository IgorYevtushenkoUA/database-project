package com.project.database;

import com.project.database.dto.bigunets.BigunetsReport;
import com.project.database.dto.statement.StatementsReport;
import com.project.database.parser.parserBigunets.BigunetsParser;
import com.project.database.parser.parserStatement.StatementParser;
import com.project.database.service.StudentService;
import com.project.database.serviceHibernate.BigunetsServiceH;
import com.project.database.serviceHibernate.ParserServiceH;
import com.project.database.serviceHibernate.StudentServiceH;
import com.project.database.serviceHibernate.SubjectServiceH;
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

        BigunetsServiceH bigunetsServiceH = applicationContext.getBean(BigunetsServiceH.class);
        bigunetsServiceH.findAllBySubjectNo(1,1,20);
//        SubjectServiceH subjectServiceH = applicationContext.getBean(SubjectServiceH.class);
//        subjectServiceH.findSubjectAverageMark(1, 2);


//        ParserServiceH parserServiceH = applicationContext.getBean(ParserServiceH.class);
//        StatementParser parser = new StatementParser();
//        StatementsReport statementsReport = parser.getStatementsReportByRoot("/pdfs/ios_good_2DONE.pdf");
//        System.out.println(statementsReport);
//        parserServiceH.insertVidomist(statementsReport.getStatementInfo());

    }
}

