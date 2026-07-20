# Tharvbytes Tasks

Tharvbytes Tasks is a lightweight Spring Boot 3.x and Java 21 sample application demonstrating integration with **I.Core** as the sole external OAuth2/OIDC Identity and Authorization Provider.

This application acts purely as an **OAuth2/OIDC Client** (there is no local database for user credentials, and no local username/password forms).

---

## Technical Architecture & SSO Flow

When a user accesses the application:
1. **Unauthenticated Check**: The application checks if a local security session exists. Since none is present, Spring Security triggers the OAuth2 Authorization Code flow.
2. **Redirect to IdP**: The client redirects the browser to I.Core's authorization endpoint:
   `http://localhost:8080/oauth2/authorize?response_type=code&client_id=tharvbytes-task-app&scope=openid%20profile%20email&redirect_uri=http://127.0.0.1:9000/login/oauth2/code/icore`
3. **User Authentication**: The user logs in on I.Core's login page.
4. **Callback (Redirect Back)**: I.Core redirects the user back to the registered callback URI:
   `http://127.0.0.1:9000/login/oauth2/code/icore?code=AUTHORIZATION_CODE`
5. **Token Exchange**: The application exchanges the authorization code for tokens (ID Token, Access Token) at I.Core's token endpoint behind the scenes.
6. **PKCE (Proof Key for Code Exchange)**: Because I.Core enforces PKCE (`requireProofKey(true)`), the client configures a custom `OAuth2AuthorizationRequestResolver` that applies `OAuth2AuthorizationRequestCustomizers.withPkce()` to append the `code_challenge` and `code_challenge_method` query parameters.
7. **Authorities Mapping**: The application parses the `OidcIdToken`, extracts identity details, and processes the customized `role` and `permissions` claims to map them directly to Spring Security `GrantedAuthority` objects.
8. **Session Setup**: A secure local session is initiated, and the user is redirected to the home dashboard.

---

## PKCE (Proof Key for Code Exchange) Support

Since **I.Core** enforces PKCE security on its OAuth2 authorization endpoints, and Spring Security by default only auto-applies PKCE to public clients (without secrets), we explicitly enable it in `SecurityConfig.java` using:
```java
private OAuth2AuthorizationRequestResolver authorizationRequestResolver(ClientRegistrationRepository repo) {
    DefaultOAuth2AuthorizationRequestResolver resolver = 
        new DefaultOAuth2AuthorizationRequestResolver(repo, "/oauth2/authorization");
    resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());
    return resolver;
}
```
This forces the inclusion of `code_challenge` parameters in client redirect requests, preventing the `[invalid_request] OAuth 2.0 Parameter: code_challenge` error from I.Core.

---

## Where the "permissions" claim is read

The OIDC ID Token contains custom claims:
- `"role"`: A string containing the user's role (e.g. `admin`, `user`).
- `"permissions"`: An array of strings representing permissions (e.g. `["USER_READ", "TASK_DELETE"]`).

These claims are extracted and converted into Spring Security authorities in:
* **[SecurityConfig.java](src/main/java/dev/tharvbytes/tasks/config/SecurityConfig.java)** inside the `userAuthoritiesMapper` bean:
  ```java
  List<String> permissions = idToken.getClaimAsStringList("permissions");
  if (permissions != null) {
      for (String perm : permissions) {
          mappedAuthorities.add(new SimpleGrantedAuthority(perm));
      }
  }
  ```

* **[TaskController.java](src/main/java/dev/tharvbytes/tasks/controller/TaskController.java)**:
  The `deleteTask` mapping is guarded via method-level security:
  ```java
  @PostMapping("/tasks/{id}/delete")
  @PreAuthorize("hasAuthority('TASK_DELETE')")
  public String deleteTask(...) {
      ...
  }
  ```
  If the ID token issued by I.Core does not contain the `TASK_DELETE` permission, Spring Security blocks the request and throws an `AccessDeniedException`, which the controller catches and displays as a sleek alert banner on the page.

---

## Prerequisites

1. **Java 21 or higher** installed.
2. **I.Core Authorization Server** running on `http://localhost:8080` with the client `tharvbytes-task-app` registered.
3. Redirect URI registered on I.Core: `http://127.0.0.1:9000/login/oauth2/code/icore`.

---

## How to Run

1. Navigate to the project root directory:
   ```bash
   cd C:\Users\DS\Documents\projects\SpringBoot\Task-Tracker
   ```
2. Build and run the project using the Maven wrapper:
   ```bash
   # On Windows (cmd/PowerShell)
   .\mvnw spring-boot:run
   
   # On Linux/macOS
   ./mvnw spring-boot:run
   ```
3. Open your browser and navigate to:
   [http://127.0.0.1:9000](http://127.0.0.1:9000) (or `http://localhost:9000`)
4. You will be immediately redirected to the I.Core login page. Upon successful authentication, you will return to the tasks list.
