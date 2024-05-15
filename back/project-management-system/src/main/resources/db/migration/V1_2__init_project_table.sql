CREATE TABLE project
(
    project_id  uuid PRIMARY KEY         NOT NULL,
    name        VARCHAR(64)              NOT NULL,
    description VARCHAR(255),
    status      VARCHAR(32)              NOT NULL,
    start_date  TIMESTAMP WITH TIME ZONE NOT NULL,
    finish_date TIMESTAMP WITH TIME ZONE
);