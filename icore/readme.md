# i.Core v1.2.0-ALPHA

**i.Core** is an Identity & Access Management (IAM) application built with JSP, Servlets, JDBC, and MySQL. It implements permission-based authorization, dynamic UI rendering, session management, CSRF protection, and role administration.

## Key Features

### Authentication

* User registration with automatic `USER` role assignment.
* Secure login using email and password verification.
* Session-based authentication.
* CSRF token protection.
* Optional Remember Me functionality.

### Authorization

* Permission-based access control (RBAC).
* Authorization decisions are based on permissions assigned to roles, not hardcoded role checks.
* Every protected request is validated through a centralized filter.
* Permissions are dynamically loaded from the database.

### Dynamic UI

* Menus, pages, and actions are displayed based on user permissions.
* No need for hardcoded checks such as:

```java
if(role.equals("ADMIN"))
```

* User capabilities are determined entirely by assigned permissions.

### Role Management

* Create and manage custom roles.
* Assign default permissions during role creation.
* Add or remove permissions through a role management interface.
* Permission changes take effect automatically for all users assigned to that role.

## How It Works

1. User registers and receives a default role.
2. User logs in and a session is created.
3. Authentication filter verifies the session.
4. Authorization filter retrieves the user's role and permissions from the database.
5. Requested resources are validated against required permissions.
6. Access is granted or denied based on permissions.

## Example

To access `/users`, the system requires:

```text
USER_READ
```

If the user's role contains that permission:

```text
Access Granted
```

Otherwise:

```text
403 Access Denied
```

## Release

**Version:** v1.2.0-ALPHA

### Included

* Complete source code
* Setup guide
* Pre-configured Tomcat server package
* Database scripts
* Permission-based authorization system
* Dynamic UI implementation

### Live Demo

Available at: https://cloud.imtharvesh.me/icore

## Project Summary

When a user logs in, the session identifies who they are; the role-permission system determines what they are allowed to do.
