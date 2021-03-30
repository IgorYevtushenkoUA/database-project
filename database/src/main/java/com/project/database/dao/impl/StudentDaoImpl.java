package com.project.database.dao.impl;

import com.project.database.dao.connector.Connector;
import com.project.database.dao.inter.StudentDao;
import com.project.database.entity.Student;
import com.project.database.entity.Subject;
import com.project.database.entity.Vidomist;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Repository
public class StudentDaoImpl implements StudentDao {

    private static Connection connection;
    private PreparedStatement preparedStatement;

    static {
        try {
            connection = new Connector().getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StudentDaoImpl si = new StudentDaoImpl();
        System.out.println(si.findAllDebtorsByYearSubjectGroupTeacher(null,null,null,null, 1, 3));
    }

    /**
     * method to create student by ResultSet
     */
    private Student createStudent(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setStudentCode(resultSet.getLong("student_code"));
        student.setStudentSurname(resultSet.getString("student_surname"));
        student.setStudentName(resultSet.getString("student_name"));
        student.setStudentPatronymic(resultSet.getString("student_patronymic"));
        student.setStudentRecordBook(resultSet.getString("student_record_book"));
        return student;
    }

    private Vidomist createVidomist(ResultSet resultSet) throws SQLException {
        Vidomist vidomist = new Vidomist();
        vidomist.setVidomistNo(resultSet.getInt("vidomist_no"));
        vidomist.setTutorNo(resultSet.getInt("tutor_no"));
        vidomist.setPresentCount(resultSet.getInt("present_count"));
        vidomist.setAbsentCount(resultSet.getInt("absent_count"));
        vidomist.setRejectedCount(resultSet.getInt("rejected_count"));
        vidomist.setControlType(resultSet.getString("control_type"));
        vidomist.setExamDate(resultSet.getDate("exam_date"));
        vidomist.setGroupCode(resultSet.getInt("group_code"));

        return vidomist;
    }

    // +
    @Override
    public List<Student> findAllStudents(int page, int numberPerPage) {
        List<Student> students = new ArrayList<>();
        int index = 1;
        try {
            String sql = "select * from student order by student_surname limit ? offset ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(createStudent(resultSet));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return students;
    }

    // +
    @Override
    public List<Student> findStudentsByPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        List<Student> students = new ArrayList<>();
        int index = 1;
        try {
            String sql = "select * from student where student_name=? and student_surname=? and student_patronymic=?  order by student_surname limit ? offset ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(index++, name);
            preparedStatement.setString(index++, surname);
            preparedStatement.setString(index++, patronymic);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(createStudent(resultSet));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return students;
    }

    // +
    @Override
    public List<Student> findStudentsByNameSurname(String name, String surname, int page, int numberPerPage) {
        List<Student> students = new ArrayList<>();
        int index = 1;
        try {
            String sql = "select * from student where student_name=? and student_surname=? order by student_surname limit ? offset ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(index++, name);
            preparedStatement.setString(index++, surname);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(createStudent(resultSet));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return students;
    }

    List<String> setParams(String eduYear, String subjectName, String groupName, String tutorName) {
        eduYear = eduYear == null ? "in (select distinct(gg.edu_year) from \"group\" gg) " : " = " + eduYear + " ";
        subjectName = subjectName == null ? "in (select ss.subject_name from subject ss) " : " = " + subjectName + " ";
        groupName = groupName == null ? "in (select distinct(gg.group_name) from \"group\" gg) " : " = " + groupName + " ";
        tutorName = tutorName == null ? "in (select tt.tutor_name from tutor tt) " : " = " + tutorName + " ";
        return List.of(eduYear, subjectName, groupName, tutorName);
    }

    // +
    @Override
    public List<Student> findAllDebtorsByYearSubjectGroupTeacher(String eduYear, String subjectName, String groupName, String tutorName, int page, int numberPerPage) {
        List<Student> students = new ArrayList<>();
        int index = 1;
        List<String> params = setParams(eduYear, subjectName, groupName, tutorName);
        System.out.println(params);
        try {
            String sql = "select * " +
                    "from student " +
                    "where student_code in (select vm.student_code " +
                    "                       from vidomist_mark vm " +
                    "                       where vm.vidomist_no in (select v.vidomist_no " +
                    "                                                from vidomist v " +
                    "                                                where v.control_type = 'залік' " +
                    "                                                  and v.tutor_no in (select t.tutor_no " +
                    "                                                                     from tutor t " +
                    "                                                                     where tutor_name " +  params.get(3) + " ) " +
                    "                                                  and v.group_code in (select group_code " +
                    "                                                                       from \"group\" g " +
                    "                                                                       where g.group_code in (select gr.group_code " +
                    "                                                                                              from \"group\" gr " +
                    "                                                                                              where gr.group_name " + params.get(2) + ") " +
                    "                                                                         and edu_year " + params.get(0) +" " +
                    "                                                                         and subject_no in (select s.subject_no " +
                    "                                                                                            from subject s " +
                    "                                                                                            where s.subject_name " + params.get(1) + " ))) " +
                    "                         and vm.complete_mark < 60) " +
                    "or student_code in (select vm.student_code " +
                    "                       from vidomist_mark vm " +
                    "                       where vm.vidomist_no in (select v.vidomist_no " +
                    "                                                from vidomist v " +
                    "                                                where v.control_type = 'екзамен' " +
                    "                                                  and v.tutor_no in (select t.tutor_no " +
                    "                                                                     from tutor t " +
                    "                                                                     where tutor_name " + params.get(3) +") " +
                    "                                                  and v.group_code in (select group_code " +
                    "                                                                       from \"group\" g " +
                    "                                                                       where g.group_code in (select gr.group_code " +
                    "                                                                                              from \"group\" gr " +
                    "                                                                                              where gr.group_name " + params.get(2) + " ) " +
                    "                                                                         and edu_year " + params.get(0) +
                    "                                                                         and subject_no in (select s.subject_no " +
                    "                                                                                            from subject s " +
                    "                                                                                            where s.subject_name " + params.get(1) + " ))) " +
                    "                         and vm.complete_mark < 61) " +
                    "order by student_surname " +
                    "limit ? offset ? ";
            System.out.println(sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(createStudent(resultSet));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return students;
    }

    List<String> setParams(String eduYear, int subjectId, int groupId, int tutorId) {
        eduYear = eduYear == null ? " in (select distinct(gg.edu_year) from \"group\" gg) " : " = " + eduYear + " ";
        String subjectRes = subjectId == -1 ? " in (select ss.subject_no from subject ss) " : " = " + subjectId + " ";
        String groupRes = groupId == -1 ? " in (select gg.group_code from \"group\" gg) " : " = " + groupId + " ";
        String tutorRes = tutorId == -1 ? " in (select tt.tutor_no from tutor tt) " : " = " + tutorId + " ";
        return List.of(eduYear, subjectRes, groupRes, tutorRes);
    }

    @Override
    public List<Student> findAllDebtorsByYearSubjectGroupTeacher(String eduYear, int subjectNo, int groupCode, int tutorNo, int page, int numberPerPage) {
        List<Student> students = new ArrayList<>();
        int index = 1;
        List<String> params = setParams(eduYear, subjectNo, groupCode, tutorNo);
        try{
            String sql = "select *   " +
                    "from student   " +
                    "where student_code in (select vm.student_code   " +
                    "                       from vidomist_mark vm   " +
                    "                       where vm.vidomist_no in (select v.vidomist_no   " +
                    "                                                from vidomist v   " +
                    "                                                where v.control_type = 'залік'   " +
                    "                                                  and v.tutor_no " + params.get(3)   + " " +
                    "                                                  and v.group_code in (select group_code   " +
                    "                                                                       from \"group\" g   " +
                    "                                                                       where g.group_code " + params.get(2)   + " " +
                    "                                                                         and edu_year " + params.get(0)  +" " +
                    "                                                                         and subject_no " + params.get(1) + " ))   " +
                    "                         and vm.complete_mark < 60)   " +
                    "   or student_code in (select vm.student_code   " +
                    "                       from vidomist_mark vm   " +
                    "                       where vm.vidomist_no in (select v.vidomist_no   " +
                    "                                                from vidomist v   " +
                    "                                                where v.control_type = 'екзамен'   " +
                    "                                                  and v.tutor_no " + params.get(3)  +" " +
                    "                                                  and v.group_code in (select group_code   " +
                    "                                                                       from \"group\" g   " +
                    "                                                                       where g.group_code " + params.get(2)  + " " +
                    "                                                                         and edu_year " + params.get(0)  +" " +
                    "                                                                         and subject_no " + params.get(1) +" ))   " +
                    "                         and vm.complete_mark < 61)   " +
                    "order by student_surname   " +
                    "limit ? offset ?   ";
            System.out.println(sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                students.add(createStudent(resultSet));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // +

    @Override
    public List<Student> findAllStudentsByYearSubjectGroupTeacher(String eduYear, String subjectName, String groupName, String tutorName, int page, int numberPerPage) {
        List<Student> students = new ArrayList<>();
        int index = 1;
        List<String> params = setParams(eduYear, subjectName, groupName, tutorName);
        System.out.println(params);
        try {
            String sql = "select *  " +
                    "from student  " +
                    "where student_code in (select vm.student_code  " +
                    "                       from vidomist_mark vm  " +
                    "                       where vm.vidomist_no in (select v.vidomist_no  " +
                    "                                                from vidomist v  " +
                    "                                                where v.tutor_no in (select t.tutor_no  " +
                    "                                                                     from tutor t  " +
                    "                                                                     where tutor_name " + params.get(3) + ")  " +
                    "                                                  and v.group_code in (select group_code  " +
                    "                                                                       from \"group\" g  " +
                    "                                                                       where g.group_code in (select gr.group_code  " +
                    "                                                                                              from \"group\" gr  " +
                    "                                                                                              where gr.group_name " + params.get(2) + ")  " +
                    "                                                                         and edu_year " + params.get(0) +
                    "                                                                         and subject_no in (select s.subject_no  " +
                    "                                                                                            from subject s  " +
                    "                                                                                            where s.subject_name " + params.get(1) + ")))  " +
                    "                         and vm.complete_mark >= 60)  " +
                    "order by student_surname  " +
                    "limit ? offset ?";
            System.out.println(sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(createStudent(resultSet));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return students;
    }

    // todo check or >= 60 or != F (because in some we can have 60 in some 61)
    @Override
    public List<Student> findAllStudentsByYearSubjectGroupTeacher(String eduYear, int subjectNo, int groupCode, int tutorNo, int page, int numberPerPage) {
        List<Student> students = new ArrayList<>();
        int index = 1;
        List<String> params = setParams(eduYear, subjectNo, groupCode, tutorNo);
        try{
            String sql = "select * " +
                    "from student " +
                    "where student_code in (select vm.student_code " +
                    "                       from vidomist_mark vm " +
                    "                       where vm.vidomist_no in (select v.vidomist_no " +
                    "                                                from vidomist v " +
                    "                                                where v.tutor_no " + params.get(3)  +
                    "                                                  and v.group_code in (select group_code " +
                    "                                                                       from \"group\" g " +
                    "                                                                       where g.group_code " + params.get(2) +
                    "                                                                         and edu_year " + params.get(0) +
                    "                                                                         and subject_no " + params.get(1) + ")) "+
                    "                         and vm.complete_mark >= 60) " +
                    "order by student_surname " +
                    "limit ? offset ?";
            System.out.println(sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                students.add(createStudent(resultSet));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public List<Student> findBestPercentOfStudentByGroupCourse(int percent, int groupCode, int course, int page, int numberPerPage) {
        return null;
    }

    @Override
    public List<Student> findBestPercentOfStudentByGroupCourse(int percent, String groupName, int course, int page, int numberPerPage) {
        return null;
    }

    @Override
    public List<Student> findWorstPercentOfStudentByGroupCourse(int percent, int groupCode, int course, int page, int numberPerPage) {
        return null;
    }

    @Override
    public List<Student> findWorstPercentOfStudentByGroupCourse(int percent, String groupName, int course, int page, int numberPerPage) {
        return null;
    }

    @Override
    public List<Student> sortAllStudentInGroupCourseByFromHighToLowRating(int percent, String groupName, int course, int page, int numberPerPage) {
        return null;
    }

    @Override
    public List<Student> sortAllStudentInGroupCourseByFromHighToLowRating(int percent, int groupCode, int course, int page, int numberPerPage) {
        return null;
    }

    @Override
    public List<Student> sortAllStudentInGroupCourseByFromLowToHighRating(int percent, String groupName, int course, int page, int numberPerPage) {
        return null;
    }

    @Override
    public List<Student> sortAllStudentInGroupCourseByFromLowToHighRating(int percent, int groupCode, int course, int page, int numberPerPage) {
        return null;
    }

    // +
    @Override
    public List<Vidomist> findAllVidomostyByStudentId(int studentId, int page, int numberPerPage) {
        List<Vidomist> vidomists = new ArrayList<>();
        try {
            String sql = "select * from vidomist where vidomist_no in (select vidomist_no from vidomist_mark where student_code = ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vidomists.add(createVidomist(resultSet));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return vidomists;
    }

    // +
    @Override
    public List<Vidomist> findAllVidomostyByStudentPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        List<Vidomist> vidomists = new ArrayList<>();
        try {
            String sql = "select * from vidomist where vidomist_no in " +
                    "(select vidomist_no from vidomist_mark where student_code in " +
                    "(select student_code from student where student_name=? and student_surname=? and student_patronymic=?))";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, patronymic);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vidomists.add(createVidomist(resultSet));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return vidomists;
    }

    @Override
    public double findAverageMarkForStudentById(int studentCode, int page, int numberPerPage) {
        return 0;
    }

    @Override
    public double findAverageMarkForStudentByPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        return 0;
    }

    @Override
    public TreeMap<Subject, Integer> findAllMArksForStudentByPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        return null;
    }

    @Override
    public TreeMap<Subject, Integer> findAllMArksForStudentById(int studentCode, int page, int numberPerPage) {
        return null;
    }
}
