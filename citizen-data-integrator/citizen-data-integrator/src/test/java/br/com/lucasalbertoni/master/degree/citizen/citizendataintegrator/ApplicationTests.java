package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(
	    partitions = 1, 
	    controlledShutdown = true,
	    topics = {"${topico.requisicao.cidadao}", "${topico.resposta.cidadao}"},
	    brokerProperties = {
	        "listeners=PLAINTEXT://localhost:3334", 
	        "port=3334"
	})
class ApplicationTests {
	
	static {
		System.setProperty("spring.kafka.bootstrap-servers", "localhost:3334");
	}

	@Test
	void contextLoads() {
	}

}
