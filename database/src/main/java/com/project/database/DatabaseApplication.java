package com.project.database;


import com.project.database.serviceHibernate.StudentServiceH;
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
        test(applicationContext);
    }


    private static void test(ApplicationContext applicationContext) {

        VidomistServiceH vidomistServiceH = applicationContext.getBean(VidomistServiceH.class);
        System.out.println(vidomistServiceH.findById(156));

        StudentServiceH studentServiceH = applicationContext.getBean(StudentServiceH.class);
        System.out.println(studentServiceH.findAllStudentMarks(17, 3, 2, 1, 10).getContent());
    }
}

