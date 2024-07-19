# Task requirement

- Create a simple REST API service. 
1. Create a service that communicates using REST API.
2. Service's job is to maintain a persistent data store of birds, their characteristics, and sightings.
3. At start-up, the service initializes persistent data storage.
4. Service supports two types of data: birds and bird sightings.
5. Service supports CRUD operations for birds and sightings.
6. Birds have the following attributes (Name,Color,Weight,Height)
7. Sightings have the following attributes (Bird,Location,Date-time)
8. Service supports listing all birds and sightings.
9. Service supports bird queries by name and color.
10. Service supports sighting queries by bird, location, and time interval.

- Other Requirements
1. Use the IDE of your choice
2. The project must be buildable using Maven
3. Use Java 11
4. Use Spring framework
5. Integrate with the open-source data store of your choice (relational or NoSQL, such as PostgreSQL or MongoDB)
6. Document your code
7. Provide simple API documentation
8. Provide instructions on how to:
8.1. Build your project
8.2. Run the datastore (ideally provide a docker-compose.yml file because it can easily be launched in Docker)
8.3. Run your software

- Evaluation
1. Adherence to functional requirements.
2. Ease of use.
3. Thread safety.
4. Error handling.
5. Adequate test coverage.
6. Organization and clarity of code.
7. Clarity of documentation

# Installing & Configuring MongoDB and JAVA 11.

1. Install MongoDB - https://www.mongodb.com/try/download/community downloading current version 7.0.12 (current).
Run downloaded file: mongodb-windows-x86_64-7.0.12-signed.msi
URI mongodb://localhost:27017

2. As above setup also installs Mongo Compass. You can create "birdservice" database.
Enter the database name as birdservice.
Enter a collection name, for example, birds (since MongoDB requires at least one collection to create a database).
(any collections are created automatically when putting data in them).
We will be using below URL:
spring.data.mongodb.uri=mongodb://localhost:27017/birdservice
(application name is: MongoDBCompass)

3. Install OpenJDK Java 11 - www.openlogic.com/openjdk-downloads
select: Java Version 11, OS Windows, Architecture x86_64-bit, JDK, msi
download and install: openlogic-openjdk-11.0.23+9-windows-x64.msi
Set JAVA_HOME11: c:\installed\jdk-11.0.23.9-hotspot
Set JAVA_HOME = %JAVA_HOME11%

4. Run the Application
Run MongoDB: mongod
Run the Spring Boot Application: mvn spring-boot:run

# Run method - BirdApplication.main()

# Useful commands
- for killing processes in windows
C:\WINDOWS\system32>netstat -ano | findstr :8080
TCP    0.0.0.0:8080           0.0.0.0:0              LISTENING       292

C:\WINDOWS\system32>taskkill /PID 292 /F
SUCCESS: The process with PID 292 has been terminated.

# Deliverables included
- A collection of few API(s) for both birds and sightings.
REST API birds and sightings.postman_collection.json

- API(s) are available as OPENAPI was integrated via this link
http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/
