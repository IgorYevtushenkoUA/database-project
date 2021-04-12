create table tutor
(
    tutor_no         serial      not null
        constraint tutor_pk
            primary key,
    tutor_name       varchar(32) not null,
    tutor_surname    varchar(32) not null,
    tutor_patronymic varchar(32),
    science_degree   varchar(60),
    academ_status    varchar(60),
    position         varchar(60)
);

create table subject
(
    subject_no  serial       not null
        constraint subject_pk
            primary key,
    subject_name varchar(128) not null,
    edu_level   char(8)      not null,
    faculty     varchar(64)  not null
);

create table student
(
    student_code        serial      not null
        constraint student_pk
            primary key,
    student_surname     varchar(32) not null,
    student_name        varchar(32) not null,
    student_patronymic  varchar(32),
    student_record_book varchar(20)
);



create table "group"
(
    group_code serial      not null
        constraint group_pk
            primary key,
    group_name varchar(16) not null,
    edu_year   char(9)     not null,
    trim       char(2)     not null,
    course     integer     not null,
    subject_no integer     not null
        constraint fk_subject_no
            references "subject"
            on update cascade
);

create table bigunets
(
    bigunets_no  serial      not null
        constraint bigunets_pk
            primary key,
    exam_date    date        not null,
    valid_until  date,
    postp_reason varchar(40) not null,
    control_type char(10)    not null,
    tutor_code   integer     not null
        constraint fk_tutor
            references tutor
            on update cascade
);

create table vidomist
(
    vidomist_no    serial   not null
        constraint vidomost_pk
            primary key,
    tutor_no       serial   not null
        constraint fk_tutor
            references tutor
            on update cascade,
    present_count  smallint not null,
    absent_count   smallint not null,
    rejected_count smallint not null,
    control_type   char(7)  not null,
    exam_date      date     not null,
    group_code     integer  not null
        constraint fk_group
            references "group"
            on update cascade
);

create table bigunets_mark
(
    bigunets_no   integer     not null
        constraint fk_bigunets
            references bigunets
            on update cascade on delete cascade,
    student_code  integer     not null
        constraint fk_student
            references student
            on update cascade on delete cascade,
    vidomist_no   integer     not null
        constraint fk_bigunets_mark_vidomist
            references vidomist
            on update cascade on delete cascade,
    tutor_no      integer     not null
        constraint fk_tutor
            references tutor
            on update cascade on delete cascade,
    trim_mark     smallint    not null,
    mark_check    smallint    not null,
    complete_mark smallint    not null,
    nat_mark      varchar(30) not null,
    ects_mark     char(2)     not null,
    constraint bigunets_mark_pk
        unique (bigunets_no, vidomist_no, student_code, tutor_no)
);


create table vidomist_mark
(
    vidomist_no   integer     not null
        constraint fk_vidomist_mark_idomist
            references vidomist
            on update cascade on delete cascade,
    student_code  integer     not null
        constraint fk_student
            references student
            on update cascade on delete cascade,
    trim_mark     smallint    not null,
    nat_mark      varchar(32) not null,
    mark_check    smallint,
    complete_mark smallint,
    ects_mark     varchar(2),
    constraint vidomist_mark_pk
        primary key (student_code, vidomist_no)
);
