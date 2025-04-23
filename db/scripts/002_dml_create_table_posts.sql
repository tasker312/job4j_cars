create table posts
(
    id          serial primary key,
    description varchar                   not null,
    created     timestamp                 not null,
    user_id     int references users (id) not null

)
