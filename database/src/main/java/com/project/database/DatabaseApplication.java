package com.project.database;

import com.project.database.dao.impl.StudentDaoImpl;
import com.project.database.dao.inter.StudentDao;
import com.project.database.service.StudentService;
import com.project.database.service.SubjectService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(DatabaseApplication.class, args);
        test(applicationContext);
    }

    private static void test(ApplicationContext applicationContext) {
        StudentService studentService = applicationContext.getBean(StudentService.class);
        System.out.println(studentService.findAllDebtorsByYearSubjectGroupTeacherTrimCourse(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                1, 20
        ));
    }

}
