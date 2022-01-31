drop table if exists users;

create table if not exists users
(
    id SERIAL PRIMARY KEY,
    first_name   varchar(100) not null,
    last_name    varchar(100) not null,
    middle_name  varchar(100),
    email       varchar(45)  not null,
    phone       varchar(15),
    password    varchar(150) not null,
    enabled     boolean default true,
    created_by   integer      not null,
    created_at   timestamp    not null,
    modified_by  integer,
    modified_at  timestamp
    );

create table if not exists role
(
    id SERIAL PRIMARY KEY,
    name varchar(45) not null
    );

create table if not exists users_role
(
    user_id integer not null references users (id) on update cascade on delete cascade,
    role_id integer not null references role (id) on update cascade on delete cascade
    );

CREATE TABLE IF NOT EXISTS refresh_token
(
    token       text  not null primary key,
    created_at  timestamp without time zone       NOT NULL,
    expiry_date timestamp without time zone       NOT NULL
);

CREATE TABLE IF NOT EXISTS access_token
(
    token         text NOT NULL primary key,
    refresh_token_id text REFERENCES refresh_token (token),
    created_at    timestamp without time zone       NOT NULL,
    expiry_date   timestamp without time zone       NOT NULL
);
