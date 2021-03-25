--create database playground_tournament;
create table tournament (
id serial not null primary key,
name varchar(255) not null,
created_at timestamp not null,
updated_at timestamp
);
create table team (
id serial not null primary key,
name varchar(255) not null,
created_at timestamp not null,
updated_at timestamp
);
insert into tournament (name, created_at) values ('Turkish Super League', now()),('UEFA Champions League', now());
