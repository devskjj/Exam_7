INSERT INTO roles (role)
VALUES ('USER'),
       ('ADMIN');

INSERT INTO currencies (code)
VALUES ('USD'),
       ('EUR'),
       ('KGS');

INSERT INTO users (username, password, phone_number, enabled, role_id)
VALUES ('12345',
        '$2a$12$uXfcpGKAuXmpEMtY80KnRuuEUmAqHdII7bI/JOp2Soym1EMOnm1ie',
        '996 (999) 99-99-99',
        true,
        (SELECT id FROM roles WHERE role = 'ADMIN'));


