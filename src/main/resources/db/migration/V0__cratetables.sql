create table captcha_codes (
id integer primary key not null,
code tinyint not null,
secret_code tinyint not null,
time timestamp not null
) engine=InnoDB;
create table global_settings (
id integer primary key auto_increment not null,
code enum('MULTIUSER_MODE','POST_PREMODERATION','STATISTICS_IN_PUBLIC') not null,
name varchar(255) not null,
value enum('YES','NO') not null
) engine=InnoDB;
create table users (
id integer primary key auto_increment not null,
is_moderator tinyint not null,
reg_time datetime not null,
name varchar(255) not null,
email varchar(255) not null,
password varchar(255) not null,
code varchar(255),
photo text
) engine=InnoDB;
create table posts (
id integer primary key auto_increment not null,
is_active bit not null,
moderation_status enum('NEW','ACCEPTED','DECLINED') not null,
moderator_id integer not null, text varchar(255) not null,
time timestamp not null, title varchar(255) not null,
user_id integer not null,
view_count integer not null,
foreign key (user_id) references users (id),
foreign key (moderator_id) references users (id)
) engine=InnoDB;
create table posts_votes (
id integer primary key auto_increment not null,
post_id integer not null,
time timestamp not null,
user_id integer not null,
value integer not null,
foreign key (post_id) references posts (id),
foreign key (user_id) references users (id)
) engine=InnoDB;
create table tags (
id integer not null auto_increment primary key,
name varchar(255) not null
) engine=InnoDB;
create table tag2post (
id integer primary key auto_increment not null,
post_id integer not null,
tag_id integer not null,
foreign key (post_id) references posts (id),
foreign key (tag_id) references tags (id)
) engine=InnoDB;
create table posts_comments (
id integer primary key auto_increment not null,
parent_id integer,
post_id integer not null,
text varchar(255) not null,
time timestamp not null,
user_id integer not null,
foreign key (parent_id) references posts_comments (id),
foreign key (post_id) references posts (id),
foreign key (user_id) references users (id)
) engine=InnoDB;