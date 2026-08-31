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
