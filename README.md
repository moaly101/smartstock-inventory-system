# SmartStock – Inventory Management System

Ein modernes Lagerverwaltungssystem mit automatisierten Schwellenwert-Warnungen, JWT-basierter Authentifizierung und rollenbasierter Zugriffskontrolle. Dieses Projekt demonstriert die Anwendung einer **Schichten-Architektur** (Layered Architecture), konsequentes **Test-Driven Development (TDD)** und die Containerisierung einer Java-Backend-Anwendung.

---

## Features

- **RESTful API:** Endpunkte zum Erstellen, Abrufen, Aktualisieren und Löschen von Produkten.
- **JWT-Authentifizierung:** Sichere Anmeldung mit JSON Web Tokens (gespeichert als HttpOnly-Cookie).
- **Rollenbasierte Zugriffskontrolle:** Trennung zwischen `ROLE_ADMIN` und `ROLE_USER` – Admins können Produkte verwalten, User können das Inventar einsehen.
- **Data Validation:** Strikte Validierung der Eingabedaten (z. B. keine negativen Bestände) via Jakarta Bean Validation.
- **Smart Alerting:** Logik zur Identifizierung von Beständen, die den Mindestschwellenwert unterschreiten (`needsRestock`).
- **Lager-Dashboard:** Eine Web-Oberfläche (Thymeleaf) zur Visualisierung des aktuellen Inventars.
- **Persistenz:** Vollständige Integration einer PostgreSQL-Datenbank mit Flyway-Migrationen.
- **Dockerized:** Einfaches Setup der gesamten Infrastruktur via Docker Compose.

---

## Quick Links (Lokale Umgebung)

| Seite | URL |
|---|---|
| Login | `http://localhost:8080/auth/login` |
| Registrierung | `http://localhost:8080/auth/register` |
| Lager-Dashboard (User) | `http://localhost:8080/inventory` |
| Admin-Dashboard | `http://localhost:8080/admin/dashboard` |
| API Dokumentation (Swagger UI) | `http://localhost:8080/swagger-ui/index.html` |

---

## Tech Stack

| Kategorie | Technologien |
|---|---|
| **Backend** | Java 21, Spring Boot 3.4, Spring Data JPA, Jakarta Validation |
| **Security** | Spring Security, JWT (JSON Web Tokens) |
| **Frontend** | Thymeleaf (HTML5 / CSS) |
| **Datenbank** | PostgreSQL (H2 für isolierte Tests) |
| **Migrations** | Flyway |
| **Dokumentation** | Swagger UI / OpenAPI 3 |
| **Infrastruktur** | Docker & Docker Compose |
| **Testing** | JUnit 5, Mockito, AssertJ, MockMvc |

---

## Voraussetzungen

Folgende Tools müssen installiert sein:

- [Docker & Docker Compose](https://www.docker.com/)
- [Java 21](https://adoptium.net/) (nur für lokale Entwicklung ohne Docker)
- [Git](https://git-scm.com/)

---

## Installation & Start

### 1. Repository klonen
```bash
  git clone https://github.com/moaly101/smartstock-inventory-system.git
cd smartstock-inventory-system
```

### 2. System starten (Docker Compose)

Docker Compose startet automatisch die PostgreSQL-Datenbank:

```bash
  docker-compose up --build
```

Die Datenbank wird beim ersten Start automatisch über Flyway-Migrationen initialisiert (Tabellen + Standard-Admin-User).

### 3. Anwendung starten

```bash
  ./gradlew bootRun
```

### 4. Tests ausführen

```bash
  ./gradlew test
```

---

## Konfiguration

Die Anwendung lässt sich über Umgebungsvariablen konfigurieren. Standardwerte sind bereits gesetzt:

| Variable | Beschreibung | Standard |
|---|---|---|
| `JWT_SECRET` | Geheimer Schlüssel für JWT-Signierung | (vordefinierter Hex-String) |
| `JWT_EXPIRATION` | Token-Gültigkeitsdauer in Millisekunden | `86400000` (24h) |

Die Datenbankverbindung ist in `application.properties` konfiguriert und zeigt auf den Docker-Container:

```
Host:     localhost:5432
Datenbank: userdb
User:     admin
Passwort: geheim123
```

> **Hinweis:** Für Produktionsumgebungen sollten alle Credentials und der JWT-Secret über Umgebungsvariablen oder einen Secret-Manager gesetzt werden.

---

## Authentifizierung

### Registrierung & Login (Web)

1. Öffne `http://localhost:8080/auth/register` und erstelle einen Account.
2. Melde dich unter `http://localhost:8080/auth/login` an.
3. Nach dem Login wird ein **JWT-Token als HttpOnly-Cookie** gesetzt.
4. Abhängig von der Rolle wirst du weitergeleitet:
    - `ROLE_ADMIN` → `/admin/dashboard`
    - `ROLE_USER` → `/inventory`

### Standard-Admin-User

Beim ersten Start wird automatisch ein Admin-Account per Flyway-Migration angelegt. Die Zugangsdaten sind in `V2__insert_admin_user.sql` hinterlegt.

---

## API-Endpunkte

### Authentifizierung

| Methode | Endpunkt | Beschreibung | Zugriff |
|---|---|---|---|
| `GET` | `/auth/login` | Login-Seite | Öffentlich |
| `POST` | `/auth/login-form` | Einloggen, JWT-Cookie setzen | Öffentlich |
| `GET` | `/auth/register` | Registrierungs-Seite | Öffentlich |
| `POST` | `/auth/register` | Neuen User anlegen | Öffentlich |

### Produkte (REST API)

| Methode | Endpunkt | Beschreibung | Zugriff |
|---|---|---|---|
| `GET` | `/api/products` | Alle Produkte abrufen | Authentifiziert |
| `POST` | `/api/products` | Neues Produkt anlegen | `ROLE_ADMIN` |
| `PUT` | `/api/products/{id}/stock` | Bestand aktualisieren | `ROLE_ADMIN` |
| `DELETE` | `/api/products/{id}` | Produkt löschen | `ROLE_ADMIN` |
| `GET` | `/api/products/alerts` | Produkte unter Mindestbestand | `ROLE_ADMIN` |

### Beispiel: Bestand aktualisieren

```bash
  # Verkauf von 5 Einheiten (negativer Wert = Abgang)
curl -X PUT "http://localhost:8080/api/products/1/stock?amount=-5" \
     -H "Cookie: JWT_TOKEN=<dein-token>"

# Wareneingabe von 20 Einheiten
curl -X PUT "http://localhost:8080/api/products/1/stock?amount=20" \
     -H "Cookie: JWT_TOKEN=<dein-token>"
```

---

## Projektstruktur

```
src/main/java/org/example/smartstocksystem/
├── config/
│   ├── ApplicationConfig.java       # Spring Beans (AuthManager, PasswordEncoder)
│   ├── SecurityConfig.java          # HTTP-Security, JWT-Filter-Konfiguration
│   ├── JwtAuthenticationFilter.java # JWT aus Cookie lesen & validieren
│   └── FlywayConfig.java            # Flyway-Konfiguration
├── controller/
│   ├── AuthController.java          # Login/Register (Web-Formulare)
│   ├── AuthApiController.java       # Login/Register (REST API)
│   ├── ProductController.java       # CRUD für Produkte (REST API)
│   ├── ProductWebController.java    # Inventar-Ansicht (Thymeleaf)
│   └── AdminController.java        # Admin-Dashboard
├── service/
│   ├── ProductService.java          # Geschäftslogik für Produkte
│   ├── UserService.java             # Benutzerverwaltung
│   └── JWTService.java             # Token generieren & validieren
├── repository/
│   ├── ProductRepository.java       # Datenbankzugriff Produkte
│   └── UserRepository.java         # Datenbankzugriff Benutzer
├── model/
│   ├── Product.java                 # Produkt-Entity
│   └── User.java                   # User-Entity
└── dt/
    ├── RegisterRequest.java         # DTO für Registrierung
    ├── LoginRequest.java            # DTO für Login
    ├── ProduktDTO.java              # DTO für Produkte
    └── UserDTO.java                 # DTO für Benutzer

src/main/resources/
├── templates/                       # Thymeleaf HTML-Templates
│   ├── Login.html
│   ├── Register.html
│   ├── inventory.html
│   └── adminDashboard.html
├── db/migration/                    # Flyway SQL-Migrationen
│   ├── V1__create_users_table.sql
│   ├── V2__insert_admin_user.sql
│   ├── V3__create_products_table.sql
│   └── V4__insert_products_table.sql
└── application.properties
```

---

## Architektur & Qualität

Dieses Projekt folgt der **Layered Architecture** (Controller → Service → Repository) und legt großen Wert auf Softwarequalität durch **TDD (Red-Green-Refactor)**:
- **Entities:** Repräsentieren das Datenbankschema (JPA/Hibernate).
- **DTOs (Data Transfer Objects):** Werden für die Kommunikation über die REST-API verwendet. Dies entkoppelt die API-Schnittstelle von der internen Datenbankstruktur und verhindert die ungewollte Exponierung interner Felder.
- **Services:** Enthalten die Geschäftslogik (z. B. Bestandsberechnungen und Schwellenwert-Prüfungen).
- **Unit Tests:** Vollständige Absicherung der Geschäftslogik im Service-Layer (`ProductServiceTest`).
- **Integration Tests:** Validierung der REST-Schnittstellen und View-Controller mittels `MockMvc` (`ProductControllerTest`).
- **Clean Code:** Strikte Trennung von Belangen (Separation of Concerns) und sauberes Dependency Management.

---
