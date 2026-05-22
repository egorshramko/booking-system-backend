create sequence if not exists cin_sess_pkey_seq start with 1 increment by 1;
create sequence if not exists cinema_pkey_seq start with 1 increment by 1;
create sequence if not exists employee_pkey_seq start with 1 increment by 1;
create sequence if not exists movie_pkey_seq start with 1 increment by 1;
create sequence if not exists permission_pkey_seq start with 1 increment by 1;
create sequence if not exists profile_pkey_seq start with 1 increment by 1;
create sequence if not exists role_pkey_seq start with 1 increment by 1;
create sequence if not exists screening_pkey_seq start with 1 increment by 1;
create sequence if not exists ticket_pkey_seq start with 1 increment by 1;
create sequence if not exists user_pkey_seq start with 1 increment by 1;


create table if not exists cinema (
    actual boolean,
    created_at timestamp(6),
    id bigint not null,
    address varchar(255) not null,
    city varchar(255) not null,
    name varchar(255) not null,
    seating_chart_json varchar(8192),
    primary key (id)
);

create table if not exists cinema_session (
    actual boolean,
    places_left integer,
    price integer,
    created_at timestamp(6),
    id bigint not null,
    screening_id bigint,
    session_date timestamp(6),
    primary key (id)
);

create table if not exists employee (
    actual boolean,
    manager boolean,
    cinema_id bigint,
    created_at timestamp(6),
    id bigint not null,
    profile_id bigint unique,
    position varchar(255),
    primary key (id)
);

create table if not exists movie (
    actual boolean,
    release_year integer,
    created_at timestamp(6),
    id bigint not null,
    age_limit varchar(255) check ((age_limit in ('NO_LIMIT','OLDER_6','OLDER_12','OLDER_16','ADULT_ONLY'))),
    name varchar(255),
    poster_filename varchar(255),
    primary key (id)
);

create table if not exists permission_ (
    actual boolean,
    created_at timestamp(6),
    id bigint not null,
    object varchar(255) check ((object in ('USER'))),
    type varchar(255) check ((type in ('CREATE','READ','UPDATE','DELETE'))),
    primary key (id)
);

create table if not exists profile
    (birth_date date,
    created_at timestamp(6),
    id bigint not null,
    user_id bigint unique,
    first_name varchar(255),
    last_name varchar(255),
    primary key (id)
);

create table if not exists role_ (
    actual boolean,
    created_at timestamp(6),
    id bigint not null,
    name varchar(255),
    primary key (id)
);

create table if not exists role_permission (
    permission_ bigint not null,
    role_ bigint not null,
    primary key (permission_, role_)
);

create table if not exists screening (
    actual boolean,
    end_date date,
    start_date date,
    cinema_id bigint,
    created_at timestamp(6),
    id bigint not null,
    movie_id bigint,
    primary key (id)
);

create table if not exists ticket (
    actual boolean,
    place integer,
    row integer,
    created_at timestamp(6),
    id bigint not null,
    profile_id bigint,
    session_id bigint,
    status varchar(255)
    check ((status in ('BOOKED','PAID','CANCELLED'))),
    primary key (id)
);

create table if not exists user_ (
    actual boolean,
    created_at timestamp(6),
    id bigint not null,
    password varchar(255),
    username varchar(255) unique,
    primary key (id)
);

create table if not exists user_role (
    role_ bigint not null,
    user_ bigint not null,
    primary key (role_, user_)
);


alter table if exists cinema_session add constraint cin_sess_screening_fk foreign key (screening_id) references screening
alter table if exists employee add constraint employee_cinema_fk foreign key (cinema_id) references cinema
alter table if exists employee add constraint employee_profile_fk foreign key (profile_id) references profile
alter table if exists profile add constraint profile_user_fk foreign key (user_id) references user_
alter table if exists role_permission add constraint role_permission_permission_fk foreign key (permission_) references permission_
alter table if exists role_permission add constraint role_permission_role_fk foreign key (role_) references role_
alter table if exists screening add constraint screening_cinema_fk foreign key (cinema_id) references cinema
alter table if exists screening add constraint screening_movie_fk foreign key (movie_id) references movie
alter table if exists ticket add constraint ticket_profile_fk foreign key (profile_id) references profile
alter table if exists ticket add constraint ticket_session_fk foreign key (session_id) references cinema_session
alter table if exists user_role add constraint user_role_role_fk foreign key (role_) references role_
alter table if exists user_role add constraint user_role_user_fk foreign key (user_) references user_