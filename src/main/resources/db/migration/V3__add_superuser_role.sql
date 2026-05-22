INSERT INTO role_ (id, actual, created_at, name) VALUES
    (1, TRUE, NOW(), 'SUPERUSER') RETURNING id;

INSERT INTO role_permission (role_, permission_) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4);

