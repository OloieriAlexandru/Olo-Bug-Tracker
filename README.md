
# Olo Bug Tracker

## Back-end

### Used technologies:

 - Spring Boot
 - Spring Data JPA
 - Hibernate
 - Postgres Database
 - JUnit + Mockito

## Front-end

### Used technologies/ libraries:

 - Angular 9
 - Materialize (https://materializecss.com/)

## CI/CD

Github Actions. When any changes are commited to /WebApp or /WebServer directories, a pipeline is executed and the following things are done:

    - The unit tests are run (only for the server app)
    - The application is built
    - A docker container is built
    - The container is push to Heroku Container Registry
    - The container is released and the changes are immediately visible
