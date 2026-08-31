# CineBook

CineBook is a Spring Boot 3 movie-booking demo with a responsive static frontend, JPA booking API, H2 local profile, and MySQL-compatible production profile.

## Run locally

```powershell
$env:SPRING_PROFILES_ACTIVE = "local"
.\mvnw.cmd spring-boot:run
```

Open `http://localhost:8080/`.

## Test

```powershell
.\mvnw.cmd test
```

## Build a deployable image

Production requires an external database. Set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` in the runtime environment.

```powershell
docker build -t cinebook .
docker run --rm -p 8080:8080 `
  -e DB_URL="jdbc:mysql://host.docker.internal:3306/cinebook" `
  -e DB_USERNAME="cinebook" `
  -e DB_PASSWORD="change-me" `
  cinebook
```

The production profile uses `ddl-auto=validate`, disables the H2 console, enables template caching, and never initializes SQL scripts automatically. Create and migrate the database schema before deployment.

## Deploy on Render

1. Open [Render](https://render.com/) and choose **New > Blueprint**.
2. Connect the `CHATUR-BHARADWAJ-098/BookMyShow-Mock-Spring` repository.
3. Render will detect `render.yaml` and create the `cinebook` Docker web service.
4. Enter a reachable MySQL-compatible `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` when prompted.
5. Deploy the service and use the generated `onrender.com` URL.

Render does not provide a native MySQL service. Use an external managed MySQL provider and create the CineBook schema before deploying because the production profile validates, rather than modifies, the schema.

## Use MySQL running on your machine

Render cannot connect to `localhost` directly. For a temporary demo, expose only the MySQL TCP port through an authenticated tunnel and keep your computer online:

1. Install [ngrok](https://ngrok.com/download), authenticate it, and start a TCP tunnel:

```powershell
ngrok tcp 3306
```

2. Restrict your MySQL user to the CineBook database and use a dedicated password. Do not expose the MySQL root user.
3. Copy the ngrok forwarding address, for example `0.tcp.ngrok.io:15432`.
4. In Render, set `DB_URL` to:

```text
jdbc:mysql://0.tcp.ngrok.io:15432/cinebook?sslMode=REQUIRED&serverTimezone=UTC
```

5. Set `DB_USERNAME` and `DB_PASSWORD` to the dedicated MySQL user credentials, then redeploy.

This is suitable for a short-lived demo only. The tunnel endpoint can change, your machine must remain powered on, and exposing a personal database increases risk. For a real deployment, move the database to a managed MySQL provider and rotate any credentials used during testing.
