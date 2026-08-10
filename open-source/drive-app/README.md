# Filed — your own Google Drive, self-hosted

A self-hosted, Google-Drive-style file storage app: **Spring Boot + Thymeleaf** on the backend/frontend, **Cloudinary** for actual file storage/CDN. Register, make folders, upload files, share links publicly — all from your own server.

## Stack

- Spring Boot 3 (Java 17), server-rendered with **Thymeleaf** — no separate frontend build
- Spring Security with classic session-based form login
- Spring Data JPA + Postgres (H2 for local dev)
- **Cloudinary** for file storage — uploads go straight to Cloudinary as `authenticated` (private) assets; downloads are served through short-lived signed URLs your backend generates on demand, so files aren't publicly guessable
- Docker + docker-compose

## How storage works

- Cloudinary stores the actual bytes (images, PDFs, video, anything — uploaded with `resource_type: auto`).
- Your Postgres/H2 database only stores **metadata**: file name, folder, owner, Cloudinary `public_id`, size, format.
- Every download (`/drive/file/{id}/download`) checks you own the file, then asks Cloudinary for a fresh signed URL and redirects you to it. The link isn't permanently public.
- Share links (`/share/{token}`) work the same way but check the share token instead of a login — so a link can be handed to someone without an account.

## Quick start

### 1. Get Cloudinary credentials

You said you already have these — grab them from your [Cloudinary console](https://console.cloudinary.com) dashboard: **Cloud name**, **API Key**, **API Secret**.

### 2. Local run with H2 (fastest way to try it)

```bash
export CLOUDINARY_CLOUD_NAME=your-cloud-name
export CLOUDINARY_API_KEY=your-api-key
export CLOUDINARY_API_SECRET=your-api-secret

mvn spring-boot:run
```

Visit `http://localhost:8080`, register an account, and start uploading. Data resets on restart (H2 is in-memory) — files themselves stay in Cloudinary since that's separate storage.

### 3. Docker (Postgres, persistent)

Copy `.env.example` to `.env` and fill in your Cloudinary credentials, then:

```bash
docker compose up --build
```

Visit `http://localhost:8080`.

## Features (v1)

- Register / log in (session-based auth)
- Nested folders — create, navigate via breadcrumbs
- Upload / download files of any type
- Delete files and (empty) folders
- Search files and top-level folders by name
- Share links — generate a public, no-login-required link to a single file or an entire folder (recipients can browse the folder's subtree but can't escape it into the rest of your drive)

## Project layout

```
controller/   AuthController, DriveController (authenticated), ShareController (public), HomeController
service/      FolderService, FileService, ShareService, CloudinaryService, AuthService
entity/       User, Folder, FileItem, ShareLink
repository/   Spring Data JPA repositories
config/       SecurityConfig, CloudinaryConfig
templates/    Thymeleaf views (login, register, drive, search, share-*, error)
```

## Configuration

| Variable | Default | Purpose |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | `h2` | `h2` for local, `postgres` for docker/production |
| `CLOUDINARY_CLOUD_NAME` | — | **required** |
| `CLOUDINARY_API_KEY` | — | **required** |
| `CLOUDINARY_API_SECRET` | — | **required** |
| `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD` | — | Used by the `postgres` profile |

## Known v1 limitations / good next steps

- Folder delete requires the folder to be empty (no cascading delete yet) — add cascade or a recursive-delete confirmation.
- Share links never expire by default — the `ShareLink.expiresAt` field exists; wire up an expiry picker in the UI.
- No file preview (images/PDF inline viewer) yet — Cloudinary supports on-the-fly thumbnail transformations (`.../w_300,h_300,c_fill/...`) that would be a natural add for images.
- No storage quota per user.
- Signed URLs currently expire on Cloudinary's default window; tune via `CloudinaryService.signedDownloadUrl` if you need longer/shorter-lived links.

## License

MIT — see [LICENSE](LICENSE).
