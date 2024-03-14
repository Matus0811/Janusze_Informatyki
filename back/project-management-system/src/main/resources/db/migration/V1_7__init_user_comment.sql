CREATE TABLE user_comment
(
    user_comment_id SERIAL PRIMARY KEY NOT NULL,
    user_id         INT                NOT NULL,
    comment_id      INT                NOT NULL,
    CONSTRAINT fk_user_comment_user
        FOREIGN KEY (user_id)
            REFERENCES user_table (user_id),
    CONSTRAINT fk_user_comment_comment
        FOREIGN KEY (comment_id)
            REFERENCES comment_table (comment_id)
);