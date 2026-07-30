# Projects (RBAC-With-Security)

A personal learning monorepo of Java, Servlet/JSP, MySQL, and Spring/Spring Boot practice projects. This branch builds on `main` with additional Spring Boot projects and an earlier snapshot of the IAM system, focused on role-based access control (RBAC) and security experiments.

> [!IMPORTANT]
> Some of live url may not be work due to free trial of VPS

---

## Repository Structure

```text
projects
├── MyToDo                     # Spring Boot ToDo app with JWT auth
├── Spring Security
│   └── SpringSecurity         # Spring Security (HTTP Basic) demo
├── SpringBoot
│   ├── CoursePortal           # Student/course/enrollment REST API
│   ├── JpaMapping             # JPA entity relationship practice
│   ├── MailSender              # Spring Boot email sending service
│   ├── SSBoot                 # Spring stereotype annotations demo
│   ├── Student                # Student/course REST API (JPA)
│   ├── Task-Tracker           # Task tracker with OAuth2 login
│   ├── getStarted             # Spring Core / IoC basics
│   └── javaBasedConfig        # Java-based Spring configuration
├── icore                      # IAM system (Servlet/JSP + MySQL)
└── session                    # Earlier snapshot of the IAM system
```

---

## Projects

### 1. MyToDo

Spring Boot REST API for managing to-do items with JWT authentication, Spring Data JPA, DTO/mapper layering, and global exception handling.

### 2. Spring Security / SpringSecurity

Minimal Spring Boot project practicing HTTP Basic authentication.

### 3. SpringBoot/CoursePortal

REST API for a course/student enrollment portal, with student profile, enrollment, and portal-statistics services.

### 4. SpringBoot/JpaMapping

Practice project for JPA entity relationships (Student ↔ Course) with DTOs and layered service/repository design.

### 5. SpringBoot/MailSender

A small Spring Boot service for sending emails (`SimpleMailService`, `MailController`).

### 6. SpringBoot/SSBoot

Demonstrates Spring stereotype annotations and a basic login controller.

### 7. SpringBoot/Student

REST API for managing students and courses on Spring Data JPA.

### 8. SpringBoot/Task-Tracker

A task-tracking application with OAuth2-based login (`HttpCookieOAuth2AuthorizationRequestRepository`, `SecurityConfig`), task model, service, and controller.

### 9. SpringBoot/getStarted & SpringBoot/javaBasedConfig

Spring Core basics and Java-based Spring configuration practice (see `main` branch for details).

### 10. icore — i.Core v1.2.0-ALPHA

Servlet/JSP + JDBC + MySQL Identity & Access Management (IAM) system with:

* Registration, session-based login/logout, CSRF protection
* Permission-based authorization (RBAC) — access driven by permissions assigned to roles
* Dynamic UI rendering based on permissions
* Role management (create roles, assign/remove permissions)
* SQL scripts for roles, permissions, and mappings (`icore/SQL-Query`)

Live demo: `https://cloud.imtharvesh.me/icore`

### 11. session

An earlier working snapshot of the same Servlet/JSP IAM codebase as `icore`, kept on this branch alongside a `deploy.bat` deployment script. Superseded by `icore` going forward.

---

## Technology Stack

**Servlet / JSP (icore, session)**
Java, Jakarta Servlet API, JSP, Maven, MySQL, Jackson, BCrypt, Log4j2, Apache Tomcat

**Spring Boot projects**
Java, Maven, Spring Boot, Spring Data JPA, Spring Security, OAuth2, JWT, H2/MySQL, Lombok, springdoc-openapi

---

## Repository Note

This is a learning monorepo — it may contain compiled classes, target folders, local IDE files, and in-progress/practice code alongside working applications. Each sub-project has its own `pom.xml`/build files and can be built and run independently with Maven.

---

## Author

**Tharvesh Muhaideen A**
Learning by building real backend projects.
