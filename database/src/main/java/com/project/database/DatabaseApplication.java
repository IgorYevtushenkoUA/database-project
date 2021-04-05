package com.project.database;

import com.project.database.service.*;
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

        VidomistService vidomistService = applicationContext.getBean(VidomistService.class);
        System.out.println(vidomistService.findById(11));


        //        GroupService groupService = applicationContext.getBean(GroupService.class);
//        System.out.println(groupService.findAllEduYears(1,20));

//        StudentService studentService = applicationContext.getBean(StudentService.class);
//        System.out.println(studentService.findById(14));

//        System.out.println(studentService.findAllDebtorsByYearSubjectGroupTeacherTrimCourse(
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                "student_surname",
//                "desc",
//                1, 20
//        ));

//        TutorService subjectService = applicationContext.getBean(TutorService.class);
//        System.out.println(subjectService.findAll(1,20));


    }

}
