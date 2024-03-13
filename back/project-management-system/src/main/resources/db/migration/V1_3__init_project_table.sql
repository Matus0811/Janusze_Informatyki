CREATE TABLE project
(
    project_id  SERIAL PRIMARY KEY       NOT NULL,
    name        VARCHAR(64) UNIQUE       NOT NULL,
    description VARCHAR(255),
    user_id     INT                      NOT NULL,
    status      INT                      NOT NULL,
    start_date  TIMESTAMP WITH TIME ZONE NOT NULL,
    end_date    TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_project_user
        FOREIGN KEY (user_id)
            REFERENCES user (user_id)
);