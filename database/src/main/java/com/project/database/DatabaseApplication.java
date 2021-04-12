package com.project.database;


import com.project.database.serviceHibernate.VidomistServiceH;
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
//        test(applicationContext);
    }

    private static void test(ApplicationContext applicationContext) {

        VidomistServiceH vidomistServiceH = applicationContext.getBean(VidomistServiceH.class);
        vidomistServiceH.findAllStatements(null, null, null, null, null, null, "", true, 1, 20);
//        SubjectServiceH subjectServiceH = applicationContext.getBean(SubjectServiceH.class);
//        subjectServiceH.findSubjectAverageMark(1, 2);


//        ParserServiceH parserServiceH = applicationContext.getBean(ParserServiceH.class);
//        StatementParser parser = new StatementParser();
//        StatementsReport statementsReport = parser.getStatementsReportByRoot("/pdfs/ios_good_2DONE.pdf");
//        System.out.println(statementsReport);
//        parserServiceH.insertVidomist(statementsReport.getStatementInfo());

    }
}

