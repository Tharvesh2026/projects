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


SELECT COUNT(*)
FROM users
WHERE role_id IS NULL;

ALTER TABLE users
ADD CONSTRAINT fk_user_role
FOREIGN KEY (role_id)
REFERENCES roles(id);
