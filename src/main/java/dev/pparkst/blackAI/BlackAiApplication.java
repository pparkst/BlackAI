package dev.pparkst.blackAI;

import org.springframework.ai.vectorstore.pgvector.autoconfigure.PgVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(exclude = { PgVectorStoreAutoConfiguration.class })
public class BlackAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlackAiApplication.class, args);
	}

}
