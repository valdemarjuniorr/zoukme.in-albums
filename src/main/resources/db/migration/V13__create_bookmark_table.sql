create sequence if not exists bookmark_id_seq start with 1 increment by 1;

create table if not exists photo_bookmarks (
    id bigint not null default nextval('bookmark_id_seq') primary key,
    event_photo_id bigint not null references event_photos(id) on delete cascade,
    user_id bigint not null references users(id) on delete cascade,
    created_at timestamp not null default now(),

    constraint uq_photo_bookmarks_photo_user unique (event_photo_id, user_id)
);
