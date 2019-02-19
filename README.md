# kalah implementation

https://en.wikipedia.org/wiki/Kalah

## Build
mvn clean install

## Run
mvn spring-boot:run

## Play
Either use curl, or hit the local endpoint Swagger harness http://localhost:8080/swagger-ui.html

curl --header "Content-Type: application/json" --request POST http://localhost:8080/games

curl --header "Content-Type: application/json" --request PUT http://localhost:8080/games/{gameId}/pits/{pitId}
