insert into subject(subject_no,subject_name, edu_level, faculty) values (10,'test1', 'test1', 'test1');
insert into subject(subject_no,subject_name, edu_level, faculty) values (11,'test2', 'test2', 'test2');
insert into subject(subject_no,subject_name, edu_level, faculty) values (12,'test3', 'test3', 'test3');
insert into subject(subject_no,subject_name, edu_level, faculty) values (13,'test4', 'test4', 'test4');
insert into subject(subject_no,subject_name, edu_level, faculty) values (14,'test5', 'test5', 'test5');
insert into subject(subject_no,subject_name, edu_level, faculty) values (15,'test6', 'test6', 'test6');



select new s.student_code, s.student_surname,s.student_name, s.student_patronymic, s.student_record_book, avg(vm.complete_mark) as completeMark,  gr.course as course, gr.trim
           from \"group\" gr
           inner join vidomist v on gr.group_code = v.group_code
           inner join vidomist_mark vm on v.vidomist_no = vm.vidomist_no
           inner join student s on s.student_code = vm.student_code
           where exists(
           select s0.student_code, s0.student_surname, gr0.edu_year, gr0.course, gr0.trim
           from \"group\" gr0
           inner join vidomist v0 on gr0.group_code = v0.group_code
           inner join vidomist_mark vm0 on v0.vidomist_no = vm0.vidomist_no
           inner join student s0 on s0.student_code = vm0.student_code
           where s.student_code = s0.student_code
           group by s0.student_code, s0.student_surname, gr0.edu_year, gr0.course, gr0.trim
           having gr.course = max(gr0.course)
           and gr.trim = max(gr0.trim)
           )
           group by s.student_code, s.student_surname,s.student_name, s.student_patronymic, s.student_record_book,  gr.course, gr.trim