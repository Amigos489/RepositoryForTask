--changeset balabol:11

CREATE TABLE IF NOT EXISTS users_roles (

    user_id INT REFERENCES users(userId),
    role_id INT REFERENCES roles(role_id),
    PRIMARY KEY (user_id, role_id)
);