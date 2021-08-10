create table captcha_codes (
id integer primary key auto_increment not null,
code varchar(255) not null,
secret_code varchar(255) not null,
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
is_moderator bit not null,
reg_time datetime not null,
name varchar(255) not null,
email varchar(255) not null,
password varchar(255) not null,
code varchar(255),
photo varchar (255)
) engine=InnoDB;
create table posts (
id integer primary key auto_increment not null,
is_active bit not null,
moderation_status enum('NEW','ACCEPTED','DECLINED') not null,
moderator_id integer, text text not null,
time timestamp not null, title varchar(255) not null,
user_id integer not null,
view_count integer not null
) engine=InnoDB;
create table posts_votes (
id integer primary key auto_increment not null,
post_id integer not null,
time timestamp not null,
user_id integer not null,
value integer not null
) engine=InnoDB;
create table tags (
id integer not null auto_increment primary key,
name varchar(255) not null unique
) engine=InnoDB;
create table tag2post (
id integer primary key auto_increment not null,
post_id integer not null,
tag_id integer not null
) engine=InnoDB;
create table posts_comments (
id integer primary key auto_increment not null,
parent_id integer,
post_id integer not null,
text varchar(255) not null,
time timestamp not null,
user_id integer not null
) engine=InnoDB;