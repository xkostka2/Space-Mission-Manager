# Space-Mission-Manager
The project was created as part of FI MUNI course PA165 Enterprise applications in java
The project has Angular on the front-end with back-end REST API.

Detailed description of project available on wiki [here](https://github.com/xkostka2/Space-Mission-Manager/wiki).

## Running the app
In order to run the app in the base directory run `mvn clean install && cd rocket-mission-manager/ && mvn`
To log-in into the application-UI use 
 * for simple user email: john@gmail.com password: tralala123
 * for manager  email: boss@gmail.com password: bosspassword
 
When  the app is run it uses in-memory database therefore if you restart it you lose all your data be carefull :) 

REST API available at [http://localhost:8080/pa165/rest/](http://localhost:8080/pa165/rest/)

UI of the App available at [http://localhost:8080/pa165](http://localhost:8080/pa165)

The CURL commands for the tessting of rest are available in `curl_commands.txt`

Class diagram can be see in `class_diagram.png`

Use case diagram can be see in `Use Case Diagram.png`

The Swagger documentation of REST API is available at [http://localhost:8080/pa165/swagger-ui.html#/](http://localhost:8080/pa165/swagger-ui.html#/)

Modules of the app are: 
* rocket-mission-angular -> UI of the app
* rocket-mission-api -> API layer of the app
* rocket-mission-manager -> Main module
* rocket-mission-persistence -> persistance layer with in-memory DB 
* rocket-mission-rest -> REST interface of the app
* rocket-mission-sample-data -> sample data that are loaded each time when the app is started into the DB passwords+usernames here
* rocket-mission-service -> service layer of the app
