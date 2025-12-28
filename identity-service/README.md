# Identity-service

Identity-service is a Spring Boot microservice that authenticates and authorizes application users with the help of stateless JWT-based security

## Installation

Requirements:

PostgreSQL  
Java 21  
Spring Boot 3  
JWT_SECRET_KEY as an environmental variable
```bash
git clone https://github.com/d-bahchevanov/UniFlow-Microservices/tree/main/identity-service
```

## Usage

## Endpoints

### Register a user
```
POST /api/users/register
Authorization: Public
```
#### Request Body
```
{
    "firstName": "John",
    "lastName" : "Doe",
    "username" : "john_doe",
    "email" : "johndoe@example.com",
    "password" : "123456789",
    "age" : 25,
    "phoneNumber" : "0895428355"
}
```
#### Response Body
```
{
    "email": "johndoe@example.com",
    "username": "john_doe"
}
```
### Login a user
``` 
POST /api/users/auth/login
Authorization: Public
```
#### Request Body
```
{
    "username" :  "john_doe",
    "password" : "123456789"
}
```
#### Response Body
```
{
      "token": "<JWT_TOKEN>"
}
```

### Get user by Id
```
GET /api/users/id/{id}
Authorization: ADMIN
```
### Get all users
```
GET /api/users/list
Authorization: ADMIN
```

## JWT
```
Subject: username

Claims:
userId,
role

Expiration: configurable

Issuer: uniflow-identity-service
```
## Roles
```
Available roles:
- ADMIN
- PROFESSOR
- STUDENT
- NON_ALIGNED (default role on registration)
```


## Planned Improvements
Refresh tokens

Role hierarchy

Token revocation

Single-role per user
