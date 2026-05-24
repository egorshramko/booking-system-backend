ALTER TABLE permission_ DROP CONSTRAINT permission__object_check;
ALTER TABLE permission_ ADD CONSTRAINT permission__object_check CHECK ((object IN ('USER', 'ROLE', 'PERMISSION')));
INSERT INTO permission_ (id, actual, created_at, object, type) VALUES
    (5, TRUE, NOW(), 'ROLE', 'CREATE'),
    (6, TRUE, NOW(), 'ROLE', 'READ'),
    (7, TRUE, NOW(), 'ROLE', 'UPDATE'),
    (8, TRUE, NOW(), 'ROLE', 'DELETE');