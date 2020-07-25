package br.com.lucasalbertoni.master.degree.uf.sp.spufworker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.apiclient.CidadaoSaoPauloApiClient;
import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.builder.HeaderBuilder;
import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.builder.RespostaCidadaoBuilder;
import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.citizenrequesttopic.Cidadao;
import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.citizenrequesttopic.Header;
import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.ufspapisystem.CidadaoSaoPaulo;
import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.parser.CidadaoSaoPauloToCidadao;
import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.producer.RespostaCidadaoProducer;

@Service
public class RequisicaoCidadaoService {
	
	@Autowired
	private HeaderBuilder headerBuilder;
	
	@Autowired
	private CidadaoSaoPauloApiClient cidadaoSaoPauloApiClient;
	
	@Autowired
	private CidadaoSaoPauloToCidadao cidadaoSaoPauloToCidadao;
	
	@Autowired
	private RespostaCidadaoBuilder respostaCidadaoBuilder;
	
	@Autowired
	private RespostaCidadaoProducer respostaCidadaoProducer;
	
	public void processar(List<Header> headers, String cpf) {
		if(headers == null) {
			throw new RuntimeException("Requisicao invalida");
		}
		if(cpf == null || cpf.isBlank()) {
			throw new RuntimeException("CPF nulo ou vazio");
		}
		headers.add(headerBuilder.build());
		List<CidadaoSaoPaulo> cidadaos = cidadaoSaoPauloApiClient.findByCpf(cpf);
		if(cidadaos == null
				|| cidadaos.isEmpty()) {
			throw new RuntimeException("Não foram encontrados cidadaos para o cpf: " + cpf);
		}
		CidadaoSaoPaulo cidadaoSaoPaulo = cidadaos.get(0);
		if(cidadaoSaoPaulo == null || cidadaoSaoPaulo.getCpf().isBlank()) {
			throw new RuntimeException("Não foram encontrados cidadaos para o cpf: " + cpf);
		}
		Cidadao cidadao = cidadaoSaoPauloToCidadao.parse(cidadaoSaoPaulo);
		respostaCidadaoProducer.send(respostaCidadaoBuilder.build(headers, cidadao));
	}

}
