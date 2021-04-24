package com.project.database.serviceHibernate;

import com.project.database.entities.TutorEntity;
import com.project.database.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class TutorServiceH {
    @Autowired
    private TutorRepository tutorRepository;

    /**
     * @param tutorNo
     */
    public void deleteByTutorNo(int tutorNo) {
        tutorRepository.deleteByTutorNo(tutorNo);
    }

    /**
     * @return [TutorEntity(tutorNo = 1, tutorName = Наталія, tutorSurname = Гулаєва, tutorPatronymic = Михаліївна, scienceDegree = доцент, academStatus = null, position = null), ...]
     */
    public Page<TutorEntity> findAll(int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return tutorRepository.findAll(pageable);
    }

    /**
     * @return [Гулаєва, Наталія, Михаліївна], [Ющенко, Юрій, Олексійович],
     */
    public List<String> findAllTutorNames() {
        return tutorRepository.findAllTutorNames().stream().flatMap(fullname ->
                Stream.of(
                        String.join(" ", fullname.get(0), fullname.get(1), fullname.get(2))
                )).distinct().collect(Collectors.toList());
    }

    /**
     * @param name
     * @return [[Ющенко, Юрій, Олексійович],....]
     */
    public List<List<String>> findAllTutorNamesByPartOFName(String name) {
        return tutorRepository.findAllTutorNamesByPartOFName("%" + name + "%");
    }

    // insert
    public void insertTutor(TutorEntity tutor) {
        if (tutorRepository.findByTutorSurnameAndTutorNameAndTutorPatronymic(tutor.getTutorSurname(), tutor.getTutorName(), tutor.getTutorPatronymic()) == null) {
            tutorRepository.save(tutor);
            System.out.println("tutorRepository.save(tutor);");
        }
        System.out.println("WE ARE HERE; all is working ypa");
    }

    // delete
    public void deleteTutorById(int tutorNo) {
        tutorRepository.deleteByTutorNo(tutorNo);
    }

    public TutorEntity findByPIB(String surname, String name, String patronymic) {
        return tutorRepository.findByTutorSurnameAndTutorNameAndTutorPatronymic(
                surname, name, patronymic);
    }

}
