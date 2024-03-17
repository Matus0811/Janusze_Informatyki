CREATE TABLE user_project_role
(
    user_project_role_id SERIAL PRIMARY KEY NOT NULL,
    user_id              INT                NOT NULL,
    role_id              INT                NOT NULL,
    project_id           uuid               NOT NULL,
    CONSTRAINT fk_user_project_role_user
        FOREIGN KEY (user_id)
            REFERENCES user_table (user_id),
    CONSTRAINT fk_user_project_role_role
        FOREIGN KEY (role_id)
            REFERENCES role (role_id),
    CONSTRAINT fk_user_project_role_project
        FOREIGN KEY (project_id)
            REFERENCES project (project_id)
);