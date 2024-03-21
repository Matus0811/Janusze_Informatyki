CREATE TABLE bug
(
    bug_id        SERIAL PRIMARY KEY       NOT NULL,
    serial_number VARCHAR(255) UNIQUE      NOT NULL,
    title         VARCHAR(128)             NOT NULL,
    description   TEXT                     NOT NULL,
    project_id    uuid                     NOT NULL,
    user_id       INT                      NOT NULL,
    type          VARCHAR(32)              NOT NULL,
    report_date   TIMESTAMP WITH TIME ZONE NOT NULL,
    fixed         BOOLEAN                  NOT NULL,
    CONSTRAINT fk_bug_project
        FOREIGN KEY (project_id)
            REFERENCES project (project_id),
    CONSTRAINT fk_bug_user
        FOREIGN KEY (user_id)
            REFERENCES user_table (user_id)
);