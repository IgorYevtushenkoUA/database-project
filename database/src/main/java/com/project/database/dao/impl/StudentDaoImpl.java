package com.project.database.dao.impl;

import com.project.database.dao.connector.Connector;
import com.project.database.dao.inter.StudentDao;
import com.project.database.entity.Bigunets;
import com.project.database.entity.Student;
import com.project.database.entity.Subject;
import com.project.database.entity.Vidomist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Repository
public class StudentDaoImpl implements StudentDao {

    private Connection connection;
    private PreparedStatement preparedStatement;

    private final Connector connector;
    private int index = 1;

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
//
//        StudentDaoImpl si = new StudentDaoImpl();
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

    private Bigunets createBigunets(ResultSet resultSet) throws SQLException {
        Bigunets bigunets = new Bigunets();
        bigunets.setBigunetsNo(resultSet.getInt("bigunets_no"));
        bigunets.setExamDate(resultSet.getDate("exam_date"));
        bigunets.setValidUntil(resultSet.getDate("valid_until"));
        bigunets.setPostpReason(resultSet.getString("postp_reason"));
        bigunets.setControlType(resultSet.getString("control_type"));
        bigunets.setTutorCode(resultSet.getInt("tutor_code"));

        return bigunets;
    }

    @Override
    public List<Student> findAll(int page, int numberPerPage) {
        List<Student> students = new ArrayList<>();
        index = 1;
        try {
            String sql = "select * from \"student\" order by student_surname limit ? offset ?";
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
    public List<Student> findByPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        List<Student> students = new ArrayList<>();
        index = 1;
        try {
            String sql = "select * from \"student\" where student_name=? and student_surname=? and student_patronymic=?  order by student_surname limit ? offset ?";
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

    @Override
    public List<Student> findByNameSurname(String name, String surname, int page, int numberPerPage) {
        List<Student> students = new ArrayList<>();
        index = 1;
        try {
            String sql = "select * from \"student\" where student_name=? and student_surname=? order by student_surname limit ? offset ?";
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

    @Override
    public List<Vidomist> findAllVidomostyByStudentPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        List<Vidomist> vidomists = new ArrayList<>();
        try {
            String sql = "select * from vidomist where vidomist_no in " +
                    "(select vidomist_no from vidomist_mark where student_code in " +
                    "(select student_code from \"student\" where student_name=? and student_surname=? and student_patronymic=?))";
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
    public List<Bigunets> findAllBigunetsByStudentId(int studentId,
                                              int page,
                                              int numberPerPage){

        List<Bigunets> bigunets = new ArrayList<>();
        try {
            String sql = "select * from bigunets where bigunets_no in (select bigunets_no from bigunets_mark where student_code = ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bigunets.add(createBigunets(resultSet));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return bigunets;
    }

    @Override
    public double findAverageMarkById(int studentCode) {
        double averageMark = 0;
        index = 1;
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

    @Override
    public double findAverageMarkByPIB(String name, String surname, String patronymic) {
        double averageMark = 0;
        index = 1;
        try {
            String sql = "select avg(bm.complete_mark) from bigunets_mark bm where bm.student_code in (select student_code from \"student\" where student_name = ? and student_surname = ? and student_patronymic= ?)";
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

    @Override
    public TreeMap<Integer, List> findAllMarksByPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        TreeMap<Integer, List> statistics = new TreeMap<>();
        index = 1;
        try {
            String sql = "select sub.subject_no as subject_no, sub.subject_name as subject_name,sub.edu_level as edu_level, sub.faculty as faculty, vm.complete_mark as complete_mark   " +
                    "from \"student\" s, \"group\" g, subject sub, vidomist v, vidomist_mark vm   " +
                    "where s.student_code in (select student_code from \"student\" where student_name =? and student_surname=? and student_patronymic=? ) " +
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

    @Override
    public TreeMap<Integer, List> findAllMarksById(int studentCode, int page, int numberPerPage) {
        TreeMap<Integer, List> statistics = new TreeMap<>();
        index = 1;
        try {
            String sql = "select sub.subject_no as subject_no, sub.subject_name as subject_name,sub.edu_level as edu_level, sub.faculty as faculty, vm.complete_mark as complete_mark   " +
                    "from \"student\" s, \"group\" g, subject sub, vidomist v, vidomist_mark vm   " +
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

    @Override
    public void deleteById(int studentId) {
        index = 1;
        try {
            String sql = "delete from student s where s.student_code=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, studentId);
            int result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //********************************************************************
    TreeMap<String, String> setParams(String eduYear, String subjectName, String groupName, String tutorName, String trim, String course, String sortType, String sortGrow) {
        TreeMap<String, String> map = new TreeMap<>();
        eduYear = eduYear == null ? "in (select distinct(gg.edu_year) from \"group\" gg) " : " = " + eduYear + " ";
        subjectName = subjectName == null ? "in (select ss.subject_name from subject ss) " : " = " + subjectName + " ";
        groupName = groupName == null ? "in (select distinct(gg.group_name) from \"group\" gg) " : " = " + groupName + " ";
        tutorName = tutorName == null ? "in (select tt.tutor_name from tutor tt) " : " = " + tutorName + " ";

        trim = trim == null ? " in (select trim from \"group\") " : " = " + trim + " ";
        course = course == null ? " in (select course from \"group\") " : " = " + course + " ";
        sortType = sortType == null ? "  student_surname " : " complete_mark ";
        sortGrow = sortGrow == null ? " ASC " : " DESC ";

        map.put("eduYear", eduYear);
        map.put("subjectName", subjectName);
        map.put("groupName", groupName);
        map.put("tutorName", tutorName);
        map.put("trim", trim);
        map.put("course", course);
        map.put("sortType", sortType);
        map.put("sortGrow", sortGrow);

        return map;
    }

    @Override
    public List<Student> findAllByYearSubjectGroupTeacherTrimCourse(
            String eduYear,
            String subjectName,
            String groupName,
            String tutorName,
            String trim,
            String course,
            String sortType,
            String sortGrow,
            int page,
            int numberPerPage) {
        List<Student> students = new ArrayList<>();
        index = 1;
        TreeMap<String, String> params = setParams(eduYear, subjectName, groupName, tutorName, trim, course, sortType, sortGrow);
        System.out.println(params);
        try {
            String sql = "select * " +
                    "from \"student\" inner join \"vidomist_mark\" vm1 on student.student_code = vm1.student_code " +
                    "where student.student_code in (select vm.student_code " +
                    "                       from vidomist_mark vm " +
                    "                       where vm.vidomist_no in (select v.vidomist_no " +
                    "                                                from vidomist v " +
                    "                                                where v.tutor_no in (select t.tutor_no " +
                    "                                                                     from tutor t " +
                    "                                                                     where tutor_name  " + params.get("tutorName") + " ) " +
                    "                                                  and v.group_code in (select group_code " +
                    "                                                                       from \"group\" g " +
                    "                                                                       where g.group_code in (select gr.group_code " +
                    "                                                                                              from \"group\" gr " +
                    "                                                                                              where gr.group_name " + params.get("groupName") + " ) " +
                    "                                                                         and g.trim " + params.get("trim") + " " +
                    "                                                                         and g.course " + params.get("course") + " " +
                    "                                                                         and edu_year " + params.get("eduYear") + " " +
                    "                                                                         and subject_no in (select s.subject_no" + " " +
                    "                                                                                            from subject s" + " " +
                    "                                                                                            where s.subject_name " + params.get("subjectName") + " )))" +
                    "                         and vm.complete_mark >= 60) " +
                    "order by " + params.get("sortType") + "   " + params.get("sortGrow") + " " +
                    "limit ? offset ?;";
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

    @Override
    public List<Student> findAllDebtorsByYearSubjectGroupTeacherTrimCourse(
            String eduYear,
            String subjectName,
            String groupName,
            String tutorName,
            String trim,
            String course,
            String sortType,
            String sortGrow,
            int page,
            int numberPerPage) {
        List<Student> students = new ArrayList<>();
        index = 1;
        TreeMap<String, String> params = setParams(eduYear, subjectName, groupName, tutorName, trim, course, sortType, sortGrow);
        try {
            String sql = " select * " +
                    " from student " +
                    " where student_code in (select vm.student_code " +
                    "                       from vidomist_mark vm " +
                    "                       where vm.vidomist_no in (select v.vidomist_no " +
                    "                                                from vidomist v " +
                    "                                                where v.control_type = 'залік' " +
                    "                                                  and v.tutor_no in (select t.tutor_no " +
                    "                                                                     from tutor t " +
                    "                                                                     where tutor_name  " + params.get("tutorName") + " ) " +
                    "                                                  and v.group_code in (select group_code " +
                    "                                                                       from \"group\" g " +
                    "                                                                       where g.group_code in (select gr.group_code " +
                    "                                                                                              from \"group\" gr " +
                    "                                                                                              where gr.group_name " + params.get("groupName") + " ) " +
                    "                                                                         and g.trim " + params.get("trim") + " " +
                    "                                                                         and g.course " + params.get("course") + " " +
                    "                                                                         and edu_year " + params.get("eduYear") + " " +
                    "                                                                         and subject_no in (select s.subject_no " +
                    "                                                                                            from subject s " +
                    "                                                                                            where s.subject_name " + params.get("subjectName") + " ))) " +
                    "                         and vm.complete_mark < 60) " +
                    "   or student_code in (select vm.student_code " +
                    "                       from vidomist_mark vm " +
                    "                       where vm.vidomist_no in (select v.vidomist_no " +
                    "                                                from vidomist v " +
                    "                                                where v.control_type = 'екзамен' " +
                    "                                                  and v.tutor_no in (select t.tutor_no " +
                    "                                                                     from tutor t " +
                    "                                                                     where tutor_name " + params.get("tutorName") + " ) " +
                    "                                                  and v.group_code in (select group_code " +
                    "                                                                       from \"group\" g " +
                    "                                                                       where g.group_code in (select gr.group_code " +
                    "                                                                                              from \"group\" gr " +
                    "                                                                                              where gr.group_name " + params.get("groupName") + " ) " +
                    "                                                                         and g.trim " + params.get("trim") + " " +
                    "                                                                         and g.course " + params.get("course") + " " +
                    "                                                                         and edu_year " + params.get("eduYear") + " " +
                    "                                                                         and subject_no in (select s.subject_no " +
                    "                                                                                            from subject s " +
                    "                                                                                            where s.subject_name " + params.get("subjectName") + " ))) " +
                    "                         and vm.complete_mark < 61) " +
                    "order by " + params.get("sortType") + " " + params.get("sortGrow") + " " +
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

}
