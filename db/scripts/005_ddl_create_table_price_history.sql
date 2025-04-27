create table price_history
(
    id      serial primary key,
    before  bigint                    not null,
    after   bigint                    not null,
    created timestamp with time zone default now(),
    post_id int references posts (id) not null
);
