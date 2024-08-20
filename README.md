# bookstore

Running the server for the first time:
1. Clone the project
2. Remove './target' directory if it exists with the command 'rm -rf target' (for the first running the server)
3. Build target directory with the command "mvn clean package" if you don't have the "target" directory 
4. Add ".env" file in the root of the project with values for variables as it is pointed in the ".env.example" file - if you didn't add it earlier
5. Run the server container with the command "docker compose up --force-recreate --build"

Running the server next:
1. If you don't have "target" directory run the command "mvn clean package"
2. If you don't have the ".env" file - add it with values for variables as it is pointed in the ".env.example" file
3. Run the server container with the command "docker compose up --force-recreate --build"
