DROP TABLE IF EXISTS COUNTERS;
DROP SEQUENCE IF EXISTS COUNTER_ID_SEQ;

DROP TABLE IF EXISTS ALBUMS;
DROP SEQUENCE IF EXISTS ALBUM_ID_SEQ;

DROP TABLE IF EXISTS PHOTOS;
DROP SEQUENCE IF EXISTS PHOTO_ID_SEQ;

DROP TABLE IF EXISTS SOCIAL_MEDIA;
DROP SEQUENCE IF EXISTS SOCIAL_MEDIA_ID_SEQ;

DROP TABLE IF EXISTS EVENT_PHOTOS;
DROP SEQUENCE IF EXISTS EVENT_PHOTOS_SEQ;

DROP TABLE IF EXISTS SUB_EVENTS;
DROP SEQUENCE IF EXISTS SUB_EVENT_SEQ;

DROP TABLE IF EXISTS EVENTS;
DROP SEQUENCE IF EXISTS EVENTS_ID_SEQ;

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

CREATE SEQUENCE IF NOT EXISTS EVENTS_ID_SEQ start 4 increment 1;
CREATE TABLE IF NOT EXISTS EVENTS
(
  id          int primary key default nextval('EVENTS_ID_SEQ'),
  title       varchar(50)  not null,
  description text         not null,
  details     text,
  date        date         not null,
  location    varchar(50)  not null,
  cover_url   varchar(255) not null,
  event_url   varchar(255) not null
);

CREATE SEQUENCE IF NOT EXISTS SOCIAL_MEDIA_ID_SEQ start 4 increment 1;
CREATE TABLE IF NOT EXISTS SOCIAL_MEDIA
(
  id           int primary key default nextval('SOCIAL_MEDIA_ID_SEQ'),
  event_id     int REFERENCES EVENTS not null,
  instagram    varchar(50)           not null,
  phone_number varchar(11)           not null

);

CREATE SEQUENCE IF NOT EXISTS PHOTO_ID_SEQ start 7 increment 1;
CREATE TABLE IF NOT EXISTS PHOTOS
(
  id         int primary key default nextval('PHOTO_ID_SEQ'),
  event_id   int REFERENCES EVENTS not null,
  image_path varchar(255)          not null

);

CREATE SEQUENCE IF NOT EXISTS SUB_EVENT_SEQ start 10 increment 1;
CREATE TABLE IF NOT EXISTS SUB_EVENTS
(
  id       int primary key default nextval('SUB_EVENT_SEQ'),
  event_id int REFERENCES EVENTS not null,
  name     varchar(50)           not null
);

CREATE SEQUENCE IF NOT EXISTS EVENT_PHOTOS_SEQ start 10 increment 1;
CREATE TABLE IF NOT EXISTS EVENT_PHOTOS
(
  id           int primary key default nextval('EVENT_PHOTOS_SEQ'),
  sub_event_id int REFERENCES SUB_EVENTS not null,
  image_path   varchar(255)              not null
);
