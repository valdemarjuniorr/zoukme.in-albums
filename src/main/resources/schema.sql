DROP TABLE IF EXISTS COUNTERS;
DROP SEQUENCE IF EXISTS COUNTER_ID_SEQ;

DROP TABLE IF EXISTS ALBUMS;
DROP SEQUENCE IF EXISTS ALBUM_ID_SEQ;

DROP TABLE IF EXISTS EVENTS;
DROP SEQUENCE IF EXISTS EVENTS_ID_SEQ;

DROP TABLE IF EXISTS SOCIAL_MEDIA;
DROP SEQUENCE IF EXISTS SOCIAL_MEDIA_ID_SEQ;

CREATE SEQUENCE IF NOT EXISTS ALBUM_ID_SEQ start 13 increment 1;
CREATE TABLE IF NOT EXISTS ALBUMS
(
  id         int primary key default nextval('ALBUM_ID_SEQ'),
  title      varchar(50)  not null,
  city       varchar(50)  not null,
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

CREATE SEQUENCE IF NOT EXISTS SOCIAL_MEDIA_ID_SEQ start 2 increment 1;
CREATE TABLE IF NOT EXISTS SOCIAL_MEDIA
(
  id          int primary key default nextval('SOCIAL_MEDIA_ID_SEQ'),
  instagram   varchar(50) not null,
  phone_number varchar(11) not null

);

CREATE SEQUENCE IF NOT EXISTS EVENTS_ID_SEQ start 2 increment 1;
CREATE TABLE IF NOT EXISTS EVENTS
(
  id              int primary key default nextval('EVENTS_ID_SEQ'),
  title           varchar(50)                 not null,
  description     varchar(255)                not null,
  date            date                        not null,
  location        varchar(50)                 not null,
  social_media_id int REFERENCES SOCIAL_MEDIA not null
);
