CREATE TABLE user_role
(
    user_role_id SERIAL PRIMARY KEY NOT NULL,
    user_id      INT                NOT NULL,
    role_id      INT                NOT NULL,
    CONSTRAINT fk_user_role_user
        FOREIGN KEY (user_id)
            REFERENCES user (user_id),
    CONSTRAINT fk_user_role_role
        FOREIGN KEY (role_id)
            REFERENCES role (role_id)
);