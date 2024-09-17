# Pet Adoption App - Planning

## 1. App Purpose
This app is designed to centralize pet adoption listings from various non-profit organizations. 
It will allow for these organizations to post their pets for adoption and users to find them in a single location, streamlining the adoption process.

## 2. Tech Stack
- **Backend**: Spring Boot, Java
- **Database**: PostgreSQL
- **Cache**: Redis
- **Message Broker**: Kafka / RabbitMQ
- **Storage**: AWS S3 for documents and images
- **Deployment**: AWS
- **Auth0**: Authentication and Authorization
- **Notification Service**: Secondary app built for this purpose

## 3. Feature Outline

### Authentication and Authorization
#### Admin
- Login
  - Status: [ ]  - Not Started
- Create accounts for organizations
  - Status: [ ]  - Not Started
- Create new entries for species, breeds and colors
  - Status: [ ]  - Not Started
- Update organizations, species, breeds and colors
    - Status: [ ]  - Not Started
- Remove organizations, species, breeds and colors
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
- Register a user account
  - Status: [ ] Not Started
- Browse pets
  - Status: [ ] Not Started

### What can an organization do?
- Post single pet for adoption
  - Status: [X] Testing;
- Post multiple pets for adoption
  - Status: [X] Testing;
- Update owned pets information
  - Status: [X] Testing;
- Remove owned pets from adoption
  - Status: [X] Testing;
- View adoption requests for owned pets
  - Status: [ ] Not Started
- Review user information if they manifest interest in adopting an owned pet
  - Status: [ ] Not Started
- Create adoption forms for user interested in adopting a specific owned pet. The form is pre-filled with organization, pet and user information. Notify user.
  - Status: [ ] Not Started
- Accept / reject adoption requests. Notify user.
  - Status: [ ] Not Started

### What can a user do?
- Add pet to favorites list
  - Status: [X] Testing;
- View favorites list
  - Status: [ ] Not Started
- Remove pet from favorites list
  - Status: [ ] Not Started
- Manifest interest in adopting a specific pet. Notify organization.
  - Status: [ ] Not Started
- Fill out adoption form when requested. Notify organization.
  - Status: [ ] Not Started
- View all adoption requests pending
  - Status: [ ] Not Started
- View adoption requests history, accepted or rejected, with or without the respective adoption form.
  - Status: [ ] Not Started

### Pet Search and Filters (For Users, Guests, Organizations and Admins)
- Browse Pets by breed, age, etc.
  - Status: [X] Testing;

### Notification Service
- Communicate with external service to send emails and messages
  - Status: [ ] Not Started

## 4. Business Logic & Future Enhancements
- Potentially launch a real-world product with simplified functionality like redirecting users to organization websites.

