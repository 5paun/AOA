insert into users (name, email, password)
values ('John Doe', 'johndoe@gmail.com', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W'),
       ('Denis Shalashov', 'Shalashov-D-S@tut.by', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W'),
       ('Mike Smith', 'mikesmith@yahoo.com', '$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m');

insert into symptoms (title)
values ('Headache'),
       ('Fatigue'),
       ('Insomnia');
--
insert into users_symptoms (symptom_id, user_id)
values (1, 2),
       (2, 2),
       (1, 1),
       (1, 3);

insert into users_roles (user_id, role)
values (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER'),
       (2, 'ROLE_ADMIN'),
       (3, 'ROLE_DOCTOR');
