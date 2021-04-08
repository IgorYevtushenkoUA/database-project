package com.project.database;

import com.project.database.repository.StudentRepository;
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
        StudentRepository studentRepository = applicationContext.getBean(StudentRepository.class);

        System.out.println(studentRepository.findAverageStudentsMarksTrimCourse());
        System.out.println(studentRepository.findTrims(null));
        System.out.println(studentRepository.findTrims("7"));
    }


}
