DROP TABLE books;

CREATE TABLE books(
    isbn INT,
    title VARCHAR(50),
    PRIMARY KEY (isbn)
);

INSERT INTO books VALUES (1001, 'first book');


SELECT * FROM books;