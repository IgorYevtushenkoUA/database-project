-- tutor
INSERT INTO "tutor" (tutor_name, tutor_surname, tutor_patronymic, science_degree)
VALUES ('Наталія', 'Гулаєва', 'Михаліївна', 'доцент');
INSERT INTO "tutor" (tutor_name, tutor_surname, tutor_patronymic, science_degree)
VALUES ('Юрій', 'Ющенко', 'Олексійович', 'доцент');
INSERT INTO "tutor" (tutor_name, tutor_surname, tutor_patronymic, position)
VALUES ('Артем', 'Карпович', 'Валерійович', 'аспірант');
INSERT INTO "tutor" (tutor_name, tutor_surname, tutor_patronymic, position)
VALUES ('Ярослав', 'Вознюк', 'Іванович', 'аспірант');
INSERT INTO "tutor" (tutor_name, tutor_surname, tutor_patronymic, position)
VALUES ('Оксана', 'Кірієнко', 'Валентинівна', 'старший викладач');
INSERT INTO "tutor" (tutor_name, tutor_surname, tutor_patronymic, position)
VALUES ('Олена', 'Пєчкурова', 'Миколаївна', 'старший викладач');
INSERT INTO "tutor" (tutor_name, tutor_surname, tutor_patronymic, science_degree)
VALUES ('Андрій', 'Глибовець', 'Миколайович', 'доцент');
INSERT INTO "tutor" (tutor_name, tutor_surname, tutor_patronymic, position)
VALUES ('Юрій', 'Захарійченко', 'Олексійович', 'старший викладач');
INSERT INTO "tutor" (tutor_name, tutor_surname, tutor_patronymic, position)
VALUES ('Сергій', 'Борозений', 'Олександрович', 'старший викладач');


-- subject
INSERT INTO "subject" (subject_name, edu_level, faculty)
VALUES ('БД', 'бакалавр', 'ФІ');
INSERT INTO "subject" (subject_name, edu_level, faculty)
VALUES ('Логічне програмування', 'бакалавр', 'ФІ');
INSERT INTO "subject" (subject_name, edu_level, faculty)
VALUES ('Веб-програмування', 'бакалавр', 'ФІ');
INSERT INTO "subject" (subject_name, edu_level, faculty)
VALUES ('Теорія ймовірностей', 'бакалавр', 'ФІ');
INSERT INTO "subject" (subject_name, edu_level, faculty)
VALUES ('Вірусологія', 'бакалавр', 'ФІ');
INSERT INTO "subject" (subject_name, edu_level, faculty)
VALUES ('Психологія впливу', 'бакалавр', 'ФСНСТ');
INSERT INTO "subject" (subject_name, edu_level, faculty)
VALUES ('Психологія успіху', 'бакалавр', 'ФСНСТ');
INSERT INTO "subject" (subject_name, edu_level, faculty)
VALUES ('Філософія', 'бакалавр', 'ФСНСТ');
INSERT INTO "subject" (subject_name, edu_level, faculty)
VALUES ('Моделювання інформаційних процесів', 'магістр', 'ФІ');


-- student
INSERT INTO "student" (student_surname, student_name, student_patronymic, student_record_book)
VALUES ('Кучерявий', 'Вадим', 'Юрійович', 13113);
INSERT INTO "student" ( student_surname, student_name, student_patronymic, student_record_book)
VALUES ('Синицин', 'Владислав', 'Олександрович', 32432);
INSERT INTO "student" ( student_surname, student_name, student_patronymic, student_record_book)
VALUES ('Євтушенко', 'Ігор', 'Олегович', 83921);
INSERT INTO "student" ( student_surname, student_name, student_patronymic, student_record_book)
VALUES ('Бойко', 'Данило', 'Романович', 37452);
INSERT INTO "student" ( student_surname, student_name, student_patronymic, student_record_book)
VALUES ('Ландяк', 'Андрій', 'Петрович', 22113);
INSERT INTO "student" ( student_surname, student_name, student_patronymic, student_record_book)
VALUES ('Крейдун', 'Андрій', 'Миколайович', 33122);
INSERT INTO "student" ( student_surname, student_name, student_patronymic, student_record_book)
VALUES ('Залізний', 'Антон', 'Вячеславович', 32432);
INSERT INTO "student" ( student_surname, student_name, student_patronymic, student_record_book)
VALUES ('Іванюк', 'Назар', 'Олександрович', 34123);
INSERT INTO "student" ( student_surname, student_name, student_patronymic, student_record_book)
VALUES ('Сорокопуд', 'Юлія', 'Миколаївна', 13887);
INSERT INTO "student" ( student_surname, student_name, student_patronymic, student_record_book)
VALUES ('Рибак', 'Володимир', 'Ярославович', 22113);


-- group
INSERT INTO "group" (group_name, edu_year, trim, course, subject_no)
VALUES ('Група1', '2020-2021', '2', 3, 1);
INSERT INTO "group" (group_name, edu_year, trim, course, subject_no)
VALUES ('Група2', '2020-2021', '2', 3, 1);
INSERT INTO "group" (group_name, edu_year, trim, course, subject_no)
VALUES ('Група3', '2020-2021', '2', 3, 1);
INSERT INTO "group" (group_name, edu_year, trim, course, subject_no)
VALUES ('Група4', '2020-2021', '2', 3, 1);
INSERT INTO "group" (group_name, edu_year, trim, course, subject_no)
VALUES ('Група1', '2021-2022', '1д', 2, 2);
INSERT INTO "group" (group_name, edu_year, trim, course, subject_no)
VALUES ('Група2', '2021-2022', '1д', 2, 2);
INSERT INTO "group" (group_name, edu_year, trim, course, subject_no)
VALUES ('Група1', '2020-2021', '3', 1, 3);
INSERT INTO "group" (group_name, edu_year, trim, course, subject_no)
VALUES ('Група2', '2020-2021', '3', 1, 3);
INSERT INTO "group" (group_name, edu_year, trim, course, subject_no)
VALUES ('Група1', '2021-2022', '7', 4, 4);
INSERT INTO "group" (group_name, edu_year, trim, course, subject_no)
VALUES ('Група1', '2021-2022', '1', 1, 5);
INSERT INTO "group" (group_name, edu_year, trim, course, subject_no)
VALUES ('Група2', '2021-2022', '1', 1, 5);
INSERT INTO "group" (group_name, edu_year, trim, course, subject_no)
VALUES ('Група3', '2021-2022', '1', 1, 5);


-- bigunets
INSERT INTO "bigunets" (exam_date, valid_until, postp_reason, control_type, tutor_code)
VALUES ('2021-05-22', '2021-05-24', 'хвороба', 'залік', 1);
INSERT INTO "bigunets" (exam_date, valid_until, postp_reason, control_type, tutor_code)
VALUES ('2021-05-22', '2021-05-24', 'хвороба', 'залік', 1);
INSERT INTO "bigunets" (exam_date, valid_until, postp_reason, control_type, tutor_code)
VALUES ('2021-05-11', '2021-05-30', 'інше', 'екзамен', 2);
INSERT INTO "bigunets" (exam_date, valid_until, postp_reason, control_type, tutor_code)
VALUES ('2021-05-08', '2021-05-12', 'академ заборгованність', 'екзамен', 3);
INSERT INTO "bigunets" (exam_date, valid_until, postp_reason, control_type, tutor_code)
VALUES ('2021-06-04', '2021-06-12', 'інше', 'екзамен', 1);
INSERT INTO "bigunets" (exam_date, valid_until, postp_reason, control_type, tutor_code)
VALUES ('2021-04-13', '2021-05-12', 'інше', 'екзамен', 1);


-- vidomist
INSERT INTO "vidomist" (tutor_no, present_count, absent_count, rejected_count, control_type, exam_date,
                      group_code)
VALUES (1, 30, 2, 1, 'залік', '2021-05-25', 1);
INSERT INTO "vidomist" (tutor_no, present_count, absent_count, rejected_count, control_type, exam_date,
                      group_code)
VALUES (2, 40, 0, 0, 'залік', '2021-05-11', 2);
INSERT INTO "vidomist" (tutor_no, present_count, absent_count, rejected_count, control_type, exam_date,
                      group_code)
VALUES (2, 35, 5, 0, 'залік', '2021-05-13', 4);
INSERT INTO "vidomist" (tutor_no, present_count, absent_count, rejected_count, control_type, exam_date,
                      group_code)
VALUES (3, 30, 2, 0, 'залік', '2021-05-12', 1);
INSERT INTO "vidomist" (tutor_no, present_count, absent_count, rejected_count, control_type, exam_date,
                      group_code)
VALUES (3, 30, 2, 0, 'залік', '2021-05-15', 5);


-- bigunets_mark
INSERT INTO "bigunets_mark" (bigunets_no, student_code, vidomist_no, tutor_no, trim_mark, mark_check, complete_mark,
                           nat_mark, ects_mark)
VALUES (1, 1, 1, 1, 43, 30, 73, 'добре', 'C');
INSERT INTO "bigunets_mark" (bigunets_no, student_code, vidomist_no, tutor_no, trim_mark, mark_check, complete_mark,
                           nat_mark, ects_mark)
VALUES (2, 2, 2, 2, 30, 30, 60, 'задовільно', 'E');
INSERT INTO "bigunets_mark" (bigunets_no, student_code, vidomist_no, tutor_no, trim_mark, mark_check, complete_mark,
                           nat_mark, ects_mark)
VALUES (3, 5, 3, 2, 32, 30, 62, 'задовільно', 'E');
INSERT INTO "bigunets_mark" (bigunets_no, student_code, vidomist_no, tutor_no, trim_mark, mark_check, complete_mark,
                           nat_mark, ects_mark)
VALUES (4, 6, 4, 2, 31, 30, 61, 'задовільно', 'E');


-- vidomist_mark
INSERT INTO "vidomist_mark" (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (1, 1, 55, 30, 85, 'добре', 'B');
INSERT INTO "vidomist_mark" (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (1, 2, 55, 30, 85, 'добре', 'B');
INSERT INTO "vidomist_mark" (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (1, 3, 35, 26, 61, 'задовільно', 'E');
INSERT INTO "vidomist_mark" (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (1, 4, 55, 38, 93, 'відмінно', 'A');
INSERT INTO "vidomist_mark" (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (1, 5, 69, 30, 99, 'відмінно', 'A');
INSERT INTO "vidomist_mark" (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (2, 8, 61, 30, 91, 'відмінно', 'A');
INSERT INTO "vidomist_mark" (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (2, 6, 35, 30, 65, 'задовільно', 'E');
INSERT INTO "vidomist_mark" (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (2, 3, 55, 39, 94, 'відмінно', 'A');
INSERT INTO "vidomist_mark" (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (2, 2, 33, 45, 78, 'добре', 'C');
