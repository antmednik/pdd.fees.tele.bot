-- liquibase formatted sql
-- changeset am:1
create table if not exists handled_updates (
    id int not null primary key
);