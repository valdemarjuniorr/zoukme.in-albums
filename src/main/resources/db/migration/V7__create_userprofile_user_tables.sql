create sequence if not exists users_id_seq start 1 increment 1;
create table if not exists users (
    id int primary key default nextval('users_id_seq'),
    email varchar(255) not null unique,
    password varchar(255) not null,
    enabled boolean not null default true
);

create sequence if not exists user_profile_id_seq start 1 increment 1;
create table if not exists users_profile (
    id int primary key default nextval('user_profile_id_seq'),
    user_id int not null references users(id) on delete cascade,
    full_name varchar(255) not null,
    phone varchar(50),
    instagram varchar(255)
);
