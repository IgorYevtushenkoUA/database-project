create table "user_entity" (
     user_id    serial          not null    constraint user_pk primary key,
     username   varchar(255)    not null,
     password   varchar(255)    not null,
     role       varchar(40)     not null
);

