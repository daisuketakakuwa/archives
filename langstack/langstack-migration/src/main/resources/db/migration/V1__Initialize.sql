CREATE TABLE genre (
    id CHAR(22) PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE card (
    id CHAR(22) PRIMARY KEY,
    title VARCHAR,
    content VARCHAR,
    image_url VARCHAR,
    post_date DATE,
    genre_id CHAR(22) NOT NULL REFERENCES genre
);

