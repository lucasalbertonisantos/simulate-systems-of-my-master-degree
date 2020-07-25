package br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.citizenrequesttopic.RequisicaoCidadao;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.service.RequisicaoCidadaoService;

@Component
public class RequisicaoCidadaoConsumer {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private RequisicaoCidadaoService requisicaoCidadaoService;
	
	@KafkaListener(topics = "${topico.requisicao.cidadao}")
	public void requisicaoDadosCidadao(String requisicao) {
		try {
			RequisicaoCidadao requisicaoCidadao = objectMapper.readValue(requisicao, RequisicaoCidadao.class);
			requisicaoCidadaoService.processar(requisicaoCidadao.getHeaders(), requisicaoCidadao.getCpf());
		} catch (Exception e) {
			System.err.println("Erro ao reconhecer a mensagem");
			e.printStackTrace();
		}
	}

}
