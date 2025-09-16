# MultiGenesys_Booking System Assignment_Umesh Mali

A RESTful booking system built with **Spring Boot**, **MySQL**, and **JWT authentication**. This application allows users to register, authenticate, manage resources, and handle reservations.

---

## ⚙ Prerequisites
- Java 17 or above
- Maven 3.6 or above
- MySQL server running locally
- Postman (for API testing)

---

## 🟢 Setup Instructions

### 

```bash
1. Clone the repository
https://github.com/maliumesh1/MultiGenesys_Booking-System-Assignment_Umesh-Mali.git
cd MultiGenesys_Booking-System-Assignment_Umesh-Mali

2. Configure MySQL database
Create a database named bookingdb:
CREATE DATABASE bookingdb;

Update the src/main/resources/application.yml with your MySQL details:
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/bookingdb
    username: root
    password: XXXX
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

3. Build and Run
mvn clean install
mvn spring-boot:run
The application will run on: http://localhost:8080

🔑 Seed Users
Upon startup, the following users are seeded into the database automatically:
Username	   Password  	 Role
admin        admin123   ADMIN
user	       user123	  USER

Use these credentials to log in and test various API endpoints.

📚 API Documentation
Postman
Import the collection file located at:
docs/BookingSystem.postman_collection.json

🌐 Environment Variables
Alternatively, you can configure your environment using .env or directly edit application.yml:
DB_URL=jdbc:mysql://localhost:3306/bookingdb
DB_USERNAME=root
DB_PASSWORD=XXXX
JWT_SECRET=your_jwt_secret_key_UM

✅ Features
User registration and login with JWT token authentication
Resource management (CRUD operations)
Reservation management with statuses
Role-based access control (ADMIN vs USER)
Global exception handling
Seed users for easy testing

⚙ Assumptions & Trade-offs
Assumptions
Local MySQL setup is required
JWT-based authentication is sufficient for API security
Postman is used for API testing
The project is a development prototype

Trade-offs
No caching or scalability considerations
Minimal unit tests
No CI/CD pipeline configured
Exception handling could be extended
 
mvn test
The test MultiGenesysBookingSystemAssignmentUmeshMaliApplicationTests ensures basic application context loading.

📥 Postman Collection
You can download and import the Postman collection to test APIs quickly. It covers:
Authentication (/auth/login, /auth/register)
Resource CRUD endpoints
Reservation CRUD endpoints

📌 Notes
Java 17+ is recommended
Maven is used for dependency management
Use the provided credentials to test different roles
 
