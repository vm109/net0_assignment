## Title
- Interview assignment.
- Java version 21.
- mvn 3.9.9

## Helpful commands for running the project
- mvn clean dependency:purge-local-repository 
- mvn clean install
- mvn spring-boot:run

## Project structure
- Controllers (dto)
  -> Service (dto <-> entity)
    -> Repository (entity)
- Entities
  - are models
- Dtos
    - are data transfer objects
- Repositories
    - are talking objects of db