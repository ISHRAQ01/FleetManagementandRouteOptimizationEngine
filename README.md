# 🚚 Fleet Management System with Route Optimization

A comprehensive **Fleet Management System** built with Spring Boot 3, featuring vehicle/driver management, delivery task tracking, JWT authentication, and intelligent route optimization using the Nearest Neighbor algorithm.

-
## 📋 Table of Contents
--
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
- [Docker Setup](#docker-setup)
- [Configuration](#configuration)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Screenshots](#screenshots)
- [Future Enhancements](#future-enhancements)
- [Author](#author)

---

## ✨ Features

### Core Features
- ✅ **Vehicle Management** - CRUD operations for fleet vehicles
- ✅ **Driver Management** - CRUD operations with availability tracking
- ✅ **Delivery Task Management** - Create, assign, and track deliveries
- ✅ **Delivery State Machine** - UNASSIGNED → DISPATCHED → IN_TRANSIT → DELIVERED
- ✅ **JWT Authentication** - Secure API access with role-based authorization
- ✅ **Route Optimization** - Nearest Neighbor algorithm for optimal delivery sequence

### Advanced Features
- 🗺️ **External API Integration** - OSRM (Open Source Routing Machine) for distance calculation
- 📊 **Manifest Generation** - Auto-generate delivery manifests with distance/time estimates
- 🐳 **Docker Containerization** - Run anywhere with Docker & Docker Compose
- 📚 **Swagger Documentation** - Auto-generated interactive API docs
- 🛡️ **Role-Based Access** - ADMIN and OPERATOR roles with different permissions
- ⚡ **Global Exception Handling** - Consistent error responses across all APIs

---

## 🛠️ Technology Stack

| Category | Technology |
|----------|------------|
| **Backend Framework** | Spring Boot 3.1.12 |
| **Java Version** | Java 17 |
| **Database** | MySQL 8.0 |
| **ORM** | Spring Data JPA / Hibernate |
| **Security** | Spring Security + JWT |
| **External API** | OSRM (Open Source Routing Machine) |
| **API Documentation** | Swagger / OpenAPI 3.0 |
| **Containerization** | Docker & Docker Compose |
| **Build Tool** | Maven |
| **Testing** | JUnit 5 |

---

## 🏗️ Architecture

---

## 📡 API Endpoints

### 🔐 Authentication APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT token |

### 🚗 Vehicle APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/vehicles` | Create new vehicle |
| GET | `/api/vehicles` | Get all vehicles |
| GET | `/api/vehicles/{id}` | Get vehicle by ID |
| PUT | `/api/vehicles/{id}` | Update vehicle |
| DELETE | `/api/vehicles/{id}` | Delete vehicle |

### 👨‍✈️ Driver APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/drivers` | Create new driver |
| GET | `/api/drivers` | Get all drivers |
| GET | `/api/drivers/{id}` | Get driver by ID |
| PUT | `/api/drivers/{id}` | Update driver |
| DELETE | `/api/drivers/{id}` | Delete driver |

### 📦 Delivery Task APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/delivery-tasks` | Create delivery task |
| GET | `/api/delivery-tasks` | Get all tasks |
| GET | `/api/delivery-tasks/{id}` | Get task by ID |
| PUT | `/api/delivery-tasks/{id}/assign` | Assign vehicle & driver |
| PATCH | `/api/delivery-tasks/{id}/status` | Update task status |
| GET | `/api/delivery-tasks/status/{status}` | Get tasks by status |
| DELETE | `/api/delivery-tasks/{id}` | Delete task |

### 🗺️ Route Optimization API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/routes/optimize` | Get optimized delivery sequence |

### 📄 Manifest API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/manifests/generate` | Generate delivery manifest |

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- MySQL 8.0
- Maven 3.8+
- Docker (optional, for containerized deployment)

### Installation

#### 1. Clone the repository

```bash`
git clone https://github.com/your-username/fleet-management.git
cd fleet-management

#### 2. Docker setup
1. Install Docker on your system.
2. Clone the Repository.
3 .Run command docker-compose up --build
