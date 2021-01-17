package io.traffic.offences.fees.bot;

import io.traffic.offences.fees.bot.telegram.pengrad.PengradTelegramApiClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling	// todo: move to separate config
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Scheduled(fixedDelayString = "100000")
	public static void testGetMe() {
		var client = new PengradTelegramApiClient("");
		client.getMe();
	}

}
