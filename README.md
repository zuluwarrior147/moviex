# Moviex

Movie rental service for old schoolers 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to run the application

* Java 11
* Docker


### Running application


Build the project:

```
./gradlew build
```

Run the database in Docker:

```
docker run --rm --name pg-docker -e POSTGRES_PASSWORD=docker -e POSTGRES_DB=moviex 
-p 5432:5432 -v ~/docker/volumes/postgres:/var/lib/postgres/data postgres:10
```

Run the application:

```
./gradlew :configuration:bootRun
```

Application would be started on the default port 8080. 

## Running the tests

To run the tests execute the following command:

```
./gradlew test
```

### Technical notes

Since the first time I took a look at Domain Driven Design it was certainly unclear for me 
how to implement it in a real-life project. All the terms and approaches were too abstract and fuzzy, 
and they often differed from all the projects I worked on. The main problems I faced using so called 
'Database Driven Design' in typical Spring projects were:
* Where to locate domain logic?
* How to avoid code duplication by following the Law of Demeter?
* If I want to look for some feature implementation, where should I go first?
* How to change persistence/controller layer without modifying that ephemeral domain logic?

I wouldn't say that DDD is a magic pill for all these problems, and I'm certainly not an expert in this field, 
but by concentrating on use cases and separating logic into the central part of application allowed me
to develop other modules like persistence and web completely separately. Also, in conjunction with TDD approach, 
there was a clear understanding of how to implement concrete scenarios, and what to do the next.

I tried to develop this project according to high code standards, making this easily understandable and maintainable. 
Despite the lack of time I believe I achieved a certain degree of quality.

Here's some technical points, I'd like to mention:
* The main accent was made on development of domain logic and exposing API for movie rental. 
User registration/authentication can be added as a separate slice as the whole infrastructure is already set up.
* Separate rental is created for each movie to avoid complexity in case of split movies return. 
It's not visible for the end user though.
* Rental prices and bonus points are hardcoded into the code for initial simplicity 
(though at the end of the day, it would probably be easier to store information in the database, it wouldn't require
separate strategy for each movie type).
* For repository testing it was used very handy library called TestContainers.
* One of the pitfalls of separating application layers is entities creation for each of these layers, 
that could lead to entities mapping boilerplate. Luckily, Mapstruct library helps to avoid this problem. 


If I have a bit more time, I'd implement the following features:
* Swagger documentation 
* End-to-end testing by using Docker Compose
* Optimistic locks for modifying user's bonus points
* AOP logging
