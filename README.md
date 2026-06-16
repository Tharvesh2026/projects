# Project

This repository is a personal learning monorepo that contains multiple Java, Servlet, JSP, MySQL, and Spring/Spring Boot practice projects.

The main goal of this repository is to learn backend development step by step by building real working projects instead of only following theory.

---

## Repository Structure

```text
Project
├── session
│   ├── SQL-Query
│   ├── src
│   └── target
│
└── SpringBoot
    ├── getStarted
    └── javaBasedConfig
```

---

## Projects

### 1. session

`session` is a Java Servlet/JSP based Identity & Access Management learning project.

It includes:

* User Registration
* User Login
* User Logout
* Session Management
* Remember Me Cookie
* CSRF Protection
* Password Hashing
* User Management
* Role Management
* Permission-Based Access Control
* REST APIs
* JSP Frontend Pages
* MySQL Database Integration
* Development and Production Environment Support

Live URL:

```text
https://cloud.imtharvesh.me/session
```

Current release line:

```text
IdentityCore v1.2.0-alpha
```

---

### 2. SpringBoot/getStarted

This project contains basic Spring Core and Spring Boot learning examples.

Covered concepts include:

* Maven project setup
* ApplicationContext
* Bean creation
* XML configuration
* IoC Container basics
* Dependency Injection basics
* Lombok practice

---

### 3. SpringBoot/javaBasedConfig

This project is used to practice Java-based Spring configuration.

Covered concepts include:

* Java Configuration
* `@Configuration`
* `@Bean`
* Object creation through Spring container
* Moving from XML configuration to annotation-based configuration

---

## Current Focus

The current active project is:

```text
session
```

Main learning focus:

```text
Servlet/JSP IAM System
↓
Permission-Based Authorization
↓
Role Management
↓
Spring Boot Migration Preparation
```

---

## Technology Stack

### Servlet Project

* Java 25
* Jakarta Servlet API
* JSP
* Maven
* MySQL
* Jackson
* BCrypt
* Log4j2
* Apache Tomcat 11

### Spring Learning Projects

* Java
* Maven
* Spring Core
* Spring Boot basics
* Lombok

---

## Repository Note

This is a learning monorepo.

It may include:

* Source files
* Compiled class files
* WAR files
* Target folders
* Local IDE files
* Practice code
* Trial projects

The repository is intentionally kept as plug-and-play for learning and deployment testing.

Current `.gitignore` mainly ignores:

```text
.temp
temp
```

---

## Release History

### I.A.M v1.0.0-beta

Initial authentication and session management foundation.

### IdentityCore v1.1.0

Stable Servlet/JSP IAM foundation with authentication, user management, APIs, security, and environment support.

### IdentityCore v1.2.0-alpha

RBAC and permission-based authorization foundation.

Includes:

* roles table
* permissions table
* role_permissions mapping
* role_id migration
* PermissionValidator
* permission-based AuthFilter
* dynamic UI access control

---

## Upcoming Plans

* Complete custom role creation
* Complete role permission management
* Add role status management
* Improve audit logging
* Clean frontend and API route separation
* Prepare Spring Boot v2.0.0 migration
* Add service layer in Spring Boot version

---

## Author

Tharvesh Muhaideen A

Learning by building real backend projects.
