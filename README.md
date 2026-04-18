# Profile Intelligence Service

A Spring Boot backend application that enriches user profiles using external intelligence APIs (Genderize, Agify, and Nationalize). The service leverages the Java 21 + Spring WebFlux (Project Reactor) stack for non-blocking concurrent API calls and stores enriched data in a persistent database using UUID v7.

---

## 🚀 Features

- **Concurrent Data Enrichment**: Uses `Mono.zip` to fetch data from three external APIs simultaneously, reducing response latency.
- **UUID v7**: Uses time-ordered UUIDs for better database indexing and performance.
- **Robust Error Handling**: Standardized JSON error responses for `400`, `404`, `422`, and `502` status codes.
- **Advanced Filtering**: Search through stored profiles with optional, case-insensitive query parameters.
- **Clean Architecture**: Implementation of the Service-Contract pattern with DTO-based communication.

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.x |
| Persistence | Spring Data JPA |
| Reactive Client | Spring WebFlux (WebClient) |
| Boilerplate Reduction | Lombok |
| Database | H2 (dev) / PostgreSQL (prod) |

---

## 📋 API Documentation

### 1. Enrich & Create Profile

**Endpoint:** `POST /api/profiles`

**Request Body:**
```json
{
  "name": "Full Name"
}
```

**Validation Rules:**
- Name must only contain letters and spaces — returns `422` otherwise.
- Name cannot be empty — returns `400` otherwise.

---

### 2. Get All Profiles

**Endpoint:** `GET /api/profiles`

**Optional Query Parameters:**

| Parameter | Type | Description |
|---|---|---|
| `gender` | `string` | Filter by gender (case-insensitive) |
| `country_id` | `string` | Filter by country code (e.g., `NG`) |
| `age_group` | `string` | Filter by age group |

**Example:**
