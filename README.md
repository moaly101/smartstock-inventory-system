# SmartStock – Inventory Management System

Ein modernes Lagerverwaltungssystem mit automatisierten Schwellenwert-Warnungen. Dieses Projekt demonstriert die Anwendung einer **Schichten-Architektur** (Layered Architecture), konsequentes **Test-Driven Development (TDD)** und die Containerisierung einer Java-Backend-Anwendung.

##  Features

- **RESTful API:** Endpunkte zum Erstellen, Abrufen und Aktualisieren von Produkten.
- **Data Validation:** Strikte Validierung der Eingabedaten (z. B. keine negativen Bestände) via Jakarta Bean Validation.
- **Smart Alerting:** Logik zur Identifizierung von Beständen, die den Mindestschwellenwert unterschreiten (`needsRestock`).
- **Lager-Dashboard:** Eine Web-Oberfläche (Thymeleaf) zur Visualisierung des aktuellen Inventars.
- **Persistenz:** Vollständige Integration einer PostgreSQL-Datenbank.
- **Dockerized:** Einfaches Setup der gesamten Infrastruktur via Docker Compose.

##  Quick Links (Lokale Umgebung)

- **Lager-Dashboard:** `http://localhost:8080/inventory`
- **API Dokumentation (Swagger UI):** `http://localhost:8080/swagger-ui/index.html`

##  Architektur & Qualität

Dieses Projekt folgt der **Layered Architecture** (Controller → Service → Repository) und legt großen Wert auf Softwarequalität durch **TDD (Red-Green-Refactor)**:  


* **Entities:** Repräsentieren das Datenbankschema (JPA/Hibernate).
* **DTOs (Data Transfer Objects):** Werden für die Kommunikation über die REST-API verwendet. Dies entkoppelt die API-Schnittstelle von der internen Datenbankstruktur und verhindert die ungewollte Exponierung interner Felder.
* **Services:** Enthalten die Geschäftslogik (z. B. Bestandsberechnungen und Schwellenwert-Prüfungen).
- **Unit Tests:** Vollständige Absicherung der Geschäftslogik im Service-Layer (`ProductServiceTest`).
- **Integration Tests:** Validierung der REST-Schnittstellen und View-Controller mittels `MockMvc` (`ProductControllerTest`).
- **Clean Code:** Strikte Trennung von Belangen (Separation of Concerns) und sauberes Dependency Management.

##  Tech Stack

| Kategorie | Technologien |
|---|---|
| **Backend** | Java 21, Spring Boot 3.4, Spring Data JPA, Jakarta Validation |
| **Frontend** | Thymeleaf (HTML5 / CSS) |
| **Datenbank** | PostgreSQL (H2 für isolierte Tests) |
| **Dokumentation** | Swagger UI / OpenAPI 3 |
| **Infrastruktur** | Docker & Docker Compose |
| **Testing** | JUnit 5, Mockito, AssertJ |

## Installation & Start

### 1. Repository klonen
```bash
  git clone https://github.com/moaly101/smartstock-inventory-system.git
cd smartstock-inventory-system
```

### 2. System starten (Docker Compose)
```bash
  docker-compose up --build
```

### 3. Tests ausführen
```bash
  ./gradlew test
```

## ⌨️ API-Nutzung (Beispiel)

Bestand aktualisieren (z. B. Verkauf von 5 Einheiten):
```bash
  curl -X PUT "http://localhost:8080/api/products/1/stock?amount=-5"
```