create table participants
(
    user_id int references users(id) not null,
    post_id int references posts(id) not null
)
