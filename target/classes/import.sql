ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_seq');
ALTER TABLE credentials ALTER COLUMN id SET DEFAULT nextval('credentials_seq');
ALTER TABLE ingredient ALTER COLUMN id SET DEFAULT nextval('ingredient_seq');

INSERT INTO users(name, surname, email)VALUES('Admin', 'Root', 'admin@mail.com');

INSERT INTO credentials(username, password, role, user_id, enabled)VALUES('admin', '$2a$10$zXfQs9TdcOamfi6oBuKZqu8miF/LV93/CwqBlRdNWR8yRdsd14I1i', 'ADMIN', 1, true);
