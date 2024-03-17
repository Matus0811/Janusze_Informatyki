CREATE TABLE user_table
(
    user_id   SERIAL PRIMARY KEY  NOT NULL,
    user_name VARCHAR(128) UNIQUE NOT NULL,
    password  VARCHAR(255)        NOT NULL,
    name      VARCHAR(64)         NOT NULL,
    surname   VARCHAR(64)         NOT NULL,
    gender    VARCHAR(64),
    email     VARCHAR(64) UNIQUE  NOT NULL,
    phone     VARCHAR(64) UNIQUE  NOT NULL
);