### Bookstore application
This application is supposed to be used for selling books. 
Admin users have rights to uploads information about books and normal users can buy them.
Normal users can put chosen books into carts. 
Admin users also have rights to upload book categories, update and delete data about books and categories. 
Books can belong to the categories depending on the book subject.

## Technologies
The application was made with technologies:
1. Spring Boot
2. Spring Security
3. Spring Data JPA
4. Hibernate
5. Liquibase 
6. Json Web token 
7. Lombok 
8. Mapstruct
9. Test containers
10. Springdoc

## Controllers
There are controllers:
1. AuthenticationController. 
This controller provides the way of registration of user and signing in of registered user.
2. BookController. This controller handles CRUD operations with books.
3. CartController. This controller is responsible for CRUD operations with cart.
4. CategoryController. This controller handles CRUD operations with book categories.
5. OrderController. Responsible for CRUD operations with book orders.

## Launch the project locally
```mermaid
flowchart ID
A[Launch the application] --> B{Running the server for the first time:
1. Clone the project
2. Remove './target' directory if it exists with the command 'rm -rf target' (for the first running the server)
3. Build target directory with the command "mvn clean package" if you don't have the "target" directory 
4. Add ".env" file in the root of the project with values for variables as it is pointed in the ".env.example" file - if you didn't add it earlier
5. Run the server container with the command "docker compose up --force-recreate --build"}
A[Launch the application] --> C{Running the server next:
1. If you don't have "target" directory run the command "mvn clean package"
2. If you don't have the ".env" file - add it with values for variables as it is pointed in the ".env.example" file
3. Run the server container with the command "docker compose up --force-recreate --build"}
```

## Project features
This project is run with the Dockerfile and docker compose. 
The challenge was - to make docker settings in order to provide the ability to run the project on the every computer.
