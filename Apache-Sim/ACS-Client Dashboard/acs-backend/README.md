# IaaS Console Backend

This is the operator-facing service layer for Apache CloudStack. It serves as an auth gateway, exposes REST APIs for a future React frontend, and currently includes a Thymeleaf-based UI.

## Architecture & Authentication Flows

This system maintains a strict separation between two identities:

1. **Operator Login (OAuth2)**
   - Operators access this console by authenticating via OAuth2.
   - Primary provider: `identityCore`
   - Secondary provider: `github` (useful for demos)
   - *This handles HUMAN authentication to the console.*

2. **Service-to-CloudStack Auth (HMAC-SHA1)**
   - When the console communicates with the backend CloudStack API, it signs requests using an API key and Secret key.
   - The signing scheme uses HMAC-SHA1 over a sorted, URL-encoded query string, Base64-encoded.
   - Credentials for this are stored securely in `application.properties` under `app.cloudstack.*` namespace.
   - *This handles SYSTEM authentication between the console and CloudStack.*

### Future Scopes & Permissions
Currently, synced domains and accounts are persisted locally in H2 for caching and relational joins.
In the future, the operator's `User` identity (provided by OAuth2) will be mapped to a CloudStack `Domain` and `Account`. The backend will then restrict which domains/networks the operator can see or manipulate based on this mapping, resolving scopes before delegating to the unified CloudStack service account.
