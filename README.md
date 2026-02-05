🚀 Product Catalog API – Role-Based Access Control (RBAC)
==========================================================

📌 Overview
--------------

This project implements a secure Product Catalog REST API using Spring Boot and Spring Security, 
featuring JWT-based authentication and role-based authorization as required in Module 5 – Assignment Set 2.

The application follows stateless security principles, enforces strict access control, 
and adheres to industry best practices for password handling, JWT management, and security configuration.
The design is clean, readable, and production-ready.

✨ Features Implemented
--------------------------

🔐 1. Authentication (Form Login + JWT)
****************************************

User authentication via Spring Security form login

On successful login:

A JWT token is issued
Token contains username and roles
Passwords are securely encoded using PasswordEncoder (BCrypt)

🪪 2. JWT Handling
-------------------

JWT is required for all secured APIs

JWT is:
Issued during login
Validated on every protected request

A custom JWT filter:
Extracts the token from Authorization: Bearer <token>
Validates token signature and expiration
Sets authentication in the SecurityContext

🛂 3. Role-Based Authorization Rules
--------------------------------------

API Access	Role Required
View products	Public (No authentication required)
Add product	ROLE_ADMIN
Update product	ROLE_ADMIN
Delete product	ROLE_ADMIN

Authorization is enforced using Spring Security configuration, not controller-level logic.

🚫 4. Access Control Enforcement
----------------------------------

Requests to secured APIs without a valid JWT are blocked

Unauthorized access returns proper HTTP status codes:
401 Unauthorized – Missing or invalid JWT
403 Forbidden – Insufficient role permissions

🧱 Security Configuration
---------------------------

Stateless API design (no HTTP session usage after login)

CSRF disabled for REST APIs

JWT filter added before UsernamePasswordAuthenticationFilter
Readable and well-commented security configuration class
Uses Spring Security defaults (no custom crypto logic)

🧾 Code Deliverables

  Security configuration class
  
  JWT utility and filter
  
  Role-based authorization rules
  
  Secure password encoding
  
  Stateless authentication setup


🔒 Security Best Practices Followed
--------------------------------------

 Passwords encoded using PasswordEncoder
 
 No hardcoded credentials
 
 JWT secret excluded from version control
 
 Stateless REST API design
 
 No custom encryption or hashing logic
 
 Clear separation of authentication and authorization


✅ Assignment Compliance Checklist
-------------------------------------

  Form-based login using Spring Security
  
  JWT issued on successful login
  
  JWT validated for protected APIs
  
  Role-based access control implemented
  
  Unauthorized access prevented
  
  Security pitfalls identified and addressed
  
  Readable and well-documented configuration

🏁 Conclusion
==============

This project demonstrates a secure, stateless Product Catalog API with JWT authentication and role-based authorization,
fully compliant with Module 5 – Assignment Set 2 requirements.
