package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.RespostaCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service.CidadaoSistemasService;

@Component
public class CidadaoConsumer {
	
	private Logger logger = LoggerFactory.getLogger(CidadaoConsumer.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private CidadaoSistemasService cidadaoSistemasService;
	
	@KafkaListener(topics = "${topico.resposta.cidadao}")
	public void requisicaoDadosCidadao(String requisicao) {
		try {
			logger.info(requisicao);
			RespostaCidadao respostaCidadao = objectMapper.readValue(requisicao, RespostaCidadao.class);
			cidadaoSistemasService.receberRetornoBuscaSalvar(respostaCidadao);
		} catch (Exception e) {
			logger.error(requisicao, e);
			e.printStackTrace();
		}
	}

}
