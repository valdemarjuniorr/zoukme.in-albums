native-start:
	./mvnw -Pnative native:compile -DskipTests
	./target/zoukme.in-albums
