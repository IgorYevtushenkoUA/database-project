package com.project.database;


import com.project.database.serviceHibernate.BigunetsServiceH;
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
        System.out.println(vidomistServiceH.findAllStatements("Логічне програмування", null, null, "", true, 1, 20)
                .getContent());



    }
}

