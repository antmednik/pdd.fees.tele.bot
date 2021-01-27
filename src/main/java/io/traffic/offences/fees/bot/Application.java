package io.traffic.offences.fees.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
@EnableScheduling	// todo: move to separate config
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Scheduled(fixedDelayString = "100000")
	public static void testGetMe() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		/*var client = new PengradTelegramApiClient("", null);
		var response = client.getUpdates(new GenericGetUpdatesRequest());*/
	}
}
