create table if not exists client
(
    id bigserial primary key,
    name varchar(255) not null,
    email varchar(255) not null unique,
    password varchar(255) not null
);

create table if not exists analysis
(
    id bigserial primary key,
    client_id bigserial not null,
    title varchar(255) not null,
    total_cholesterol NUMERIC(3, 1),
    white_blood_cells NUMERIC(2, 1),
    lymphocytes INTEGER,
    created_date timestamp not null,
    FOREIGN KEY (client_id) REFERENCES client (id) on DELETE CASCADE
);

create table if not exists symptom
(
    id bigserial primary key,
    title varchar(50) not null,
    description varchar(500),
    recommendation varchar(1000)
);

create table if not exists client_symptom
(
    client_id bigint not null,
    symptom_id bigint not null,
    primary key (client_id, symptom_id),
    constraint fk_client_symptom_client foreign key (client_id) references client (id) on delete cascade on update no action,
    constraint fk_client_symptom_symptom foreign key (symptom_id) references symptom (id) on delete cascade on update no action
);

create table if not exists client_role
(
    client_id bigint not null,
    role varchar(255) not null,
    primary key (client_id, role),
    constraint fk_client_role_client foreign key (client_id) references client (id) on delete cascade on update no action
);

create table if not exists analysis_image
(
    analysis_id bigint not null,
    image varchar(255) not null,
    constraint fk_analysis_image_analysis foreign key (analysis_id) references analysis (id) on delete cascade on update no action
);

create table if not exists symptom_image
(
    symptom_id bigint not null,
    image varchar(255) not null,
    constraint fk_symptom_image_symptom foreign key (symptom_id) references symptom (id) on delete cascade on update no action
);