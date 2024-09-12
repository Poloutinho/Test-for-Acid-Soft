# Book Management API

This project is a Spring Boot application that provides a RESTful API for managing books. It allows users to create, read, update, delete, and search for books.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [API Endpoints](#api-endpoints)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)

## Features

- Create a new book
- Retrieve all books with pagination
- Retrieve a book by its ID
- Update a book's details
- Delete a book
- Search for books based on various parameters

## Technologies

- Java 17
- Spring Boot 3.3.3
- Spring Data JPA
- H2 Database (in-memory)
- Lombok
- MapStruct
- JUnit 5 for testing

## API Endpoints

### Create a Book

*POST /api/books*

**Request Body:**
```json
{
    "title": "Book Title",
    "author": "Author Name",
    "publicationYear": "Year of publishing less or equal 2024",
    "genre": "Genre of book (optional)",
    "isbn": "ISBN Number thirteen digits"
}
```
### Get All Books

*GET /api/books*

### Get Book by ID

*GET /api/books/{id}*

### Update a Book

*PUT /api/books/{id}*

Request Body:

```json
{
    "title": "Updated Book Title",
    "author": "Updated Author Name",
    "publicationYear": "Updated year of publishing less or equal 2024",
    "genre": "Updated genre of book (optional)",
    "isbn": "Updated ISBN Number"
}
```
### Delete a Book

*DELETE /api/books/{id}*


### Search Books

*GET /api/books/search*

#### Query Parameters:

title (optional)

author (optional)

genre (optional)

## Installation
### Clone the repository:

*git clone https://github.com/yourusername/book-management-api.git*

Navigate to the project directory:

cd book-management-api
### Build the project using Maven:

*mvn clean install*

### Usage

To run the application, use the following command:

*mvn spring-boot:run*

The API will be available at http://localhost:8080/api/books.
