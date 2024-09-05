# Bookstore application
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

Running the server for the first time:
1. Clone the project from the GitHub repository: https://github.com/artemklishch/bookstore
2. Add ".env" file in the root of the project with values for variables as it is pointed in the ".env.example" file - if you didn't add it earlier
   You can use your values of variables in the '.env' file
3. Run the command "mvn clean package"
4. Run the container with the command "docker compose up --force-recreate --build"

Running the server next times:
1. If you don't have "target" directory run the command "mvn clean package"
2. If you don't have the ".env" file - add it with values for variables as it is pointed in the ".env.example" file
3. Run the container with the command "docker compose up --force-recreate --build"

Video instruction to run the app:
[![Watch the video](https://img.freepik.com/free-vector/isometric-cms-concept_23-2148807389.jpg)](https://www.loom.com/share/f32fb6dfe9624ae58157ee6732655667?sid=33b2152a-1f9e-49e9-a7a4-a5a36c0ddf12)

## Project features
This project is run with the Dockerfile and docker compose. 
The challenge was - to make docker settings in order to provide the ability to run the project on the every computer.
