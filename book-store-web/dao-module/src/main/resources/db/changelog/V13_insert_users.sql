--changeset balabol:13

INSERT INTO users(login, password, enabled)
VALUES ('admin', '$2a$10$W797rMjXhOzAAgoZM9pZtOmu.RGpd46al.PJRZcL0wCufYnjth2Je', true),
('user', '$2a$10$0rZMpbnqv6JADuIluY1meexaJ6yinSo2D0YGt9sg182dz.PXZ7JF2', true);
