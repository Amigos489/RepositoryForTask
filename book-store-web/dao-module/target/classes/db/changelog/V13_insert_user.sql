--changeset balabol:13

-- пользователи
INSERT INTO users(login, password, enabled)
VALUES
    ('admin', '$2a$10$kW5aVjB8s4tR1Z9e0D4dEeXzNf1xBvP4v2xqJdLtKkVq8kXHpH6tW', true),
    ('user', '$2a$10$Vq5P9nE7z3yF1S9bQ1yJv.0E5k2D3xYh6/4k9rJjH1cZ5tT7j1bE6', true);

-- связываем роли
INSERT INTO users_roles(user_id, role_id) VALUES (1, 1); -- admin → ROLE_ADMIN
INSERT INTO users_roles(user_id, role_id) VALUES (2, 2); -- user → ROLE_USER