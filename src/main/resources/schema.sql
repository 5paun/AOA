create schema if not exists analysislist;

create table if not exists users
(
    id bigserial primary key,
    name varchar(255) not null,
    email varchar(255) not null unique,
    password varchar(255) not null
);

create table if not exists analysis
(
    id bigserial primary key,
    title varchar(255) not null,
    created_date timestamp not null
);

-- create table if not exists users_analyses
-- (
--     user_id bigint not null,
--     analysis_id bigint not null,
--     primary key (user_id, analysis_id),
--     constraint fk_users_analyses_users foreign key (user_id) references users (id) on delete cascade on update no action,
--     constraint fk_users_analyses_analyses foreign key (analysis_id) references analyses (id) on delete cascade on update no action
-- )

create table if not exists users_role
(
    user_id bigint not null,
    role varchar(255) not null,
    primary key (user_id, role),
    constraint fk_users_roles_users foreign key (user_id) references users (id) on delete cascade on update no action
)