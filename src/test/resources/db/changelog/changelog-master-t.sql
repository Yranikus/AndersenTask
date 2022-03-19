--liquibase formatted sql

--changeset create schema:0

DROP TABLE IF EXISTS marksforlessons;
DROP TABLE IF EXISTS user_dates;
DROP TABLE IF EXISTS dates;
DROP TABLE IF EXISTS student_team;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS teams;

--changeset create teams:1


create table if not exists teams
(
    id     serial not null
        constraint teams_pk
            primary key,
    leader varchar,
    repo varchar unique
);

alter table teams
    owner to postgres;

--changeset create students:2

create table if not exists students
(
    id           serial not null
        constraint students_pk
            primary key,
    name         varchar,
    primaryscore integer,
    score        double precision,
    status       integer
);

alter table students
    owner to postgres;

--changeset create student_team:3

create table if not exists student_team
(
    student_id integer,
    team_id    integer,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (team_id) REFERENCES teams(id)
);

alter table student_team
    owner to postgres;

create unique index bebebe2
    on student_team (student_id, team_id);


--changeset create dates:4

create table if not exists dates
(
    id   serial not null
        constraint dates_pk
            primary key,
    date date
);

alter table dates
    owner to postgres;

create unique index dates_id_uindex
    on dates (id);


--changeset create user_dates:5

create table if not exists user_dates
(

    user_id integer,
    date_id integer,
    FOREIGN KEY (user_id) REFERENCES students(id),
    FOREIGN KEY (date_id) REFERENCES dates(id)
);

alter table user_dates
    owner to postgres;

create unique index bebebe
    on user_dates (user_id, date_id);

--changeset create marksforlessons:6

create table if not exists marksforlessons
(
    answer     double precision,
    question   double precision,
    student_id integer
        constraint marksforlessons_student_id_fkey
            references students,
    date_id    integer
        constraint marksforlessons_date_id_fkey
            references dates
);

alter table marksforlessons
    owner to postgres;

create unique index kek
    on marksforlessons (student_id, date_id);


