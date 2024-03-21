CREATE TABLE task
(
    task_id     SERIAL PRIMARY KEY       NOT NULL,
    task_code   VARCHAR(255) UNIQUE      NOT NULL,
    name        VARCHAR(128)             NOT NULL,
    description TEXT                     NOT NULL,
    status      VARCHAR(32)              NOT NULL,
    priority    VARCHAR(32)              NOT NULL,
    start_date  TIMESTAMP WITH TIME ZONE NOT NULL,
    finish_date TIMESTAMP WITH TIME ZONE,
    project_id  uuid                     NOT NULL,
    CONSTRAINT fk_task_project
        FOREIGN KEY (project_id)
            REFERENCES project (project_id)
);