create sequence if not exists email_verification_token_id_seq start 1 increment 1;
create table email_verification_tokens (
    id int primary key default nextval('email_verification_token_id_seq'),
    token varchar(255) not null unique,
    user_id int not null references users(id) on delete cascade,
    expiry_date timestamp not null
);
