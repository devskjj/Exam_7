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
        '12345',
        true,
        (SELECT id FROM roles WHERE role = 'ADMIN'));

INSERT INTO users (username, password, phone_number, enabled, role_id)
VALUES ('user1',
        '$2a$12$uXfcpGKAuXmpEMtY80KnRuuEUmAqHdII7bI/JOp2Soym1EMOnm1ie',
        '996 (500) 11-11-11',
        true,
        (SELECT id FROM roles WHERE role = 'USER'));

INSERT INTO users (username, password, phone_number, enabled, role_id)
VALUES ('user2',
        '$2a$12$uXfcpGKAuXmpEMtY80KnRuuEUmAqHdII7bI/JOp2Soym1EMOnm1ie',
        '996 (501) 22-22-22',
        true,
        (SELECT id FROM roles WHERE role = 'USER'));

INSERT INTO users (username, password, phone_number, enabled, role_id)
VALUES ('user3',
        '$2a$12$uXfcpGKAuXmpEMtY80KnRuuEUmAqHdII7bI/JOp2Soym1EMOnm1ie',
        '996 (502) 33-33-33',
        true,
        (SELECT id FROM roles WHERE role = 'USER'));

INSERT INTO accounts (user_id, account_number, currency_id, balance, created_at)
VALUES (1, 'ACC-1001-USD', 1, 1000.00, '2026-01-10 10:00:00'),
       (2, 'ACC-1002-EUR', 2, 500.00, '2026-01-10 10:05:00'),
       (3, 'ACC-1003-USD', 1, 750.00, '2026-01-10 10:10:00'),
       (4, 'ACC-1004-EUR', 2, 1200.00, '2026-01-10 10:15:00');

INSERT INTO transactions
(from_account_id, to_account_id, amount, currency_id, status, approved, approved_by, transaction_type, created_at,
 updated_at)
VALUES (NULL, 1, 1000.00, 1, 'COMPLETED', TRUE, 1, 'DEPOSIT', '2026-01-10 10:00:00', '2026-01-10 10:00:00'),
       (1, 2, 200.00, 1, 'COMPLETED', TRUE, 1, 'TRANSFER', '2026-01-10 11:00:00', '2026-01-10 11:00:00'),
       (NULL, 3, 500.00, 1, 'COMPLETED', TRUE, 3, 'DEPOSIT', '2026-01-10 12:00:00', '2026-01-10 12:00:00'),
       (4, 2, 300.00, 2, 'COMPLETED', TRUE, 4, 'TRANSFER', '2026-01-10 13:00:00', '2026-01-10 13:00:00');


INSERT INTO transaction_rollbacks (transaction_id, rolled_back_by, created_at)
VALUES (2, 1, '2026-01-10 14:00:00'),
       (4, 4, '2026-01-10 15:00:00');



