INSERT INTO users (username, password)
VALUES ('admin', '$2a$12$j5AutaN2lxrSIQnMAoaXjemx2BNxp8D6w/DGyttUq0q1xGMFKv7t2');

INSERT INTO user_roles (user_id, roles) VALUES (1, 'ROLE_ADMIN');
INSERT INTO user_roles (user_id, roles) VALUES (1, 'ROLE_USER');