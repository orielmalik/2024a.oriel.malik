# Find Love & Matching

## Overview
**Find Love & Matching** is an integrative final course project designed to help users find their perfect match. The project leverages modern web technologies and reactive programming principles to create a responsive and scalable application.

## Team
- **Lecturer:** Eyal
- **Team Leader:** Oriel
- **Students:** Ahmad, Lior, Maayan, Avichay, Moshe

## Project Scope
- **Subject:** Find Love & Matching
- **Technologies:** Reactive MongoDB, Java, Webflux, Spring Boot, AssertJ, Gradle

## Features
- **Reactive MongoDB:** The application uses Reactive MongoDB to handle large-scale, real-time data operations efficiently.
- **Java with Webflux:** Webflux enables non-blocking, reactive programming in Java, allowing for scalable and responsive user interactions.
- **Spring Boot:** Utilized for building a robust and production-ready application with minimal configuration.
- **AssertJ:** Provides a fluent assertion API for testing, ensuring the application behaves as expected.
- **Gradle Build System:** The project is managed and built using Gradle, a flexible build automation tool.

## Project Structure
```bash
find-love-matching/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/findlove/
│   │   │       ├── config/         # Spring Boot configuration files
│   │   │       ├── controller/     # REST controllers
│   │   │       ├── model/          # Data models
│   │   │       ├── repository/     # Reactive MongoDB repositories
│   │   │       └── service/        # Business logic
│   │   └── resources/
│   │       ├── application.yml     # Spring Boot application configuration
│   │       └── static/             # Static resources (HTML, CSS, JS)
│   └── test/
│       └── java/
│           └── com/findlove/
│               └── tests/          # Unit and integration tests
│
├── build.gradle                   # Gradle build configuration
└── README.md                      # Project documentation
