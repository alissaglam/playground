create database playground_team;
create table team (id serial not null primary key, name varchar(255) not null);
insert into team (name) values ('Galatasaray'),('Fenerbahce'),('Besiktas'),('Trabzonspor');

CREATE TABLE event_produce (
    id uuid NOT NULL,
    event text NOT NULL,
    payload text NOT NULL,
    created_at time without time zone NOT NULL
);

CREATE TABLE event_consume (
    id uuid NOT NULL,
    created_at time without time zone NOT NULL,
    fallback boolean DEFAULT false NOT NULL
);


CREATE TABLE fallback_event_consume_log (
    id integer NOT NULL,
    lastly_consumed_event_id uuid NOT NULL,
    lastly_consumed_event_produce_time timestamp without time zone NOT NULL
);

CREATE TABLE shedlock (
    name character varying(64) NOT NULL,
    lock_until timestamp(3) without time zone,
    locked_at timestamp(3) without time zone,
    locked_by character varying(255)
);

CREATE TABLE team_log (
    id integer NOT NULL,
    log text NOT NULL
);
