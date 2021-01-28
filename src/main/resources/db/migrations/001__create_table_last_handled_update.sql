-- liquibase formatted sql
-- changeset am:1
create table if not exists last_handled_update (
    id bigint not null auto_increment primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    update_id int not null
);