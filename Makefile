start:
	mvn spring-boot:run

native-start:
	mvn -Pnative native:compile -DskipTests
	./target/zoukme.in-albums