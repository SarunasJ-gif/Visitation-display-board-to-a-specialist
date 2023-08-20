# Visitation display board to a specialist

---

## Table of Contents

- [Description](#Description)
- [Prerequisites](#Prerequisites)
- [Technologies](#Technologies)
- [Configuration](#Configuration)
- [Dependencies](#Dependencies)
- [Project Structure](#Project-Structure)
- [Getting Started](#Getting-Started)
- [API Endpoints](#API-Endpoints)

---

## Description

Screens with serial numbers can be seen in customer service departments (bank, outpatient
clinic, post office, etc.). The incoming customer prints out the number and the display shows the
waiting line and the customers place in it. To avoid serial numbers printed on paper, a system is
needed that could allow the customer to book a visit time using a website. After booking the
time of the visit, the customer could see the waiting line and their respected place in it. The
queue is displayed on the service department screens. The customer can see how much time
he has left before the meeting according to the reservation code.
Customer - a user whose purpose is to visit a specialist.
A specialist - a user whose purpose is to serve a customer.
The customer does not need to log in to the system. Login to the system is required for the specialist.



---

## Prerequisites

- Java Development Kit (JDK) 8 or later
- Gradle build tool
- Any required IDE (e.g., IntelliJ IDEA, Eclipse, etc.)

---

## Technologies

- Java 
- Spring Boot
- H2 database

---

## Configuration

- src/main/resources/application.properties: Contains application-specific configurations

---

## Dependencies

- The project includes the necessary dependencies for building a Java Spring application. You can find the list of
dependencies and their versions in the build.gradle file.
- Added dependencies for using Swagger ui when testing endpoints. localhost:9090/swagger-ui/index.html

---

## Project Structure

The project follows the standard Java Spring project structure:
- src/main/java: Contains the Java source code of the application.
- src/main/resources: Contains configuration files and resources.
- src/test: Contains the unit tests for the application.

---

## Getting Started

1. Clone the repository to your local machine:
-      $ git clone <repository_url>
2. Navigate to the project directory:
-      $ cd <project_directory>
3. Build the application using Gradle:
-      $ gradle build
4. There are a few ways how you can run the starter application:
    * Right click on _src/main/java/com/sarunas/Visma/meeting/application/VismaMeetingApplication.java_ and select _Run_
    * Use `gradlew bootRun` to run the starter application.

---

## API Endpoints

The application provides the following REST API endpoints:

1. Create a new visitation:
- Endpoint: `POST /client/board/{specialistId}`
- Description: the endpoint is intended for the customer to create a new appointment with a specialist
  whose id (specialistId) is specified. The visit number and time are generated. No need to authenticate.


- Response: Status: 200 OK
- Body:

```
{
  "id": 1,
  "visitNumber": 1,
  "time": "2023-08-20 14:55:10",
  "isActive": false,
  "isFinished": false,
  "specialistFirstName": "Sarunas",
  "specialistLastName": "Jurevicius"
}
```

2. Check the visit time:
- Description: The customer can check the time of his visit. A visit id is required. No need to authenticate.
- Endpoint: `GET /client/board/visit-time/{id}`
- Response: Status: 200 OK
-Body:

```
{
  "visitationTime": "2023-08-20 14:55:10"
}
```

3. Check how much time is left until the visit:
- Description: The customer can check how much time is left until the visit. Visit ID is required.
  No authentication required.
- Endpoint: `GET /client/board/remaining-time/{id}`

- Response: Status: 200 OK
- Body:

```
{
  "remainingTime": "0 days, 0 hours, 13 minutes, 47 seconds"
}
```

4. You can view seven upcoming visits:
- Description: No authentication required.
- Endpoint: `GET /client/board/seven-next-visits`
- Response: Status: 200 OK
- Body:

```
[
  {
    "id": 2,
    "visitNumber": 2,
    "time": "2023-08-20 15:10:10",
    "isActive": false,
    "isFinished": false,
    "specialistFirstName": "Sarunas",
    "specialistLastName": "Jurevicius"
  },
  {
    "id": 3,
    "visitNumber": 3,
    "time": "2023-08-20 15:25:10",
    "isActive": false,
    "isFinished": false,
    "specialistFirstName": "Sarunas",
    "specialistLastName": "Jurevicius"
  },
  {
    "id": 4,
    "visitNumber": 4,
    "time": "2023-08-20 15:40:10",
    "isActive": false,
    "isFinished": false,
    "specialistFirstName": "Sarunas",
    "specialistLastName": "Jurevicius"
  },
  {
    "id": 5,
    "visitNumber": 5,
    "time": "2023-08-20 15:55:10",
    "isActive": false,
    "isFinished": false,
    "specialistFirstName": "Sarunas",
    "specialistLastName": "Jurevicius"
  },
  {
    "id": 6,
    "visitNumber": 6,
    "time": "2023-08-20 16:10:10",
    "isActive": false,
    "isFinished": false,
    "specialistFirstName": "Sarunas",
    "specialistLastName": "Jurevicius"
  },
  {
    "id": 7,
    "visitNumber": 7,
    "time": "2023-08-20 16:25:10",
    "isActive": false,
    "isFinished": false,
    "specialistFirstName": "Sarunas",
    "specialistLastName": "Jurevicius"
  },
  {
    "id": 8,
    "visitNumber": 8,
    "time": "2023-08-20 16:40:10",
    "isActive": false,
    "isFinished": false,
    "specialistFirstName": "Sarunas",
    "specialistLastName": "Jurevicius"
  }
]
```
5. You can view active visits:
- Description: No authentication required.
- Endpoint: `GET /client/board/active-visits`
- Response: Status: 200 OK
- Body:

```
[
  {
    "id": 1,
    "visitNumber": 1,
    "time": "2023-08-20 14:55:10",
    "isActive": true,
    "isFinished": false,
    "specialistFirstName": "Sarunas",
    "specialistLastName": "Jurevicius"
  }
]
```
6. You can cancel your visit.:
- Description:
  It is possible to cancel a visit, the customer must provide the visit id. No authentication required.
- Endpoint: `DELETE /client/board/{id}`
- Response: Status: 200 OK


7. You can add a new specialist:
- Description: You can add a new specialist to the system.
- Endpoint: `POST /specialist/add`
- Request body:

```
{
  "username": "Jonas",
  "password": "password",
  "roles": [
    "SPECIALIST"
  ],
  "email": "jonas@mail.com",
  "firstName": "Jonas",
  "lastName": "Baptistas"
}
```

- Response: Status: 200 OK
- Body:

```
{
  "id": 2,
  "username": "Jonas",
  "roles": [
    "SPECIALIST"
  ],
  "email": "jonas@mail.com",
  "firstName": "Jonas",
  "lastName": "Baptistas",
  "clients": [],
  "isBusy": false
}
```

8. Login to the system:
- Description: Connecting to the system as a specialist or administrator.
  One default administrator has been created, username: "SarunasJ", password: "password".
- Endpoint: `POST /sesion`
- Request body:

```
{
  "username": "SarunasJ",
  "password": "password"
}
```

- Response: Status: 200 OK
- Body:

```
{
  "username": "SarunasJ"
}
```

9. Update of specialist data:
- Description: The specialist has the opportunity to update his profile.
  Authentication is required.
- Endpoint: `PUT /specialist`
- Request body:

```
{
  "username": "Sarunas",
  "password": "password",
  "roles": [
    "ADMIN"
  ],
  "email": "sarunas@mail.com",
  "firstName": "Sarunas",
  "lastName": "Jurevicius"
}
```

- Response: Status: 200 OK
- Body:

```
{
  "id": 1,
  "username": "Sarunas",
  "roles": [
    "ADMIN"
  ],
  "email": "sarunas@mail.com",
  "firstName": "Sarunas",
  "lastName": "Jurevicius",
  "clients": [
    {
      "id": 1,
      "visitNumber": 1,
      "time": "2023-08-20 14:55:10",
      "isActive": true,
      "isFinished": false,
      "specialistFirstName": "Sarunas",
      "specialistLastName": "Jurevicius"
    }
    ],
      "isBusy": null
}
```

10. Update of specialist data:
- Description:
  The administrator has the ability to update any specialist
  profiles by specialist id. Authentication is required.
- Endpoint: `PUT /specialist/{id}`
- Request body:

```
{
  "username": "Jonas",
  "password": "password",
  "roles": [
    "SPECIALIST"
  ],
  "email": "jonasB@mail.com",
  "firstName": "Jonas",
  "lastName": "Baptistas"
}
```

- Response: Status: 200 OK
- Body:

```
{
  "id": 2,
  "username": "Jonas",
  "roles": [
    "SPECIALIST"
  ],
  "email": "jonasB@mail.com",
  "firstName": "Jonas",
  "lastName": "Baptistas",
  "clients": [],
  "isBusy": null
}
```

11. Get a list of all specialists:
- Description: The administrator has the opportunity to view the list of all specialists.
  Authentication is required. Only administrators have access to the endpoint.
- Endpoint: `GET /specialist`
- Response: Status: 200 OK
- Body:

```
[
  {
    "id": 1,
    "username": "Sarunas",
    "roles": [
      "ADMIN"
    ],
    "email": "sarunas@mail.com",
    "firstName": "Sarunas",
    "lastName": "Jurevicius",
    "clients": [
      {
        "id": 1,
        "visitNumber": 1,
        "time": "2023-08-20 14:55:10",
        "isActive": true,
        "isFinished": false,
        "specialistFirstName": "Sarunas",
        "specialistLastName": "Jurevicius"
      }
      ],
          "isBusy": false
  },
  {
    "id": 2,
    "username": "Jonas",
    "roles": [
      "SPECIALIST"
    ],
    "email": "jonasB@mail.com",
    "firstName": "Jonas",
    "lastName": "Baptistas",
    "clients": [],
    "isBusy": false
  }
]
```

12. Get specialist by id:
- Description: The administrator has the opportunity to get specialist by id.
  Authentication is required. Only administrators have access to the endpoint.
- Endpoint: `GET /specialist/{id}`
- Response: Status: 200 OK
- Body:

```

  {
    "id": 2,
    "username": "Jonas",
    "roles": [
      "SPECIALIST"
    ],
    "email": "jonasB@mail.com",
    "firstName": "Jonas",
    "lastName": "Baptistas",
    "clients": [],
    "isBusy": false
  }
```

13. Delete specialist by id:
- Description: The administrator has the opportunity to delete specialist by specialist id.
  Authentication is required. Only administrators have access to the endpoint.
- Endpoint: `DELETE /specialist/{id}`
- Response: Status: 200 OK

14. Get visitation by id:
- Description: The specialist can view the visit assigned to him by calling him by the visit id.
  Authentication is required.
- Endpoint: `GET /visitation/specialist/get-visitation/{id}`
- Response: Status: 200 OK
- Body:

```
{
  "id": 1,
  "visitNumber": 1,
  "time": "2023-08-20 16:06:41",
  "isActive": false,
  "isFinished": false,
  "specialistFirstName": "Sarunas",
  "specialistLastName": "Jurevicius"
}
```

15. Get next visitation:
- Description: The specialist can see the next visit assigned to him. Authentication is required.
- Endpoint: `GET /visitation/specialist/next-visitation`
- Response: Status: 200 OK
- Body:

```
{
  "id": 2,
  "visitNumber": 2,
  "time": "2023-08-20 16:21:41",
  "isActive": false,
  "isFinished": false,
  "specialistFirstName": "Sarunas",
  "specialistLastName": "Jurevicius"
}
```

16. Mark that the visit has started:
- Description: The specialist indicates that the visit has begun.
  If the specialist already has  customer, he cannot activate another visit at the same time.
  Authentication is required.
- Endpoint: `PATCH /visitation/specialist/activate-visitation/{id}`
- Response: Status: 200 OK
- Body:

```
{
  "id": 1,
  "visitNumber": 1,
  "time": "2023-08-20 16:06:41",
  "isActive": true,
  "isFinished": false,
  "specialistFirstName": "Sarunas",
  "specialistLastName": "Jurevicius"
}
```

17. Mark that the visit has ended:
- Description: The specialist marks the end of the visit. Before that, the visit had to be marked as active.
  Authentication is required.
- Endpoint: `PATCH /visitation/specialist/finish-visitation/{id}`
- Response: Status: 200 OK
- Body:

```
{
  "id": 1,
  "visitNumber": 1,
  "time": "2023-08-20 16:06:41",
  "isActive": false,
  "isFinished": true,
  "specialistFirstName": "Sarunas",
  "specialistLastName": "Jurevicius"
}
```

18. Cancel visit:
- Description: The specialist has the option to cancel a visit by visit id.
  Authentication is required.
- Endpoint: `DELETE /visitation/specialist/delete-visitation/{id}`
- Response: Status: 200 OK


19. Delete all expired visits:
- Description: The specialist has the possibility to delete all the visits assigned to him from the database.
  Authentication is required.
- Endpoint: `DELETE /visitation/specialist/delete-all-expired-visits`
- Response: Status: 200 OK


---