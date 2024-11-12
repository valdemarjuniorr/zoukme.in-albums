package in.zoukme.zouk_album;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class ZoukAlbumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZoukAlbumApplication.class, args);
	}

}
