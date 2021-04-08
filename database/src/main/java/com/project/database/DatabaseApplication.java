package com.project.database;

import com.project.database.repository.GroupRepository;
import com.project.database.repository.StudentRepository;
import com.project.database.service.StudentService;
import com.project.database.serviceHibernate.BigunetsServiceH;
import com.project.database.serviceHibernate.StudentServiceH;
import com.project.database.serviceHibernate.VidomistMarkServiceH;
import com.project.database.serviceHibernate.VidomistRepositoryH;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

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
//        System.out.println(studentRepository.findTrims(null));
//        System.out.println(studentRepository.findTrims("7"));


        StudentServiceH studentServiceH = applicationContext.getBean(StudentServiceH.class);
        System.out.println(studentServiceH.findAllStudentByYearSubjectGroupTeacherTrimCourse(null, null, null, null, null, null));

//        System.out.println(studentServiceH.findAllStudentMarks(2, null,null));
        //        System.out.println(studentServiceH.findAverageStudentMarksTrimCourse(
//                1,null, null, "2020-2021", "student_surname", true
//        ));


    }


}
