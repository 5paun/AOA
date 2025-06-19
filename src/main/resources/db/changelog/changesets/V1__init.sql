create table if not exists users
(
    id
    bigserial
    primary
    key,
    name
    varchar
(
    255
) not null,
    email varchar
(
    255
) not null unique,
    password varchar
(
    255
) not null
    );

create table if not exists analyses
(
    id
    bigserial
    primary
    key,
    title
    varchar
(
    255
) not null,
    total_cholesterol NUMERIC
(
    3,
    1
),
    white_blood_cells NUMERIC
(
    2,
    1
),
    lymphocytes INTEGER,
    created_date timestamp not null
    );

create table if not exists symptoms
(
    id
    bigserial
    primary
    key,
    title
    varchar
(
    50
) not null,
    description varchar
(
    500
),
    recommendation varchar
(
    1000
)
    );

create table if not exists users_symptoms
(
    user_id
    bigint
    not
    null,
    symptom_id
    bigint
    not
    null,
    primary
    key
(
    user_id,
    symptom_id
),
    constraint fk_users_symptoms_users foreign key
(
    user_id
) references users
(
    id
) on delete cascade
  on update no action,
    constraint fk_users_symptoms_symptoms foreign key
(
    symptom_id
) references symptoms
(
    id
)
  on delete cascade
  on update no action
    );

create table if not exists users_roles
(
    user_id
    bigint
    not
    null,
    role
    varchar
(
    255
) not null,
    primary key
(
    user_id,
    role
),
    constraint fk_users_roles_users foreign key
(
    user_id
) references users
(
    id
) on delete cascade
  on update no action
    );

create table if not exists analyses_images
(
    analysis_id
    bigint
    not
    null,
    image
    varchar
(
    255
) not null,
    constraint fk_analyses_images_analyses foreign key
(
    analysis_id
) references analyses
(
    id
) on delete cascade
  on update no action
    );

create table if not exists symptoms_images
(
    symptom_id
    bigint
    not
    null,
    image
    varchar
(
    255
) not null,
    constraint fk_symptoms_images_symptoms foreign key
(
    symptom_id
) references symptoms
(
    id
) on delete cascade
  on update no action
    );