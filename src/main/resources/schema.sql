DROP TABLE IF EXISTS user_dates;
DROP TABLE IF EXISTS student_team;
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS dates;
DROP TABLE IF EXISTS students;
create table teams
(
    id     serial not null
        constraint teams_pk
            primary key,
    leader varchar
);

alter table teams
    owner to postgres;



create table students
(
    id           serial not null
        constraint students_pk
            primary key,
    name         varchar,
    primaryscore integer,
    score        integer
);

alter table students
    owner to postgres;


create table student_team
(
    student_id integer
        constraint student_team_student_id_fkey
            references students,
    team_id    integer
        constraint student_team_team_id_fkey
            references teams
);

alter table student_team
    owner to postgres;

create unique index bebebe2
    on student_team (student_id, team_id);

create table dates
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


create table user_dates
(
    user_id integer
        constraint user_dates_user_id_fkey
            references students,
    date_id integer
        constraint user_dates_date_id_fkey
            references dates
);

alter table user_dates
    owner to postgres;

create unique index bebebe
    on user_dates (user_id, date_id);


