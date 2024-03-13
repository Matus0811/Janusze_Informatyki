CREATE TABLE user_task
(
    user_task_id SERIAL  NOT NULL,
    user_id      INT     NOT NULL,
    task_id      INT     NOT NULL,
    finished     BOOLEAN NOT NULL,
    CONSTRAINT fk_user_task_user
        FOREIGN KEY (user_id)
            REFERENCES user (user_id),
    CONSTRAINT fk_user_task_task
        FOREIGN KEY (task_id)
            REFERENCES task (task_id)
);