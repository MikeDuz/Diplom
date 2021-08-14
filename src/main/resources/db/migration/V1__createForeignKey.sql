alter table posts
    add foreign key (user_id) references users (id),
add foreign key (moderator_id) references users (id);
alter table posts_votes
    add foreign key (post_id) references posts (id),
add foreign key (user_id) references users (id);
alter table tag2post
    add foreign key (post_id) references posts (id),
add foreign key (tag_id) references tags (id);
alter table posts_comments
    add foreign key (parent_id) references posts_comments (id),
add foreign key (post_id) references posts (id),
add foreign key (user_id) references users (id);

