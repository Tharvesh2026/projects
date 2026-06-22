SET SQL_SAFE_UPDATES = 0;

UPDATE users u
JOIN roles r
ON u.role = r.role_name
SET u.role_id = r.id
WHERE u.id > 0;

DELETE FROM role_permissions
WHERE role_id > 0;

INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p
WHERE r.role_name = 'USER'
AND p.permission_key IN (
    'PROFILE_READ',
    'PROFILE_UPDATE'
);

INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p
WHERE r.role_name = 'ADMIN'
AND p.permission_key IN (
    'USER_READ',
    'USER_CREATE',
    'USER_UPDATE',
    'PROFILE_READ',
    'PROFILE_UPDATE',
    'ROLE_READ',
    'LOG_VIEW'
);

INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p
WHERE r.role_name = 'SYS_ADMIN';

INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p
WHERE r.role_name = 'INSPECTOR'
AND p.permission_key IN (
    'USER_READ',
    'ROLE_READ',
    'PROFILE_READ',
    'LOG_VIEW'
);

SELECT
    r.role_name,
    p.permission_key
FROM role_permissions rp
JOIN roles r
ON rp.role_id = r.id
JOIN permissions p
ON rp.permission_id = p.id
ORDER BY r.role_name, p.permission_key;

SET SQL_SAFE_UPDATES = 1;

SELECT
    u.username,
    r.role_name,
    p.permission_key
FROM users u
JOIN roles r
ON u.role_id = r.id
JOIN role_permissions rp
ON r.id = rp.role_id
JOIN permissions p
ON rp.permission_id = p.id
WHERE u.username = 'tharvesh2026'
ORDER BY p.permission_key;