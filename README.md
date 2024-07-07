# nxtspace-backend-service

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)

## Introduction
The service is a spring boot application which is implemented as a backend service to support student management system.

## Features
- Register Students
- Assigning courses to the students
- Payment schemas for the courses and etc.

## Prerequisites
- Java 11 or higher
- Maven 3.6.3 or higher
- MySQL

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/manuprabath0627/nxtspace-backend-service.git
   cd your-repo-name
   
2. Validate DB connectivity details.
3. Build the project:
   ```bash
   mvn clean package
4. Run the application:
   ```bash
   java -jar nspace.jar
   
## Usage
Endpoints will be exposed to the consumers and refer <B>Nxtspace.postman_collection.json</B> for available endpoints.
