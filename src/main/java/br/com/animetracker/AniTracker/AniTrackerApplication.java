package br.com.animetracker.AniTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class AniTrackerApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
			.directory(".")
			.ignoreIfMalformed()
			.ignoreIfMissing()
			.load();

		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});

		SpringApplication.run(AniTrackerApplication.class, args);
	}

}
