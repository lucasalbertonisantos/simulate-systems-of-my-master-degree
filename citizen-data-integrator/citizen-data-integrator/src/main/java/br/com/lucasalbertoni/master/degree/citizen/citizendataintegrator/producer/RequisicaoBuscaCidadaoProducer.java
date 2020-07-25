package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.RequisicaoCidadao;

@Component
public class RequisicaoBuscaCidadaoProducer {
	
	@Value("${topico.requisicao.cidadao}")
	private String topic;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;

	public void send(RequisicaoCidadao requisicaoCidadao) {
		try {
			kafkaTemplate.send(topic, objectMapper.writeValueAsString(requisicaoCidadao));
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Erro ao enviar mensagem", e);
		}
	}

}
