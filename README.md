# OTA Note RESTful API

This project is a simple implementation of a RESTful API with in-memory database using Spring Boot, inspired by Uncle Bob's Clean Architecture principles.

The repository setup is very simple, no git flow constraint and CI/CD in place, I have mostly focused on trying to create a clean foundation in the hopes to scale this project one day.

## Getting Started

To get started with this project, ensure you have the following installed:

- Java Development Kit (JDK) ~21.0.2
- Gradle ^8.7
- Git ^2.33
- Intellij Idea 2024.1 (Ultimate Edition)
  - Enable experimental features

_I recommend using Intellij Idea 2024.1 (Ultimate Edition) for an optimal development experience._

## Running the Project

To run the project, simply import it into your preferred IDE and execute the main class. The application will start on the default port 8080.

```console
user@laptop:~$ git clone https://github.com/miko-codes-a-lot/ota_note_api.git
user@laptop:~$ cd ota_note_api
user@laptop:~/ota_note_api$ idea .
```

_Please do reach out to me if you have trouble setting up the project!_

### Swagger UI - running HTTP Requests

http://localhost:8080/swagger-ui/

![OpenAPI](https://gcdnb.pbrd.co/images/gn7v3g2HwSL6.png)

### cURL - running HTTP Requests

Creating a Note:

    curl -X 'POST' \
      'http://localhost:8080/api/notes/' \
      -H 'accept: */*' \
      -H 'Content-Type: application/json' \
      -d '{
      "title": "I am a todo!",
      "body": "I'\''m really surely positive I wont procrastinate"
    }'

Updating a Note:

    curl -X 'PUT' \
      'http://localhost:8080/api/notes/1' \
      -H 'accept: */*' \
      -H 'Content-Type: application/json' \
      -d '{
        "title": "I am a todo changes!!",
        "body": "I'\''m really surely positive I wont procrastinate"
    }'

Getting a Note:

    curl -X 'GET' \
      'http://localhost:8080/api/notes/1' \
      -H 'accept: */*'

Getting a list of Notes:

    curl -X 'GET' \
      'http://localhost:8080/api/notes/?query=&sortBy=title&page=0&pageSize=0' \
      -H 'accept: */*'

Delete a Note:

    curl -X 'DELETE' \
      'http://localhost:8080/api/notes/1' \
      -H 'accept: application/json'