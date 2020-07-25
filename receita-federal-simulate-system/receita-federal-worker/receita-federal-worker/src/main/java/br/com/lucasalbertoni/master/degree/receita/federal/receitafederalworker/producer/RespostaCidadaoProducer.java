package br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.citizenrequesttopic.RespostaCidadao;

@Component
public class RespostaCidadaoProducer {
	
	@Value("${topico.resposta.cidadao}")
	private String topic;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;

	public void send(RespostaCidadao respostaCidadao) {
		try {
			kafkaTemplate.send(topic, objectMapper.writeValueAsString(respostaCidadao));
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Erro ao enviar mensagem", e);
		}
	}

}
