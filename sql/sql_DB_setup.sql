CREATE SCHEMA IF NOT EXISTS APP;

DROP TABLE IF EXISTS APP.users;
DROP TABLE IF EXISTS APP.issues;
DROP TABLE IF EXISTS APP.issue_comments;
DROP TABLE IF EXISTS APP.departments;
DROP TABLE IF EXISTS APP.skills;
DROP TABLE IF EXISTS APP.tech_skills;

CREATE TABLE APP.users (
    id              SERIAL,
    username        VARCHAR(20),
    password        VARCHAR(30),
    first_name      VARCHAR(20),
    last_name       VARCHAR(30),
    department_id   INT,
    staff_type      INT,
    PRIMARY KEY (id)
);

CREATE TABLE APP.issues (
    id              SERIAL,
    reporter_id     INT,
    title           VARCHAR(50),
    status          VARCHAR(10),
    description     TEXT,
    stickied        BOOLEAN,
    time_submitted  TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (reporter_id) REFERENCES APP.users (id)
);

CREATE TABLE APP.issue_comments (
    id              SERIAL,
    issue_id        INT,
    poster_id       INT,
    content         TEXT,
    time_submitted  TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (issue_id) REFERENCES APP.issues (id),
    FOREIGN KEY (poster_id) REFERENCES APP.users (id)
);

CREATE TABLE APP.departments (
    id              SERIAL,
    department_name VARCHAR(40),
    location        VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE APP.skills (
    id  SERIAL,
    skill_name VARCHAR(30),
    PRIMARY KEY (id)
);

CREATE TABLE APP.tech_skills (
    tech_id INT,
    skill_id INT,
    skill_level INT,
    PRIMARY KEY (tech_id, skill_id),
    FOREIGN KEY (tech_id) REFERENCES APP.users (id),
    FOREIGN KEY (skill_id) REFERENCES APP.skills (id)
);

-- Users table test data
INSERT INTO APP.users (username, password, first_name, last_name, staff_type)
VALUES ('jsmith', 'tardis', 'John', 'Smith', 2);
INSERT INTO APP.users (username, password, first_name, last_name, staff_type)
VALUES ('hsaxon', 'thedrums', 'Harold', 'Saxon', 2);
INSERT INTO APP.users (username, password, first_name, last_name, department_id, staff_type)
VALUES ('dnoble', 'doctordonna', 'Donna', 'Noble', 1, 1);
INSERT INTO APP.users (username, password, first_name, last_name, department_id, staff_type)
VALUES ('jharkness', 'hellothere', 'Jack', 'Harkness', 1, 1);
INSERT INTO APP.users (username, password, first_name, last_name, department_id, staff_type)
VALUES ('msmith', 'mickeymouse', 'Mickey', 'Smith', 2, 1);
INSERT INTO APP.users (username, password, first_name, last_name, department_id, staff_type)
VALUES ('rtyler', 'badwolf', 'Rose', 'Tyler', 2, 1);

-- Departments table test data
INSERT INTO APP.departments (department_name, location)
VALUES ('Human Resources', 'Floor 15, Canary Wharf, London');
INSERT INTO APP.departments (department_name, location)
VALUES ('Research and Development', 'Floor 18, Canary Wharf, London');

-- Issues table test data
INSERT INTO APP.issues (reporter_id, title, status, description, stickied, time_submitted)
VALUES (4, 'Laptop Frozen', 'open', 'My laptop has frozen and doesn''t work anymore', false, '2019-09-10 12:42:10');

-- Issue_comments table test data
INSERT INTO APP.issue_comments (issue_id, poster_id, content, time_submitted)
VALUES (1, 1, 'Have you tried turning it off and on again?', '2019-09-12 14:01:16');

-- Skills table test data
INSERT INTO APP.skills (skill_name)
VALUES ('PC Support');
INSERT INTO APP.skills (skill_name)
VALUES ('Mac Support');
INSERT INTO APP.skills (skill_name)
VALUES ('Android Support');
INSERT INTO APP.skills (skill_name)
VALUES ('Server Support');

-- Tech_skills table test data
INSERT INTO APP.tech_skills (tech_id, skill_id, skill_level)
VALUES (1, 3, 3);
INSERT INTO APP.tech_skills (tech_id, skill_id, skill_level)
VALUES (1, 1, 2);
INSERT INTO APP.tech_skills (tech_id, skill_id, skill_level)
VALUES (2, 2, 2);
