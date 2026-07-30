DROP SEQUENCE IF EXISTS encounter_id_seq;
DROP TABLE IF EXISTS counters;

CREATE SEQUENCE IF NOT EXISTS event_count_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS event_count(
  id bigint not null default nextval('event_count_id_seq') primary key,
  event_id bigint not null references events(id) on delete cascade,
  count bigint not null default 0,

  constraint uq_events_count_event unique (event_id)
);
