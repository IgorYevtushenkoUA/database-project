package com.project.database.serviceHibernate;

import com.project.database.dto.statement.StatementReport;
import com.project.database.dto.statement.info.StatementInfo;
import com.project.database.entities.VidomistEntity;
import com.project.database.parser.parserStatement.StatementParser;
import com.project.database.repository.VidomistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StatementServiceH {
    private final VidomistRepository vidomistRepository;


    public Optional<StatementInfo> getStatementInfo(int statementId) {
        return Optional.empty();
    }

    public Integer saveStatement(StatementReport statementFileName){
        return 12345;
    }


    public Page<List<String>> findAllStudentVidomosties(int studentCode, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return vidomistRepository.findAllStudentVidomosties(studentCode, pageable);
    }


    public void insertVidomist(VidomistEntity vidomist) {
        if (vidomistRepository.findByVidomistNo(vidomist.getVidomistNo()) == null) {
            vidomistRepository.save(vidomist);
        }
    }


    public void deleteVidomistById(int vidomistNo) {
        vidomistRepository.deleteById(vidomistNo);
    }


    public Page<VidomistEntity> findAllByTutorNo(Integer tutorNo, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return vidomistRepository.findAllByTutorNo(tutorNo, pageable);
    }


    public Page<VidomistEntity> findAllBySubjectNo(Integer subjectNo, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return vidomistRepository.findAllBySubjectNo(subjectNo, pageable);
    }


    public Page<VidomistEntity> findAllByGroupName(String groupName, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return vidomistRepository.findAllByGroupName(groupName, pageable);
    }


    public int nonAdmissionByGroupCode(Integer groupCode) {
        return vidomistRepository.nonAdmissionByGroupCode(groupCode);
    }


    public int nonAdmissionBySubjectCode(Integer subjectNo) {
        return vidomistRepository.nonAdmissionBySubjectCode(subjectNo);
    }


    public int nonAdmissionByTeacherNo(Integer tutorNo) {
        return vidomistRepository.nonAdmissionByTeacherNo(tutorNo);
    }


    public StatementReport processStatement(MultipartFile file) throws FileAlreadyExistsException, IOException {
        final Path statementFolder = Paths.get("statement").toAbsolutePath().normalize();
        Files.createDirectories(statementFolder);
        Path targetLocation = statementFolder.resolve(Objects.requireNonNull(file.getOriginalFilename()));
//        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        file.transferTo(targetLocation);

        final StatementParser parser = new StatementParser();
        return parser.getStatementsReportByRoot(targetLocation);
    }
}
