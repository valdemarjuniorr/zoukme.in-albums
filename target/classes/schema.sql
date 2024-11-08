DROP TABLE COUNTERS;
DROP TABLE ALBUMS;
DROP SEQUENCE ALBUM_ID_SEQ;
DROP SEQUENCE COUNTER_ID_SEQ;

CREATE SEQUENCE IF NOT EXISTS ALBUM_ID_SEQ start 13 increment 1;

CREATE TABLE IF NOT EXISTS ALBUMS
(
    id         int primary key default nextval('ALBUM_ID_SEQ'),
    title      varchar(50)  not null,
    city       varchar(50) not null,
    event_date date         not null,
    thumb_url  varchar(255) not null,
    url        varchar(255) not null
);

CREATE SEQUENCE IF NOT EXISTS COUNTER_ID_SEQ start 13 increment 1;
CREATE TABLE IF NOT EXISTS COUNTERS
(
    id       int primary key default nextval('COUNTER_ID_SEQ'),
    count    int             default 1 not null,
    album_id int REFERENCES ALBUMS     not null
);