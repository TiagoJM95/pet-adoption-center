# Pet Adoption App - Planning

## 1. App Purpose
This app is designed to centralize pet adoption listings from various non-profit organizations. 
It will allow for these organizations to post their pets for adoption and users to find them in a single location, streamlining the adoption process.

## 2. Tech Stack
- **Backend**: Spring Boot, Java
- **Database**: PostgreSQL
- **Cache**: Redis
- **Message Broker**: Kafka
- **Storage**: AWS S3 for documents and images
- **Deployment**: AWS
- **Auth0**: Authentication and Authorization
- **Email**: Send automatically emails (Microservice) node.js

## 3. Feature Outline

### Authentication and Authorization
#### Admin
- Have an account created for them
  - Status: [ ]  - Not Started
- Login
  - Status: [ ]  - Not Started
- Create accounts for organizations
  - Status: [ ]  - Not Started
- Update organization information
    - Status: [ ]  - Not Started
- Remove organizations
  - Status: [ ]  - Not Started
- Create new entries for species, breeds, colors
  - Status: [ ]  - Not Started
#### Organization
- Register account after invitation
  - Status: [ ] Not Started
- Login
  - Status: [ ] Not Started
- View account information
  - Status: [ ] Not Started
- Update account information
  - Status: [ ] Not Started
- Delete account
  - Status: [ ] Not Started
- Create/update/delete owned pets
  - Status: [ ] Not Started
- View adoption requests for owned pets
  - Status: [ ] Not Started
- Ask a user to fill out an adoption form
  - Status: [ ] Not Started
- Accept / reject adoption requests
  - Status: [ ] Not Started
- View pet adoption history of owned pets
  - Status: [ ] Not Started
- Update adoption form fields related to organization
  - Status: [ ] Not Started
#### User
- Register an account
  - Status: [ ] Not Started
- Login
    - Status: [ ] Not Started
- View account information
  - Status: [ ] Not Started
- Update account information
  - Status: [ ] Not Started
- Delete account
    - Status: [ ] Not Started
- Can add pets to favorites list and view list
  - Status: [ ] Not Started
- Manifest interest in adopting a pet
  - Status: [ ] Not Started
- Fill out an adoption form when requested
  - Status: [ ] Not Started 
- Update adoption form fields related to user
  - Status: [ ] Not Started
#### Guest
- Browse pets
  - Status: [ ] Not Started

### Pet Management (For Organizations)
- Post single pet for adoption
  - Status: [ ] Not Started
- Post multiple pets for adoption
  - Status: [ ] Not Started
- Update owned pets information
    - Status: [ ] Not Started
- Remove owned pets from adoption
  - Status: [ ] Not Started
- View adoption requests for owned pets
  - Status: [ ] Not Started
- Create adoption requests for a specific owned pet and with the information of the user requesting
  - Status: [ ] Not Started
- Accept adoption requests, send email/message to user and allow them to fill out adoption form
  - Status: [ ] Not Started
- Reject adoption requests and send email/message to user
  - Status: [ ] Not Started

### Pet Management (For Users)
- Add pet to favorites list
  - Status: [ ] Not Started
- View favorites list
  - Status: [ ] Not Started
- Remove pet from favorites list
  - Status: [ ] Not Started
- Manifest interest in adopting a specific pet, send email/message to organization
  - Status: [ ] Not Started
- Fill out adoption form when requested
  - Status: [ ] Not Started
- View all adoption requests pending
  - Status: [ ] Not Started
- View adoption requests history (accept or reject)
  - Status: [ ] Not Started
- View adoption form history
  - Status: [ ] Not Started

### Pet Search and Filters (For Users, Guests, Organizations and Admins)
- Browse Pets by breed, age, etc.
  - Status: [ ] Not Started

### Messaging Service
- Communicate with external service to send emails and messages
  - Status: [ ] Not Started

## 4. Business Logic & Future Enhancements
- Potentially launch a real-world product with simplified functionality like redirecting users to organization websites.
