CREATE TABLE comment
(
    comment_id SERIAL PRIMARY KEY NOT NULL,
    text       TEXT               NOT NULL,
    date       TIMESTAMP WITH TIME ZONE,
    user_id    INT                NOT NULL,
    task_id    INT                NOT NULL,
    CONSTRAINT fk_comment_user
        FOREIGN KEY (user_id)
            REFERENCES user (user_id),
    CONSTRAINT fk_comment_task
        FOREIGN KEY (task_id)
            REFERENCES task (task_id)
);