INSERT INTO tutor (tutor_no, tutor_name, tutor_surname, tutor_patronymic, science_degree)
VALUES (1, 'Наталія', 'Гулаєва', 'Михаліївна', 'доцент');
INSERT INTO tutor (tutor_no, tutor_name, tutor_surname, tutor_patronymic, science_degree)
VALUES (2, 'Юрій', 'Ющенко', 'Олексійович', 'доцент');
INSERT INTO tutor (tutor_no, tutor_name, tutor_surname, tutor_patronymic, position)
VALUES (3, 'Артем', 'Карпович', 'Валерійович', 'аспірант') ;
INSERT INTO tutor (tutor_no, tutor_name, tutor_surname, tutor_patronymic, position)
VALUES (4, 'Ярослав', 'Вознюк', 'Іванович', 'аспірант') ;
INSERT INTO tutor (tutor_no, tutor_name, tutor_surname, tutor_patronymic, position)
VALUES (5, 'Оксана', 'Кірієнко', 'Валентинівна', 'старший викладач');
INSERT INTO tutor (tutor_no, tutor_name, tutor_surname, tutor_patronymic, position)
VALUES (6, 'Олена', 'Пєчкурова', 'Миколаївна', 'старший викладач') ;
INSERT INTO tutor (tutor_no, tutor_name, tutor_surname, tutor_patronymic, science_degree)
VALUES (7, 'Андрій', 'Глибовець', 'Миколайович', 'доцент') ;
INSERT INTO tutor (tutor_no, tutor_name, tutor_surname, tutor_patronymic, position)
VALUES (8, 'Юрій', 'Захарійченко', 'Олексійович', 'старший викладач') ;
INSERT INTO tutor (tutor_no, tutor_name, tutor_surname, tutor_patronymic, position)
VALUES (9, 'Сергій', 'Борозений', 'Олександрович', 'старший викладач') ;


INSERT INTO subject (subject_no, subject_name, edu_level, faculty)
VALUES (1, 'БД', 'бакалавр', 'ФІ');
INSERT INTO subject (subject_no, subject_name, edu_level, faculty)
VALUES (2, 'Логічне програмування', 'бакалавр', 'ФІ');
INSERT INTO subject (subject_no, subject_name, edu_level, faculty)
VALUES (3, 'Веб-програмування', 'бакалавр', 'ФІ');
INSERT INTO subject (subject_no, subject_name, edu_level, faculty)
VALUES (4, 'Теорія ймовірностей', 'бакалавр', 'ФІ');
INSERT INTO subject (subject_no, subject_name, edu_level, faculty)
VALUES (5, 'Вірусологія' , 'бакалавр', 'ФІ');
INSERT INTO subject (subject_no, subject_name, edu_level, faculty)
VALUES (6, 'Психологія впливу', 'бакалавр', 'ФСНСТ');
INSERT INTO subject (subject_no, subject_name, edu_level, faculty)
VALUES (7, 'Психологія успіху', 'бакалавр', 'ФСНСТ');
INSERT INTO subject (subject_no, subject_name, edu_level, faculty)
VALUES (8, 'Філософія', 'бакалавр', 'ФСНСТ');
INSERT INTO subject (subject_no, subject_name, edu_level, faculty)
VALUES (9, 'Моделювання інформаційних процесів', 'магістр', 'ФІ');

INSERT INTO student (student_code, student_surname, student_name, student_patronymic, student_record_book)
VALUES (1, 'Кучерявий', 'Вадим', 'Юрійович', 13113);
INSERT INTO student (student_code, student_surname, student_name, student_patronymic, student_record_book)
VALUES (2, 'Синицин', 'Владислав', 'Олександрович', 32432);
INSERT INTO student (student_code, student_surname, student_name, student_patronymic, student_record_book)
VALUES (3, 'Євтушенко', 'Ігор', 'Олегович', 83921);
INSERT INTO student (student_code, student_surname, student_name, student_patronymic, student_record_book)
VALUES (4, 'Бойко', 'Данило', 'Романович', 37452);
INSERT INTO student (student_code, student_surname, student_name, student_patronymic, student_record_book)
VALUES (5, 'Ландяк', 'Андрій', 'Петрович', 22113);
INSERT INTO student (student_code, student_surname, student_name, student_patronymic, student_record_book)
VALUES (6, 'Крейдун', 'Андрій', 'Миколайович', 33122);
INSERT INTO student (student_code, student_surname, student_name, student_patronymic, student_record_book)
VALUES (7, 'Залізний', 'Антон', 'Вячеславович', 32432);
INSERT INTO student (student_code, student_surname, student_name, student_patronymic, student_record_book)
VALUES (8, 'Іванюк', 'Назар', 'Олександрович', 34123);
INSERT INTO student (student_code, student_surname, student_name, student_patronymic, student_record_book)
VALUES (9, 'Сорокопуд', 'Юлія', 'Миколаївна', 13887);
INSERT INTO student (student_code, student_surname, student_name, student_patronymic, student_record_book)
VALUES (10, 'Рибак', 'Володимир', 'Ярославович', 22113);

INSERT INTO "group" (group_code, group_name, edu_year, trim, course, subject_no)
VALUES (1, 'Група1', '2020-2021', '2', 3, 1);
INSERT INTO "group" (group_code, group_name, edu_year, trim, course, subject_no)
VALUES (2, 'Група2', '2020-2021', '2', 3, 1);
INSERT INTO "group" (group_code, group_name, edu_year, trim, course, subject_no)
VALUES (3, 'Група3', '2020-2021', '2', 3, 1);
INSERT INTO "group" (group_code, group_name, edu_year, trim, course, subject_no)
VALUES (4, 'Група4', '2020-2021', '2', 3, 1);
INSERT INTO "group" (group_code, group_name, edu_year, trim, course, subject_no)
VALUES (5, 'Група1', '2021-2022', '1д', 2, 2);
INSERT INTO "group" (group_code, group_name, edu_year, trim, course, subject_no)
VALUES (6, 'Група2', '2021-2022', '1д', 2, 2);
INSERT INTO "group" (group_code, group_name, edu_year, trim, course, subject_no)
VALUES (7, 'Група1', '2020-2021', '3', 1, 3);
INSERT INTO "group" (group_code, group_name, edu_year, trim, course, subject_no)
VALUES (8, 'Група2', '2020-2021', '3', 1, 3);
INSERT INTO "group" (group_code, group_name, edu_year, trim, course, subject_no)
VALUES (9, 'Група1', '2021-2022', '7', 4, 4);
INSERT INTO "group" (group_code, group_name, edu_year, trim, course, subject_no)
VALUES (10, 'Група1', '2021-2022', '1', 1, 5);
INSERT INTO "group" (group_code, group_name, edu_year, trim, course, subject_no)
VALUES (11, 'Група2', '2021-2022', '1', 1, 5);
INSERT INTO "group" (group_code, group_name, edu_year, trim, course, subject_no)
VALUES (12, 'Група3', '2021-2022', '1', 1, 5);

INSERT INTO bigunets (bigunets_no, exam_date, valid_until, postp_reason, control_type, tutor_code)
VALUES (1000000, '22.05.2021', '24.05.2021', 'хвороба','залік', 1);
INSERT INTO bigunets (bigunets_no, exam_date, valid_until, postp_reason, control_type, tutor_code)
VALUES (1000001, '22.05.2021', '24.05.2021', 'хвороба','залік', 1);
INSERT INTO bigunets (bigunets_no, exam_date, valid_until, postp_reason, control_type, tutor_code)
VALUES (1000002, '11.05.2021', '30.05.2021', 'інше','екзамен', 2);
INSERT INTO bigunets (bigunets_no, exam_date, valid_until, postp_reason, control_type, tutor_code)
VALUES (1000003, '08.05.2021', '12.05.2021', 'академ заборгованність','екзамен', 3);
INSERT INTO bigunets (bigunets_no, exam_date, valid_until, postp_reason, control_type, tutor_code)
VALUES (1000004, '04.06.2021', '12.06.2021', 'інше','екзамен', 1);
INSERT INTO bigunets (bigunets_no, exam_date, valid_until, postp_reason, control_type, tutor_code)
VALUES (1000005, '13.04.2021', '12.05.2021', 'інше','екзамен', 1);

INSERT INTO vidomist (vidomist_no, tutor_no, present_count, absent_count, rejected_count, control_type, exam_date, group_code)
VALUES (1, 1, 30, 2, 1, 'залік', '25.05.2021', 1);
INSERT INTO vidomist (vidomist_no, tutor_no, present_count, absent_count, rejected_count, control_type, exam_date, group_code)
VALUES (2, 2, 40, 0, 0, 'залік', '11.05.2021', 2);
INSERT INTO vidomist (vidomist_no, tutor_no, present_count, absent_count, rejected_count, control_type, exam_date, group_code)
VALUES (3, 2, 35, 5, 0, 'залік', '13.05.2021', 4);
INSERT INTO vidomist (vidomist_no, tutor_no, present_count, absent_count, rejected_count, control_type, exam_date, group_code)
VALUES (4, 3, 30, 2, 0, 'залік', '12.05.2021', 1);
INSERT INTO vidomist (vidomist_no, tutor_no, present_count, absent_count, rejected_count, control_type, exam_date, group_code)
VALUES (5, 3, 30, 2, 0, 'залік', '15.05.2021', 5);

INSERT INTO bigunets_mark (bigunets_no, student_code, vidomist_no, tutor_no, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (1000001, 1, 1, 1, 43, 30, 73, 'добре', 'C' );
INSERT INTO bigunets_mark (bigunets_no, student_code, vidomist_no, tutor_no, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (1000002, 2, 2, 2, 30, 30, 60, 'задовільно', 'E' );
INSERT INTO bigunets_mark (bigunets_no, student_code, vidomist_no, tutor_no, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (1000003, 5, 3, 2, 32, 30, 62, 'задовільно', 'E' );
INSERT INTO bigunets_mark (bigunets_no, student_code, vidomist_no, tutor_no, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (1000004, 6, 4, 2, 31, 30, 61, 'задовільно', 'E' );

INSERT INTO vidomist_mark (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (1, 1, 55, 30, 85, 'добре', 'B' );
INSERT INTO vidomist_mark (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (1, 2, 55, 30, 85, 'добре', 'B' );
INSERT INTO vidomist_mark (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (1, 3, 35, 26, 61, 'задовільно', 'E' );
INSERT INTO vidomist_mark (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (1, 4, 55, 38, 93, 'відмінно', 'A' );
INSERT INTO vidomist_mark (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (1, 5, 69, 30, 99, 'відмінно', 'A' );
INSERT INTO vidomist_mark (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (2, 8, 61, 30, 91, 'відмінно', 'A' );
INSERT INTO vidomist_mark (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (2, 6, 35, 30, 65, 'задовільно', 'E' );
INSERT INTO vidomist_mark (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (2, 3, 55, 39, 94, 'відмінно', 'A' );
INSERT INTO vidomist_mark (vidomist_no, student_code, trim_mark, mark_check, complete_mark, nat_mark, ects_mark)
VALUES (2, 2, 33, 45, 78, 'добре', 'C' );
