DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS employee_history;
DROP TABLE IF EXISTS job;
DROP TABLE IF EXISTS job_history;

CREATE TABLE job (
    id IDENTITY NOT NULL PRIMARY KEY,
    name CHARACTER VARYING
);

CREATE TABLE job_history (
    id BIGINT NOT NULL,
    name CHARACTER VARYING,
    start_date DATETIME,
    end_date DATETIME
);

CREATE TABLE employee (
    id IDENTITY NOT NULL PRIMARY KEY,
    job_id BIGINT NOT NULL,
    name CHARACTER VARYING,
    FOREIGN KEY (job_id) REFERENCES job (id)
);

CREATE TABLE employee_history (
    id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    name CHARACTER VARYING,
    start_date DATETIME,
    end_date DATETIME
);

-- INSERT INTO employee (id, name) VALUES
-- (1, 'Foo');
--
-- INSERT INTO team (id, name) VALUES
-- (1, 'Team');



