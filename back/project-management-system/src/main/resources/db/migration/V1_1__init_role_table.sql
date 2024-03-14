CREATE TABLE role
(
    role_id SERIAL PRIMARY KEY NOT NULL,
    name    VARCHAR(64)        NOT NULL,
    UNIQUE (name)
);