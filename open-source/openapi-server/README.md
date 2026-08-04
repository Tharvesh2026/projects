# Open Source API Server

A free, open-source **practice REST API** for beginners learning frontend or backend development — similar in spirit to [FreeAPI.app](https://api.freeapi.app) and [gerasim.in's project API](https://projectapi.gerasim.in). Built with **Spring Boot 3**, documented with **Swagger UI**, and fully **Dockerized**.

Fork it, self-host it, add your own resources, or point your frontend practice project straight at it.

## Features

- 📄 **Interactive Swagger docs** at `/swagger-ui.html` — every endpoint is documented and "Try it out"-able, no Postman needed
- 🔐 **JWT auth** — register/login, then use the token for protected routes
- 📝 **CRUD resources for practice**: Todos (private per user), Posts + Comments (public read, authenticated write), Products (mock e-commerce catalog)
- 🎲 **Zero-setup fun endpoints**: random programming quotes and jokes — no auth, no body, just `GET`
- 🌱 **Seeded sample data** on first run (a demo user, sample posts, sample products) so the API isn't empty
- 🐳 **Docker + docker-compose** — one command to run API + Postgres together
- 🧪 **H2 in-memory profile** for a zero-config local run with no Docker/DB needed
- 🌍 **CORS open by default** so any frontend (CodeSandbox, StackBlitz, localhost, etc.) can call it directly

## Quick start

### Option 1 — Docker (recommended, uses Postgres)

```bash
docker compose up --build
```

The API will be available at `http://localhost:8080`, docs at `http://localhost:8080/swagger-ui.html`.

### Option 2 — Local run with H2 (no Docker/DB needed)

```bash
mvn spring-boot:run
```

(Requires JDK 17+ and Maven installed locally. If you'd rather not install Maven, add the wrapper with `mvn -N io.takari:maven:wrapper` and use `./mvnw spring-boot:run` instead — or just use Docker.)

This uses the `h2` profile by default (in-memory DB, data resets on restart). H2 console is available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:apidb`, user `sa`, empty password).

## Try it immediately

A demo user is seeded on first boot:

```
username: demo
password: password123
```

```bash
# Log in and grab a token
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"demo","password":"password123"}'

# Use the token
curl http://localhost:8080/api/v1/todos \
  -H "Authorization: Bearer <token>"

# No auth needed at all:
curl http://localhost:8080/api/v1/quotes/random
curl http://localhost:8080/api/v1/products
```

## Endpoints overview

| Resource | Auth needed | Notes |
|---|---|---|
| `POST /api/v1/auth/register` | No | Create an account, returns a JWT |
| `POST /api/v1/auth/login` | No | Returns a JWT |
| `GET /api/v1/users/me` | Yes | Current user's profile |
| `GET/POST/PUT/DELETE /api/v1/todos` | Yes | Private to each user |
| `GET /api/v1/posts` | No | List/read is public |
| `POST/PUT/DELETE /api/v1/posts` | Yes | Only the author can edit/delete |
| `GET /api/v1/posts/{id}/comments` | No | List/read is public |
| `POST /api/v1/posts/{id}/comments` | Yes | Add a comment |
| `GET /api/v1/products` | No | Mock e-commerce catalog, filterable by `?category=` |
| `POST/PUT/DELETE /api/v1/products` | Yes | Manage catalog |
| `GET /api/v1/quotes/random`, `/api/v1/jokes/random` | No | Fun, zero-setup endpoints |

Full interactive schema (request/response shapes, all status codes) lives in Swagger UI — that's the source of truth, not this table.

## Configuration

All config is environment-variable driven (see `application.yml`):

| Variable | Default | Purpose |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | `h2` | `h2` for local, `postgres` for docker/production |
| `JWT_SECRET` | dev placeholder | **Change this in production** |
| `JWT_EXPIRATION_MS` | `86400000` (24h) | Token lifetime |
| `PORT` | `8080` | HTTP port |
| `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD` | — | Used by the `postgres` profile |

## Deploying publicly

Any platform that runs a Docker container works: Railway, Render, Fly.io, a VPS with `docker compose up -d`. Set `JWT_SECRET` to a real secret and point `SPRING_PROFILES_ACTIVE=postgres` at a managed Postgres instance.

## Extending it

The project is intentionally small and readable so it's easy to add new practice resources:

1. Add an `@Entity` in `entity/`
2. Add a `JpaRepository` in `repository/`
3. Add request DTOs in `dto/`
4. Add a `@RestController` in `controller/` with `@Tag`/`@Operation` annotations for Swagger
5. Decide read/write auth rules in `SecurityConfig`

Ideas for new resources: notes, recipes, movies, bookmarks, chat messages, weather mock data, a paginated "infinite list" endpoint for practicing infinite scroll.

## Contributing

PRs welcome — new mock resources, better validation, rate limiting, pagination helpers, an OpenAPI spec export, whatever's useful for learners. Please keep endpoints documented with springdoc annotations so Swagger UI stays accurate.

## License

MIT — see [LICENSE](LICENSE).
