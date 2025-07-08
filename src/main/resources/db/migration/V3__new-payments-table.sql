CREATE SEQUENCE IF NOT EXISTS PAYMENT_ID_SEQ start 1 increment 1;
CREATE TABLE IF NOT EXISTS PAYMENTS
(
    id             int primary key          default nextval('PAYMENT_ID_SEQ'),
    package_id     int REFERENCES PACKAGES                not null,
    full_name      varchar(150)                           not null,
    document       varchar(11)                            not null,
    email          varchar(100)                           not null,
    phone          varchar(20)                            not null,
    amount         decimal(10, 2)                         not null,
    status         varchar(20)                            not null,
    reference_id   uuid                                   not null,
    transaction_id varchar(50)                            not null,
    payment_date   timestamp with time zone default now() not null
);