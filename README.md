# 🛰️ Space Mission Telemetry Platform

A microservices-based backend platform for satellite mission operations — telemetry ingestion, anomaly alerting, and maintenance scheduling — built with **Java 17**, **Spring Boot**, **Spring Data JPA**, and **Spring Cloud Gateway**.

> ⚠️ **Status: Work in Progress.** This project currently implements the core architecture and entity models, but several key features described below are **not yet functional**. See [Known Issues & Limitations](#-known-issues--limitations) before relying on this for demos or evaluation.

---

## 📖 Business Scenario

Space agencies operate multiple satellites that continuously transmit telemetry data — battery level, temperature, signal strength, and orbital position. This platform is designed to:

- Store incoming telemetry data
- Detect abnormal sensor readings
- Raise alerts when thresholds are crossed
- Schedule maintenance for critical issues
- Generate overall mission health reports

---

## 🏗️ Architecture

```
                        ┌─────────────────┐
                        │   API Gateway    │
                        │   (port 8080)    │
                        └────────┬─────────┘
             ┌───────────────────┼───────────────────┬────────────────┐
             │                   │                    │                │
     ┌───────▼──────┐    ┌───────▼───────┐   ┌────────▼──────┐  ┌──────▼───────┐
     │  Satellite    │    │   Telemetry   │   │     Alert      │  │ Maintenance  │
     │  Service      │    │   Service     │   │    Service     │  │  Service     │
     │  (port 8081)  │    │  (port 8082)  │   │  (port 8083)   │  │ (port 8084)  │
     └───────────────┘    └───────────────┘   └────────────────┘  └──────────────┘
            │                     │                    │                   │
        MySQL DB              H2 (in-memory)        MySQL DB          H2 (in-memory)
```

**Intended** service-to-service flow (via `RestTemplate`):
`Telemetry Service → Alert Service` (on anomaly detection) → `Alert Service → Maintenance Service` (on CRITICAL severity)

---

## 🧩 Microservices

| Service | Port | Responsibility |
|---|---|---|
| **API Gateway** | 8080 | Single entry point, routes requests to backend services |
| **Satellite Service** | 8081 | Register and manage satellite/mission metadata |
| **Telemetry Service** | 8082 | Receive telemetry packets, monitor sensor values |
| **Alert Service** | 8083 | Raise and resolve alerts when thresholds are breached |
| **Maintenance Service** | 8084 | Schedule and track maintenance requests |

---

## 🗂️ Entity Model

**Satellite**
`satelliteId, name, missionName, launchDate, orbitType, status`

**Telemetry**
`telemetryId, satelliteId, batteryLevel, temperature, signalStrength, altitude, timestamp`

**Alert**
`alertId, satelliteId, alertType, severity, message, status`

**Maintenance**
`maintenanceId, satelliteId, engineerName, scheduleDate, remarks, status`

---

## 📌 Business Rules (Intended)

- Battery below 20% → `LOW_BATTERY` alert
- Temperature above threshold → `OVERHEAT` alert
- Signal strength below threshold → `COMMUNICATION_FAILURE` alert
- `CRITICAL` alerts automatically create a maintenance request
- Mission health: **GREEN** (all normal) / **YELLOW** (warnings) / **RED** (critical alerts)

---

## ▶️ Getting Started

### Prerequisites
- Java 17+
- Maven (or use the included `mvnw` wrapper)
- MySQL running locally (for Satellite & Alert services — see [Database Setup](#-database-setup))

### Run each service
Each service is a standalone Spring Boot app. Start them in this order:

```bash
# 1. Satellite Service
cd satellite
./mvnw spring-boot:run

# 2. Telemetry Service
cd telemetry/telemetry
./mvnw spring-boot:run

# 3. Alert Service
cd alert
./mvnw spring-boot:run

# 4. Maintenance Service
cd maintenance/maintenance
./mvnw spring-boot:run

# 5. API Gateway (start last)
cd "api-gateway (1)/api-gateway"
./mvnw spring-boot:run
```

### 🗄️ Database Setup
`satellite` and `alert` services use MySQL with `root/root` credentials and expect a local database — create schemas before starting:

```sql
CREATE DATABASE satellite_db;
CREATE DATABASE alert_db;
```
Update `spring.datasource.url` in each service's `application.properties` accordingly (not currently set explicitly).

`telemetry` and `maintenance` services use **H2 in-memory databases** — no setup needed, but data resets on every restart. H2 console available at `/h2-console` on each service's own port.

---

## 📡 API Reference (Current Actual State)

> Routes marked ⚠️ are **not yet reachable through the Gateway** due to a path mismatch between gateway config and controller mappings — call the service port directly for now.

### Satellite Service — `localhost:8081`
| Method | Endpoint | Status |
|---|---|---|
| POST | `/satellites` | ✅ Working |
| GET | `/satellites` | ❌ Not implemented |
| GET | `/satellites/{id}` | ❌ Not implemented |
| PUT | `/satellites/{id}` | ❌ Not implemented |
| DELETE | `/satellites/{id}` | ❌ Not implemented |

### Telemetry Service — `localhost:8082`
| Method | Endpoint | Status |
|---|---|---|
| POST | `/api/telemetry` | ⚠️ Endpoint exists but service layer is stubbed — does not persist data |
| GET | `/api/telemetry/{satelliteId}` | ⚠️ Stubbed — returns `null` |
| GET | `/api/telemetry/latest/{satelliteId}` | ⚠️ Stubbed — returns `null` |

### Alert Service — `localhost:8083`
| Method | Endpoint | Status |
|---|---|---|
| POST | `/alerts` | ✅ Working |
| GET | `/alerts` | ✅ Working |
| PUT | `/alerts/{id}/resolve` | ✅ Working |
| GET | `/alerts/{id}` | ❌ Not implemented |
| DELETE | `/alerts/{id}` | ❌ Not implemented |

### Maintenance Service — `localhost:8084`
| Method | Endpoint | Status |
|---|---|---|
| POST | `/maintenance` | ✅ Working |
| GET | `/maintenance` | ✅ Working |
| GET | `/maintenance/{id}` | ✅ Working |
| PUT | `/maintenance/{id}` | ✅ Working |
| DELETE | `/maintenance/{id}` | ✅ Working |

### Reports
| Method | Endpoint | Status |
|---|---|---|
| GET | `/reports/mission-health/{satelliteId}` | ❌ Not implemented — no report module exists yet |

---

## ⚠️ Known Issues & Limitations

This section is intentionally direct so contributors know exactly what's left to build.

1. **Gateway route mismatch** — Gateway routes (`/api/v1/solar-panels/**`, `/api/v1/wind-turbines/**`, `/api/v1/distribution/**`) don't match the actual controller paths (`/satellites`, `/api/telemetry`, `/maintenance`). Only `/alerts/**` is correctly routed. Requests via `localhost:8080` will 404 for 3 of 4 services until routes are corrected.
2. **Telemetry Service is non-functional** — `TelemetryServiceImpl` methods are stubs (`// TODO`, `return null;`); no data is ever persisted or retrieved.
3. **Incorrect request DTO in Telemetry** — `TelemetryController.addTelemetry()` accepts `TelemetryApplication.telemetryapplication` (an empty inner class left over from `@SpringBootApplication`) instead of a proper `TelemetryRequestDTO` with the required fields.
4. **No anomaly detection logic** — none of the business rules (low battery, overheat, signal loss thresholds) are implemented anywhere.
5. **No service-to-service integration** — `RestTemplate` beans are configured in Alert and Telemetry services but are never actually called. Telemetry → Alert and Alert → Maintenance integrations described in the SRS do not exist yet.
6. **No mission health report endpoint.**
7. **Incomplete CRUD** — Satellite Service supports Create only; Alert Service is missing get-by-id and delete.
8. **Incomplete exception handling** — Satellite Service has an empty exception package (no `SatelliteNotFoundException`, no handler).
9. **Mixed database backends** — Satellite/Alert use MySQL, Telemetry/Maintenance use in-memory H2 (data lost on restart), which doesn't match the SRS requirement of MySQL throughout.
10. **No automated tests** beyond the default Spring Boot–generated `*ApplicationTests.java` placeholders.

---

## 🛣️ Roadmap

- [ ] Fix API Gateway routes to match actual controller paths
- [ ] Implement Telemetry persistence + proper `TelemetryRequestDTO` with validation
- [ ] Implement anomaly detection rules in Telemetry Service
- [ ] Wire up `RestTemplate` calls: Telemetry → Alert, Alert → Maintenance
- [ ] Complete CRUD for Satellite and Alert services
- [ ] Add `SatelliteNotFoundException` + global exception handler for Satellite Service
- [ ] Build mission health report endpoint
- [ ] Standardize all services on MySQL
- [ ] Add unit/integration tests
- [ ] Add Postman collection and API test screenshots

---

## 🧰 Tech Stack

- Java 21
- Spring Boot / Spring Data JPA
- Spring Cloud Gateway
- MySQL, H2 (in-memory)
- RestTemplate
- Bean Validation (Jakarta Validation)
- Maven

---
## Output:
<img width="1897" height="816" alt="Screenshot 2026-07-23 125229" src="https://github.com/user-attachments/assets/c1bdc71e-ff9c-4a9a-8756-43459e782ea7" />
<img width="1237" height="272" alt="Screenshot 2026-07-23 125526" src="https://github.com/user-attachments/assets/519585ca-be44-448d-8997-2a15f1e6863c" />
<img width="473" height="177" alt="Screenshot 2026-07-23 125611" src="https://github.com/user-attachments/assets/b1f1b01e-f8f3-464b-b358-6686e86a9ce0" />
<img width="441" height="236" alt="Screenshot 2026-07-23 125626" src="https://github.com/user-attachments/assets/8fe1f8c1-9883-47ee-9bba-86be6d5ea0d0" />
<img width="1028" height="337" alt="Screenshot 2026-07-23 130125" src="https://github.com/user-attachments/assets/4d0fa45e-f304-45a9-abdc-ae29826af4ac" />
<img width="850" height="682" alt="Screenshot 2026-07-23 130435" src="https://github.com/user-attachments/assets/a9237d40-1827-408c-9128-39ada8e3e4ee" />







