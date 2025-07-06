CREATE SEQUENCE IF NOT EXISTS PACKAGE_ID_SEQ start 1 increment 1;
CREATE TABLE IF NOT EXISTS PACKAGES
(
    id          int primary key default nextval('PACKAGE_ID_SEQ'),
    event_id    int REFERENCES EVENTS not null,
    document    varchar(11)           not null,
    title       varchar(50)           not null,
    description text                  not null,
    price       decimal(10, 2)        not null
);