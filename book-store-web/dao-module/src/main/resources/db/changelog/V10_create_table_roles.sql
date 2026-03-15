--changeset balabol:10

CREATE TABLE IF NOT EXISTS roles (

    roleId SERIAL PRIMARY KEY,
    name VARCHAR(30) UNIQUE NOT NULL
);