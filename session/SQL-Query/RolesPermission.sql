use authentication;

INSERT INTO permissions(permission_key, description)
VALUES
('ROLE_CREATE', 'Create custom roles'),
('ROLE_PERMISSION_MANAGE', 'Manage permissions assigned to roles');

INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p
WHERE r.role_name = 'SYS_ADMIN'
AND p.permission_key IN (
    'ROLE_CREATE',
    'ROLE_PERMISSION_MANAGE'
);

SELECT
    r.role_name,
    p.permission_key
FROM role_permissions rp
JOIN roles r ON rp.role_id = r.id
JOIN permissions p ON rp.permission_id = p.id
WHERE r.role_name = 'SYS_ADMIN'
ORDER BY p.permission_key;