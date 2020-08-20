create database playground_tournament;
create table tournament (id serial not null primary key, name varchar(255) not null);
create table team (id serial not null primary key, name varchar(255) not null);
insert into tournament (name) values ('Turkish Super League'),('UEFA Champions League');
