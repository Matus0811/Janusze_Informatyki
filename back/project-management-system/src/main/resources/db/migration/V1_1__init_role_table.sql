CREATE TABLE role
(
    role_id SERIAL PRIMARY KEY NOT NULL,
    name    VARCHAR(64)        NOT NULL,
    UNIQUE (name)
);

-- INSERT INTO role (role_id, name)
-- VALUES (1, 'PROJECT_MANAGER'),
--        (2, 'PROJECT_MEMBER'),
--        (3, 'USER');