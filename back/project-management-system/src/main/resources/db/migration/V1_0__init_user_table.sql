CREATE TABLE user
(
    user_id   SERIAL PRIMARY KEY NOT NULL,
    user_name VARCHAR(128)    NOT NULL UNIQUE,
    password  VARCHAR(255)    NOT NULL,
    name      VARCHAR(64)     NOT NULL,
    surname   VARCHAR(64)     NOT NULL,
    email     VARCHAR(64)     NOT NULL UNIQUE,
    phone     VARCHAR(64)     NOT NULL UNIQUE
);