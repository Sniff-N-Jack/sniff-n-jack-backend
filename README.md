# About the project
This is the backend API for our SOEN342 project: Sniff'N'Jack<br>

# Team members
Alexandre Vallières - 40157223<br>
Jackson Amirthalingam - 40249183

# Running the project
Make sure you have [Docker](https://docs.docker.com/get-started/get-docker/) installed, as the MySQL is built to run on in a container.<br>
This project uses [Apache Maven](https://maven.apache.org/download.cgi) 3.9.9 and [Java JDK](https://www.oracle.com/ca-en/java/technologies/downloads/) 23.

## Running with an IDE
Simply run the application; the compiler will make sure to build the Docker container for you.

## Running from command line
```
docker compose up -d
mvn spring-boot:run
```

