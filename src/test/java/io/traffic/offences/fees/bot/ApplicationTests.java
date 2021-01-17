package io.traffic.offences.fees.bot;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Disabled() // todo
class ApplicationTests {

	@Test
	void contextLoads() {
		assertThat(Boolean.TRUE).isTrue();
	}

}
