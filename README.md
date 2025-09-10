# ToDo Application Microservices

This is a full-stack ToDo application built with a React frontend, Spring Boot microservices for backend logic, and MySQL for data persistence. The entire application is containerized using Docker Compose.

## Features

*   User authentication (Login/Registration)
*   Create, read, update, and delete ToDo items
*   View statistics related to ToDo items
*   Containerized development environment with Docker Compose

## Technologies Used

**Frontend:**
*   React
*   JavaScript
*   HTML/CSS

**Backend (Microservices):**
*   Spring Boot
*   Java 17
*   Spring Data JPA
*   MySQL Connector/J
*   Lombok
*   MapStruct
*   JWT (for authentication)

**Database:**
*   MySQL 8.0

**Containerization:**
*   Docker
*   Docker Compose

## Prerequisites

Before you begin, ensure you have met the following requirements:
*   Java Development Kit (JDK) 17 or higher
*   Node.js and npm (Node Package Manager)
*   Docker Desktop (includes Docker Engine and Docker Compose)
*   Git

## Getting Started

Follow these steps to get your development environment running.

### 1. Clone the Repository

```bash
git clone <repository_url>
cd ToDoApplication
```

### 2. Docker Setup

Navigate to the root directory of the cloned project (`ToDoApplication`).

First, ensure all previous Docker containers are down and removed, and build the images:

```bash
docker-compose down
docker-compose up --build -d
```

If you encounter an error like "ports are not available" for port 3306, it means another process on your machine is using that port. You can change the MySQL host port in `docker-compose.yml` from `3306:3306` to `3307:3306` (or any other available port).

After the services are up, you might need to manually create the databases if they don't exist (this should be handled by `init.sql` but can sometimes fail on initial setup):

```bash
docker-compose exec mysql mysql -u root -proot -e "CREATE DATABASE IF NOT EXISTS mydb;"
docker-compose exec mysql mysql -u root -proot -e "CREATE DATABASE IF NOT EXISTS statdb;"
```

### 3. Running the Application

Once all Docker containers are up and running, you can access the application:

*   **Frontend:** Open your web browser and go to `http://localhost:3000`
*   **ToDo Microservice API:** `http://localhost:8383/todo-app`
*   **Statistics Microservice API:** `http://localhost:8484/stat`

## API Endpoints

(Detailed API documentation can be found within each microservice's codebase or via tools like Swagger/OpenAPI if integrated.)

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.
