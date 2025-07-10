insert into client (name, email, password)
values ('John Doe', 'johndoe@gmail.com', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W'),
       ('Denis Shalashov', 'Shalashov-D-S@tut.by', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W'),
       ('Mike Smith', 'mikesmith@yahoo.com', '$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m'),
        ('Test User', 'test@user.com', '$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m');

insert into symptom (title)
values ('Headache'),
       ('Fatigue'),
       ('Insomnia');
--
insert into client_symptom (symptom_id, client_id)
values (1, 2),
       (2, 2),
       (1, 1),
       (1, 3);

insert into client_role (client_id, role)
values (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER'),
       (2, 'ROLE_ADMIN'),
       (3, 'ROLE_DOCTOR');

insert into analysis (id, client_id, title, total_cholesterol, white_blood_cells, lymphocytes, created_date)
values (1, 2, 'Test', 0.1, 0.1, 1, '2025-07-06T21:19:34.930Z');