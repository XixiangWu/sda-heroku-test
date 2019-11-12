CREATE TABLE APP.appointments (
    id              SERIAL,
    reporter_id     INT,
    issues_id       INT,
    status          VARCHAR(10),
    description     TEXT,
    stickied        BOOLEAN,
    time_submitted  TIMESTAMP,
    time_estimated  TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (reporter_id) REFERENCES APP.users (id),
    FOREIGN KEY (issues_id) REFERENCES APP.issues (id)
);


CREATE TABLE APP.appointment_comments (
    id              SERIAL,
    appointment_id  INT,
    poster_id       INT,
    content         TEXT,
    time_submitted  TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (appointment_id) REFERENCES APP.appointments (id),
    FOREIGN KEY (poster_id) REFERENCES APP.users (id)
);

INSERT INTO app.appointments(id, reporter_id, issues_id, status, description, stickied, time_submitted, time_estimated)
VALUES (5, 4, 1, 'ACCEPTED', 'on site visit to change mouse', false, '2019-10-20 12:42:10', '2019-10-20 10:00:00');

INSERT INTO app.appointments(id, reporter_id, issues_id, status, description, stickied, time_submitted)
VALUES (6, 4, 1, 'WAITING', 'change monitor', false, '2019-10-20 12:42:10');

INSERT INTO app.appointments(id, reporter_id, issues_id, status, description, stickied, time_submitted, time_estimated)
VALUES (7, 4, 1, 'RESOLVED', 'change a new laptop', false, '2019-10-20 12:42:10', '2019-10-20 10:00:00');

INSERT INTO app.appointment_comments(appointment_id, poster_id, content, time_submitted)
VALUES (5, 4, 'its still not working!!!', '2019-10-20 15:42:10');

INSERT INTO app.appointment_comments(appointment_id, poster_id, content, time_submitted)
VALUES (5, 1, 'Have you tried to change the cd drive? it may work!', '2019-10-20 15:42:10');

INSERT INTO app.appointment_comments(appointment_id, poster_id, content, time_submitted)
VALUES (5, 4, 'Ive changed the cd drive, still doesnt work!', '2019-10-20 15:42:10');