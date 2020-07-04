create database playground_team;
create table team (id serial not null primary key, name varchar(255) not null);
insert into team (name) values ('Galatasaray'),('Fenerbahce'),('Besiktas'),('Trabzonspor');
