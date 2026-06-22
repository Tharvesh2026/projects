use authentication;

INSERT INTO permissions(permission_key, description) VALUES
('USER_READ', 'View users'),
('USER_CREATE', 'Create new users'),
('USER_UPDATE', 'Update user details'),
('USER_DELETE', 'Delete users'),

('ROLE_READ', 'View roles'),
('ROLE_UPDATE', 'Manage roles'),

('PROFILE_READ', 'View profile'),
('PROFILE_UPDATE', 'Update profile'),

('LOG_VIEW', 'View application logs'),

('SETTINGS_UPDATE', 'Update application settings');


INSERT INTO roles(role_name) VALUES
('USER'),
('ADMIN'),
('SYS_ADMIN'),
('INSPECTOR');

select * from users;
SELECT * FROM roles;
SELECT * FROM permissions;