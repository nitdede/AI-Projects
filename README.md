# AI Demo

This is a Java Spring Boot application that demonstrates the use of the LangChain4j library for conversational retrieval from a PDF document.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 11 or higher
- Maven
- IntelliJ IDEA 2023.3.6 or any other IDE that supports Spring Boot

### Installing

1. Clone the repository
2. Open the project in your IDE
3. Run `mvn clean install` to build the project

## Running the Application

You can run the application from your IDE by running the `AIdemoApplication` class, or from the command line with Maven:

```bash
mvn spring-boot:run

## Usage

The application exposes a POST endpoint at `/getPdfChat` that accepts a message as request body and returns a response from the `ConversationalRetrievalChain`.

To use this endpoint, you can send a POST request with a JSON body containing your message. Here's an example using `curl`:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"message":"your message here"}' http://localhost:8080/getPdfChat
