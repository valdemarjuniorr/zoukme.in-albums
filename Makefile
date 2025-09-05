# execute build and run the application with "local" profile
start:
	./mvnw spring-boot:run -Dspring-boot.run.profiles=local

native-start:
	./mvnw -Pnative native:compile -DskipTests
	./target/zoukme.in-albums
