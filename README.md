# 🎯 Internship Tracker — Backend API

A RESTful backend API for tracking internship and job applications, built with **Spring Boot** and **Java**. Supports full CRUD operations for managing job applications across multiple stages of the hiring pipeline, including resume upload, viewing, downloading, and deletion — with resumes stored as binary (BLOB) data directly in a MySQL cloud database.

---

## 🚀 Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17+ |
| Framework | Spring Boot |
| ORM | Spring Data JPA |
| Database | MySQL / PostgreSQL |
| Build Tool | Maven |
| Containerization | Docker |
| API Style | REST |

---

## 📁 Project Structure

```
backend-JobTracker/
├── src/
│   └── main/
│       ├── java/com/Backend/JobTracker/
│       │   ├── Config/          # CORS and Web Configuration
│       │   ├── Controller/      # REST API Controllers
│       │   ├── DTO/             # Data Transfer Objects
│       │   ├── Entity/          # JPA Entity Classes
│       │   ├── Exception/       # Custom Exception Handling
│       │   ├── Repository/      # JPA Repositories
│       │   └── Service/         # Business Logic Layer
│       └── resources/
│           └── application.properties
├── Dockerfile
├── pom.xml
└── mvnw
```

---

## 📌 API Endpoints

| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/jobsDTO` | Get all job applications |
| `GET` | `/jobsDTO/{id}` | Get a specific job application |
| `POST` | `/jobs` | Add a new job application |
| `PUT` | `/jobs/{id}` | Update a job application |
| `DELETE` | `/jobs/{id}` | Delete a job application |
| `GET` | `/status` | Filter jobs by status |

### 📄 Resume Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/resume/view/{filename}` | View resume in browser (returns PDF as `application/pdf`) |
| `GET` | `/resume/download/{filename}` | Download resume file (returns PDF as file attachment) |
| `PATCH` | `/DelResumeUrl/{id}` | Delete resume — clears filename and file bytes from the job record in DB |

> **How resume storage works:** Resumes are stored as binary `BLOB` data (raw file bytes) directly in the MySQL cloud database. When `/resume/view/{filename}` or `/resume/download/{filename}` is called, the bytes are fetched from the DB and returned to the browser as `application/pdf` — no file system or external storage (S3/Cloudinary) is involved.

---

## ⚙️ Setup & Run Locally

### Prerequisites
- Java 17+
- Maven
- MySQL or PostgreSQL running locally
- Docker (optional)

### 1. Clone the repository
```bash
git clone https://github.com/PrasannaK-27/Internshiptracker-Backend-Api.git
cd Internshiptracker-Backend-Api
```

### 2. Configure the database
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jobtracker
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build and Run
```bash
./mvnw spring-boot:run
```
The API will start at `http://localhost:8080`

### 4. Run with Docker
```bash
docker build -t jobtracker-backend .
docker run -p 8080:8080 jobtracker-backend
```

---

## 🌐 CORS Configuration

The backend is configured to accept requests from the frontend origin. See `Config/WebConfig.java` for allowed origins.

---

## 🔗 Related Repository

- **Frontend**: [https://github.com/PrasannaK-27/InternShiptracker-Frontend.git])
