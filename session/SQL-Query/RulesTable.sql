use authentication;

CREATE TABLE permissions(
    id INT AUTO_INCREMENT PRIMARY KEY,
    permission_key VARCHAR(100) UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE roles(
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(100) UNIQUE
);

CREATE TABLE role_permissions(
    role_id INT,
    permission_id INT,
    PRIMARY KEY(role_id, permission_id)
);