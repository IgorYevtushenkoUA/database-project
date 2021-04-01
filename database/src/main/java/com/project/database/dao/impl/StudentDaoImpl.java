package com.project.database.dao.impl;

import com.project.database.dao.connector.Connector;
import com.project.database.dao.inter.StudentDao;
import com.project.database.entity.Student;
import com.project.database.entity.Subject;
import com.project.database.entity.Vidomist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Repository
public class StudentDaoImpl implements StudentDao {

    private Connection connection;
    private PreparedStatement preparedStatement;

    private final Connector connector;


    @Autowired
    public StudentDaoImpl(Connector connector) {
        this.connector = connector;
    }

    @PostConstruct
    void initConnection() {
        try {
            connection = connector.getConnection();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


//    public static void main(String[] args) {
//        StudentDaoImpl si = new StudentDaoImpl(connector);
//        System.out.println(si.findAllMarksForStudentByPIB("Вадим", "Кучерявий", "Юрійович", 1, 3));
//        System.out.println(si.findAllMarksForStudentById(1, 1, 3));
//    }

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
                    "                                                                     where tutor_name " + params.get(3) + " ) " +
                    "                                                  and v.group_code in (select group_code " +
                    "                                                                       from \"group\" g " +
                    "                                                                       where g.group_code in (select gr.group_code " +
                    "                                                                                              from \"group\" gr " +
                    "                                                                                              where gr.group_name " + params.get(2) + ") " +
                    "                                                                         and edu_year " + params.get(0) + " " +
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
                    "                                                                     where tutor_name " + params.get(3) + ") " +
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

    //+
    @Override
    public List<Student> findAllDebtorsByYearSubjectGroupTeacher(String eduYear, int subjectNo, int groupCode, int tutorNo, int page, int numberPerPage) {
        List<Student> students = new ArrayList<>();
        int index = 1;
        List<String> params = setParams(eduYear, subjectNo, groupCode, tutorNo);
        try {
            String sql = "select *   " +
                    "from student   " +
                    "where student_code in (select vm.student_code   " +
                    "                       from vidomist_mark vm   " +
                    "                       where vm.vidomist_no in (select v.vidomist_no   " +
                    "                                                from vidomist v   " +
                    "                                                where v.control_type = 'залік'   " +
                    "                                                  and v.tutor_no " + params.get(3) + " " +
                    "                                                  and v.group_code in (select group_code   " +
                    "                                                                       from \"group\" g   " +
                    "                                                                       where g.group_code " + params.get(2) + " " +
                    "                                                                         and edu_year " + params.get(0) + " " +
                    "                                                                         and subject_no " + params.get(1) + " ))   " +
                    "                         and vm.complete_mark < 60)   " +
                    "   or student_code in (select vm.student_code   " +
                    "                       from vidomist_mark vm   " +
                    "                       where vm.vidomist_no in (select v.vidomist_no   " +
                    "                                                from vidomist v   " +
                    "                                                where v.control_type = 'екзамен'   " +
                    "                                                  and v.tutor_no " + params.get(3) + " " +
                    "                                                  and v.group_code in (select group_code   " +
                    "                                                                       from \"group\" g   " +
                    "                                                                       where g.group_code " + params.get(2) + " " +
                    "                                                                         and edu_year " + params.get(0) + " " +
                    "                                                                         and subject_no " + params.get(1) + " ))   " +
                    "                         and vm.complete_mark < 61)   " +
                    "order by student_surname   " +
                    "limit ? offset ?   ";
            System.out.println(sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(createStudent(resultSet));
            }
        } catch (SQLException e) {
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

    // +
    @Override
    public List<Student> findAllStudentsByYearSubjectGroupTeacher(String eduYear, int subjectNo, int groupCode, int tutorNo, int page, int numberPerPage) {
        List<Student> students = new ArrayList<>();
        int index = 1;
        List<String> params = setParams(eduYear, subjectNo, groupCode, tutorNo);
        try {
            String sql = "select * " +
                    "from student " +
                    "where student_code in (select vm.student_code " +
                    "                       from vidomist_mark vm " +
                    "                       where vm.vidomist_no in (select v.vidomist_no " +
                    "                                                from vidomist v " +
                    "                                                where v.tutor_no " + params.get(3) +
                    "                                                  and v.group_code in (select group_code " +
                    "                                                                       from \"group\" g " +
                    "                                                                       where g.group_code " + params.get(2) +
                    "                                                                         and edu_year " + params.get(0) +
                    "                                                                         and subject_no " + params.get(1) + ")) " +
                    "                         and vm.complete_mark >= 60) " +
                    "order by student_surname " +
                    "limit ? offset ?";
            System.out.println(sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(createStudent(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }


    @Override
    public List<Student> findBestPercentOfStudentByGroupCourse(int percent, int groupCode, int course, int page, int numberPerPage) {
        List<Student> students = new ArrayList<>();
        int index = 1;
        try {
            String sql = "";
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

    // +
    @Override
    public double findAverageMarkForStudentById(int studentCode) {
        double averageMark = 0;
        int index = 1;
        try {
            String sql = "select avg(bm.complete_mark) from bigunets_mark bm where bm.student_code = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, studentCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                averageMark = resultSet.getDouble("avg");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return averageMark;
    }

    // +
    @Override
    public double findAverageMarkForStudentByPIB(String name, String surname, String patronymic) {
        double averageMark = 0;
        int index = 1;
        try {
            String sql = "select avg(bm.complete_mark) from bigunets_mark bm where bm.student_code in (select student_code from student where student_name = ? and student_surname = ? and student_patronymic= ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(index++, name);
            preparedStatement.setString(index++, surname);
            preparedStatement.setString(index++, patronymic);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                averageMark = resultSet.getDouble("avg");
                System.out.println(averageMark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return averageMark;
    }
    public Subject createSubject(ResultSet resultSet) throws SQLException {
        Subject subject = new Subject();
        subject.setSubjectNo(resultSet.getInt("subject_no"));
        subject.setSubjectName(resultSet.getString("subject_name"));
        subject.setEduLevel(resultSet.getString("edu_level"));
        subject.setFaculty(resultSet.getString("faculty"));
        return subject;
    }

    // +
    @Override
    public TreeMap<Integer, List> findAllMarksForStudentByPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        TreeMap<Integer, List> statistics = new TreeMap<>();
        int index = 1;
        try {
            String sql = "select sub.subject_no as subject_no, sub.subject_name as subject_name,sub.edu_level as edu_level, sub.faculty as faculty, vm.complete_mark as complete_mark   " +
                    "from student s, \"group\" g, subject sub, vidomist v, vidomist_mark vm   " +
                    "where s.student_code in (select student_code from student where student_name =? and student_surname=? and student_patronymic=? ) " +
                    "and s.student_code = vm.student_code   " +
                    "and vm.vidomist_no = v.vidomist_no   " +
                    "and g.group_code=v.group_code   " +
                    "and g.subject_no = sub.subject_no " +
                    "limit ? offset ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(index++, name);
            preparedStatement.setString(index++, surname);
            preparedStatement.setString(index++, patronymic);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int mark = resultSet.getInt("complete_mark");
                Subject subject = createSubject(resultSet);
                statistics.put(subject.getSubjectNo(), List.of(subject.getSubjectName(), mark));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statistics;
    }

    // +
    @Override
    public TreeMap<Integer, List> findAllMarksForStudentById(int studentCode, int page, int numberPerPage) {
        TreeMap<Integer, List> statistics = new TreeMap<>();
        int index = 1;
        try {
            String sql = "select sub.subject_no as subject_no, sub.subject_name as subject_name,sub.edu_level as edu_level, sub.faculty as faculty, vm.complete_mark as complete_mark   " +
                    "from student s, \"group\" g, subject sub, vidomist v, vidomist_mark vm   " +
                    "where s.student_code = ? " +
                    "and s.student_code = vm.student_code   " +
                    "and vm.vidomist_no = v.vidomist_no   " +
                    "and g.group_code=v.group_code   " +
                    "and g.subject_no = sub.subject_no " +
                    "limit ? offset ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, studentCode);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int mark = resultSet.getInt("complete_mark");
                Subject subject = createSubject(resultSet);
                statistics.put(subject.getSubjectNo(), List.of(subject.getSubjectName(), mark));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statistics;
    }
}
