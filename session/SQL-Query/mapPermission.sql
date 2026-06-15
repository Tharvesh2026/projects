use authentication;
ALTER TABLE users ADD COLUMN role_id INT;

UPDATE users u
JOIN roles r
ON u.role = r.role_name
SET u.role_id = r.id
WHERE u.id > 0;

SELECT
    u.id,
    u.username,
    u.role,
    u.role_id,
    r.role_name
FROM users u
LEFT JOIN roles r
ON u.role_id = r.id;

INSERT INTO permissions(permission_key, description) VALUES ('USER_PASSWORD_RESET', 'Reset user password');
INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.role_name = 'SYS_ADMIN'
AND p.permission_key = 'USER_PASSWORD_RESET';


SELECT COUNT(*)
FROM users
WHERE role_id IS NULL;

ALTER TABLE users
ADD CONSTRAINT fk_user_role
FOREIGN KEY (role_id)
REFERENCES roles(id);
