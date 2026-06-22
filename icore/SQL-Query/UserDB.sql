create database authentication;
use authentication;


Select * from users;

CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(100),
    mailId varchar(255) unique,
    username VARCHAR(100) unique,
    password VARCHAR(255)
);

ALTER TABLE users ADD COLUMN role VARCHAR(20) DEFAULT 'USER';
ALTER TABLE users ADD COLUMN status VARCHAR(20) DEFAULT 'ACTIVE';


UPDATE users
SET role = 'SYS_ADMIN'
WHERE mailId = 'tharvesh2026@gmail.com';

UPDATE users
SET role = 'ADMIN'
WHERE mailId = 'imtharvesh@gmail.com';
