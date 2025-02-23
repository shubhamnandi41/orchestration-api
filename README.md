****Orchestration API - User Management****
----------------------------------------------
Overview
--------------------------------------------
This Spring Boot application orchestrates user data retrieval and provides RESTful APIs for searching and retrieving user details. It fetches user data from https://dummyjson.com/users and stores it in an in-memory H2 database.
-----------------------------------------------
**Features**
---------------------------------------------
✅ Load user data from an external API into H2 database.

✅ RESTful endpoints for searching and retrieving user details.

✅ Free-text search for first name, last name, and SSN.

✅ Optimized and resilient external API calls.

✅ Clean code practices: modularization, logging, exception handling, environment layering, validations.

✅ API documentation using Swagger/OpenAPI.

------------------------------------------------------------------
**Tech Stack**
-----------------------------------
Backend: Java 17, Spring Boot, Spring Web, Spring Data JPA

Database: H2 (in-memory)

Build Tool: Maven

Testing: JUnit, Mockito

API Documentation: Swagger (Springdoc OpenAPI)

------------------------------------------------------------
**Prerequisites**
--------------------------------------------------------
☑️ Java 17 or later

☑️ Maven 3+

☑️ IntelliJ IDEA or any preferred IDE

---------------------------------------------------------------
**Setup & Installation**
----------------------------------------------------------
**Clone the Repository**

	git clone https://github.com/shubhamnandi41/orchestration-api.git
	
	cd orchestration-api

**Build the Application**

	mvn clean install

**Run the Application**

	mvn spring-boot:run

The application will start at http://localhost:8080

----------------------------------------------------------------
📜 Swagger Documentation
-------------------------------------------------------------------
After running the application, visit:

	http://localhost:8080/swagger-ui.html
 
------------------------------------------------------------
🧪 **Running Tests**
------------------------------------------------------------------
	mvn test

--------------------------------------------------------
📜 License
----------------------------------------------------
This project is licensed under the MIT License.

-----------------------------------------------
Author
-----------------------------------------------
Name: Shubham Nandi

LinkedIn: https://www.linkedin.com/in/shubhamnandi41/

Github: https://www.github.com/shubhamnandi41


