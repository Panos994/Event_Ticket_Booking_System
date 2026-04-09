# Event Booking Ticket System

This project is an event ticket booking system built with **Spring Boot**, **PostgreSQL**, and **Docker** It supports user, event, ticket, venue, and order management, secured with **JWT** and **Spring Security 6**.

---

## Technologies

- Java17+ / Spring Boot 3+
- Spring Security 6 with JWT Authentication
- PostgreSQL database
- Docker & Docker Compose for easy setup and deployment
- Maven / Gradle for dependency management

---

## Core Services

| Service        | Description                                      |
|----------------|------------------------------------------------|
| `UserService`  | User management (registration, login, roles)   |
| `EventService` | Event management (create, update events)       |
| `TicketService`| Ticket management (issue, cancel tickets)      |
| `VenueService` | Venue management                                |
| `OrderService` | Ticket order management                          |

---

## Architecture

- RESTful API endpoints for all services
- JWT Authentication for secure access
- User roles (e.g., ADMIN, USER) with access control
- Service layer for business logic
- Repository layer with Spring Data JPA for PostgreSQL interaction

---

## Configuration & Running

### 1. PostgreSQL Setup with Docker

```bash
docker run --name my-postgres-ebmgdb -e POSTGRES_PASSWORDpostgres -e POSTGRES_DB=my_eventbooking_db -p 5450:5432 -d postgres
