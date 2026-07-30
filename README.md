# Projects

A personal learning monorepo of Java, Servlet/JSP, MySQL, and Spring/Spring Boot practice projects. The goal is to learn backend development by building real, working applications rather than only following theory.

> [!IMPORTANT]
> Some of the Live URL may not at some time due to free trial of VPS.

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
│   ├── SSBoot                 # Spring stereotype annotations demo
│   ├── Student                # Student/course REST API (JPA)
│   ├── getStarted             # Spring Core / IoC basics
│   └── javaBasedConfig        # Java-based Spring configuration
└── icore                      # IAM system (Servlet/JSP + MySQL)
```

---

## Projects

### 1. MyToDo

A Spring Boot REST API for managing to-do items with JWT-based authentication.

* User registration & login
* JWT issuing and validation (`JwtToken`, `JwtAuthFilter`)
* CRUD APIs for to-do items
* Spring Data JPA + H2 database
* DTO / mapper / service / repository layering
* Global exception handling
* springdoc-openapi (Swagger UI)

### 2. Spring Security / SpringSecurity

A minimal Spring Boot project used to practice HTTP Basic authentication with `SecurityConfig` and a simple protected controller.

### 3. SpringBoot/CoursePortal

A REST API for a course/student enrollment portal.

* Student, Course, and Enrollment entities
* Enrollment and portal-statistics services
* Student profile and summary endpoints
* Centralized exception handling

### 4. SpringBoot/JpaMapping

Practice project for JPA entity relationships (Student ↔ Course), DTOs, validation, and layered service/repository design.

### 5. SpringBoot/SSBoot

Small Spring Boot app demonstrating Spring stereotype annotations (`@Component`, `@Service`, etc.) and a basic login controller.

### 6. SpringBoot/Student

REST API for managing students and courses, built on Spring Data JPA with a full service/repository/controller structure and custom exception handling.

### 7. SpringBoot/getStarted

Basic Spring Core and Spring Boot learning examples: Maven setup, `ApplicationContext`, bean creation, XML configuration, IoC/DI basics, and Lombok practice.

### 8. SpringBoot/javaBasedConfig

Practice with Java-based Spring configuration (`@Configuration`, `@Bean`) as a step from XML config to annotation-based config.

### 9. icore (i.Core v1.2.0-ALPHA)

A Servlet/JSP + JDBC + MySQL Identity & Access Management (IAM) system.

It includes:

* User registration, login, logout
* Session-based authentication with CSRF protection
* Permission-based authorization (RBAC) — access is driven entirely by permissions assigned to roles, not hardcoded role checks
* Dynamic UI rendering based on user permissions
* Role management (create roles, assign/remove permissions)
* Password hashing (BCrypt)
* SQL scripts for roles, permissions, and role-permission mapping (`icore/SQL-Query`)

Live demo:

```text
https://cloud.imtharvesh.me/icore
```

See `icore/readme.md` for full details on how the permission system works.

---

## Technology Stack

**Servlet / JSP project (icore)**
Java, Jakarta Servlet API, JSP, Maven, MySQL, Jackson, BCrypt, Log4j2, Apache Tomcat

**Spring Boot projects**
Java, Maven, Spring Boot, Spring Data JPA, Spring Security, JWT, H2/MySQL, Lombok, springdoc-openapi

---

## Repository Note

This is a learning monorepo — it may contain compiled classes, target folders, local IDE files, and in-progress/practice code alongside working applications. Each sub-project has its own `pom.xml`/build files and can be built and run independently with Maven.

---

## Author

**Tharvesh Muhaideen A**
Learning by building real backend projects.
