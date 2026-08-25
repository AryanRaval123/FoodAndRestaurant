# Food & Restaurant App
 
A microservices-based food ordering platform built with **Spring Boot** and **Spring Cloud**. It supports three types of users — customers, restaurant owners, and admins — and covers the full flow from registration to placing an order and paying for it.
 
## Services
 
| Service | Port | What it does |
|---|---|---|
| **config-server** | 8888 | Central place for shared configuration, pulled from a Git repo |
| **discovery-service** | 8761 | Eureka server — lets services find each other by name instead of hardcoded URLs |
| **api-gateway** | — | Single entry point for all requests; checks JWTs and routes traffic to the right service |
| **auth-service** | 8081 | Handles registration, login, and issuing JWTs |
| **menu-service** | — | Restaurant profiles and menu items |
| **order-service** | — | Placing orders and tracking their status |
| **payment-service** | — | Processing payments for an order |
 
## How it fits together
 
```
Client
  │
  ▼
API Gateway  ──(validates JWT, adds X-User-Id / X-User-Role headers)
  │
  ├──▶ auth-service
  ├──▶ menu-service
  ├──▶ order-service ──▶ menu-service (check items)
  │                  └─▶ payment-service (charge order)
  └──▶ payment-service
```
 
All services register with **discovery-service** (Eureka) and fetch shared settings from **config-server**. The **order-service** talks to menu-service and payment-service directly using Feign clients (no need to know their exact host/port).
 
## What I've implemented
 
### Authentication & authorization
- Register and log in (`/api/auth/register`, `/api/auth/login`)
- Passwords are hashed; login returns a JWT
- Three roles: `CUSTOMER`, `RESTAURANT_OWNER`, `ADMIN`
- The API Gateway validates every request's JWT before it reaches a service, and forwards the user's identity as `X-User-Id` / `X-User-Role` headers — so downstream services trust the gateway instead of re-checking tokens themselves
### Restaurants & menus
- Restaurant owners can create a restaurant profile
- New restaurants need **admin approval** before they're public
- Owners can add, update, and delete their own menu items
- Anyone can browse approved restaurants and view a restaurant's menu
### Orders
- Customers place orders for items from a restaurant
- Restaurant owners can view and update the status of orders for their restaurant
- Order status moves through: `PLACED → CONFIRMED → PREPARING → OUT_FOR_DELIVERY → DELIVERED` (or `CANCELLED`)
### Payments
- Order placement triggers a payment request to the payment-service
- Payment status: `PENDING`, `SUCCESS`, `FAILED`, `REFUNDED`
### Cross-cutting
- Each service has a global exception handler for consistent error responses
- Each service registers itself with Eureka and pulls config from config-server
- Input validation on all request DTOs (`@Valid`)
## Tech stack
 
- **Java / Spring Boot**
- **Spring Cloud Gateway** — API gateway & routing
- **Spring Cloud Config** — centralized configuration
- **Netflix Eureka** — service discovery
- **Spring Data JPA + PostgreSQL** — persistence
- **Spring Security + JJWT** — authentication
- **OpenFeign** — service-to-service calls
- **Lombok** — less boilerplate
## Running it locally
 
Start the services in this order so each one can register/find what it depends on:
 
1. `config-server` (needs to be up first — everything else reads config from it)
2. `discovery-service`
3. `auth-service`, `menu-service`, `order-service`, `payment-service`
4. `api-gateway`

Each service is a standard Maven project:
 
```bash
cd <service-folder>
./mvnw spring-boot:run
```
