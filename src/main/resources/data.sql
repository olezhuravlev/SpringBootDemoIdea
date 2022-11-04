INSERT INTO USERS (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES ('user@gmail.com', 'User_First', 'User_Last', '{noop}user'),
       ('admin@gmail.com', 'Admin_First', 'Admin_Last', '{noop}admin');

INSERT INTO USER_ROLE(USER_ID, ROLE)
VALUES (1, 'USER'),
       (2, 'ADMIN'),
       (2, 'USER');
