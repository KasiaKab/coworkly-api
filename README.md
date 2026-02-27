# Coworkly API

A **coworking room booking system** built with Spring Boot as a portfolio project.  
Clean monolith architecture, progressively extended with security, AWS integration, and observability.

> **Status:** Active development – paused Nov 2025 – Feb 2026 (joined [Acorn](https://www.linkedin.com/company/acornsop/) project), back in Mar 2026

---

## Table of Contents

- [What is Coworkly?](#-what-is-coworkly)
- [Tech Stack](#%EF%B8%8F-tech-stack)
- [What's done](#-whats-done)
- [What's planned](#-whats-planned)
- [Project Structure](#-project-structure)
- [Running locally](#-running-locally)
- [API Overview](#-api-overview)
- [Architecture Decisions](#-architecture-decisions)

---

## 💡 What is Coworkly?

Coworkly allows users to browse available coworking resources, check time slot availability, and create bookings with idempotency support.
The project follows considerated, documented architecture – each decision is recorded as an ADR in [`docs/decisions/`](docs/decisions/) and visualized as diagrams in [`docs/designs/`](docs/designs/).

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.x, Java 17 |
| Database | PostgreSQL (dev/prod), H2 (test) |
| ORM | Spring Data JPA / Hibernate |
| Migrations | Flyway |
| Build | Maven |
| Containerization | Docker |
| Testing | JUnit 5, Mockito, AssertJ |
| API Docs | OpenAPI 3 / Swagger UI *(planned)* |
| Cloud | AWS SQS, DynamoDB, SES *(planned)* |

---

## ✅ What's done

- **Resource management** – list available coworking resources, check availability by date
- **Booking flow** – create bookings with `Idempotency-Key` header support, get booking by ID
- **Domain model** – `Resource`, `TimeSlot`, `Booking`, `BookingStatus` entities with JPA
- **Exception handling** – `GlobalExceptionHandler`, `NotFoundException`, `SlotAlreadyBookedException`
- **Database migrations** – Flyway (V1–V5), seed data for dev
- **Auth foundation** – `User` entity, `BCryptPasswordEncoder`, `UserService` (register + email check)
- **Configuration** – `dev` / `prod` / `test` profiles with Spring properties
- **Docker** – `docker-compose` for local PostgreSQL + pgAdmin
- **Architecture docs** – ADR-001 – ADR-004, C4 diagrams, ERD, API contract, sequence diagrams

---

## 🔜 What's planned

- **Cancel & delete bookings** – `PATCH /bookings/{id}/cancel`, `DELETE /bookings/{id}`
- **Get resource by ID** – `GET /resources/{id}`
- **HTTP status codes** – explicit status annotations across all controllers
- **Unit tests** – `BookingServiceTest`, `ResourceServiceTest`, `UserServiceTest` (Mockito + AssertJ)
- **JWT Security** – `JwtService`, `JwtAuthFilter`, `SecurityConfig`, `/auth/register`, `/auth/login` (ADR-006)
- **OpenAPI / Swagger UI** – springdoc-openapi, full API annotations (ADR-005)
- **AWS SQS** – event publishing for booking lifecycle (Free Tier)
- **AWS DynamoDB** – booking event log storage (Free Tier)
- **AWS SES** – email notification on booking confirmation (Free Tier)

---

## 📁 Project Structure
```
src/
├── main/
│   ├── java/com/coworkly/
│   │   ├── api/              # Controllers, DTOs, Mappers
│   │   ├── app/              # Services, Exception handling
│   │   ├── domain/           # Entities, Repositories, Auth
│   │   └── security/         # PasswordConfig (JWT planned)
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       ├── application-prod.properties
│       └── db/               # Flyway migrations + seed data
└── test/
    ├── java/                 # Tests (in progress)
    └── resources/
        └── application-test.properties
docs/
├── decisions/                # ADR-001 – ADR-004 (ADR-005, ADR-006 planned)
└── designs/                  # C4 diagrams, ERD, API contract, sequence diagrams
```

---

## 🚀 Running locally

### Requirements

- Java 17
- Maven 3.9+
- Docker

### Environment variables

Default values are set for local development – no configuration needed to run locally.  
For production, set `DB_URL`, `DB_USER`, `DB_PASSWORD` and `SPRING_PROFILES_ACTIVE=prod`.

### Spring Profiles

| Profile | Database | Flyway | DDL | Notes |
|---|---|---|---|---|
| `dev` | PostgreSQL local | enabled | validate | Default profile, seed data loaded |
| `prod` | PostgreSQL | enabled | validate | No seed data, WARN logging |
| `test` | H2 in-memory | disabled | create-drop | Used in unit/integration tests |

### Step 1 – Start PostgreSQL
```bash
docker-compose up -d
```

PostgreSQL available at `localhost:5432`.  
pgAdmin available at `http://localhost:5050`.

### Step 2 – Run the application
```bash
mvn "-Dspring-boot.run.profiles=dev" spring-boot:run
```

Application is available at:
- API: `http://localhost:8080`
- Health: `http://localhost:8080/actuator/health`
- Swagger UI: `http://localhost:8080/swagger-ui.html` *(planned)*

---

## 📋 API Overview

| Method | Path | Description | Status |
|--------|------|-------------|---|
| `GET` | `/resources` | List all resources | ✅ |
| `GET` | `/resources/{resourceId}/availability?date=` | Check available slots | ✅ |
| `GET` | `/resources/{id}` | Get resource by ID | 🔜 |
| `GET` | `/bookings/{id}` | Get booking by ID | ✅ |
| `POST` | `/bookings` | Create booking (Idempotency-Key) | ✅ |
| `PATCH` | `/bookings/{id}/cancel` | Cancel booking | 🔜|
| `DELETE` | `/bookings/{id}` | Delete booking | 🔜 |
| `POST` | `/auth/register` | Register user | 🔜 |
| `POST` | `/auth/login` | Login, returns JWT | 🔜 |

---

## 📐 Architecture Decisions

| ADR | Decision | Status |
|-----|----------|----|
| [ADR-001](docs/decisions/ADR-001-monolith-first.md) | Monolith-first approach | ✅  |
| [ADR-002](docs/decisions/ADR-002-database-choice.md) | PostgreSQL + Flyway | ✅  |
| [ADR-003](docs/decisions/ADR-003-rest-design-plan.md) | REST API design | ✅  |
| [ADR-004](docs/decisions/ADR-004-security-baseline.md) | Security baseline (JWT) | 🔜 |
| ADR-005 | OpenAPI / Swagger UI | 🔜 |
| ADR-006 | JWT implementation | 🔜 |

---

**Last updated:** 2026-02-27