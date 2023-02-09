
DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name varchar(250) NOT NULL,
    email varchar(250) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY(id),
    CONSTRAINT uniq_user_email UNIQUE(email)
);

DROP TABLE IF EXISTS requests CASCADE;
CREATE TABLE IF NOT EXISTS requests(
        id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
        description varchar(250),
        requester_id BIGINT,
        creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
        FOREIGN KEY (requester_id) REFERENCES users(id),
        CONSTRAINT pk_request PRIMARY KEY(id)
);

DROP TABLE IF EXISTS items CASCADE;
CREATE TABLE IF NOT EXISTS items (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name varchar(500) NOT NULL,
    description varchar(500) NOT NULL,
    is_available BOOLEAN NOT NULL,
    owner_id BIGINT,
    request_id BIGINT,
    FOREIGN KEY(owner_id) REFERENCES users(id),
    FOREIGN KEY (request_id) REFERENCES requests(id),
    CONSTRAINT pk_item PRIMARY KEY(id)
);

DROP TABLE IF EXISTS bookings CASCADE;
CREATE TABLE IF NOT EXISTS bookings(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    item_id BIGINT,
    booker_id BIGINT,
    status varchar,
    FOREIGN KEY(item_id) REFERENCES items(id),
    FOREIGN KEY(booker_id) REFERENCES users(id),
    CONSTRAINT pk_booking PRIMARY KEY (id)
);



DROP TABLE IF EXISTS comments CASCADE;
CREATE TABLE IF NOT EXISTS comments(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text varchar,
    item_id BIGINT,
    author_id BIGINT,
    creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    FOREIGN KEY (item_id) REFERENCES items(id),
    FOREIGN KEY (author_id) REFERENCES users(id),
    CONSTRAINT pk_comments PRIMARY KEY (id)
)