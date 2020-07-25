package br.com.lucasalbertoni.master.degree.uf.ac.acufworker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.uf.ac.acufworker.apiclient.CidadaoAcreApiClient;
import br.com.lucasalbertoni.master.degree.uf.ac.acufworker.builder.HeaderBuilder;
import br.com.lucasalbertoni.master.degree.uf.ac.acufworker.builder.RespostaCidadaoBuilder;
import br.com.lucasalbertoni.master.degree.uf.ac.acufworker.entity.citizenrequesttopic.Cidadao;
import br.com.lucasalbertoni.master.degree.uf.ac.acufworker.entity.citizenrequesttopic.Header;
import br.com.lucasalbertoni.master.degree.uf.ac.acufworker.entity.ufacapisystem.CidadaoAcre;
import br.com.lucasalbertoni.master.degree.uf.ac.acufworker.parser.CidadaoAcreToCidadao;
import br.com.lucasalbertoni.master.degree.uf.ac.acufworker.producer.RespostaCidadaoProducer;

@Service
public class RequisicaoCidadaoService {
	
	@Autowired
	private HeaderBuilder headerBuilder;
	
	@Autowired
	private CidadaoAcreApiClient cidadaoAcreApiClient;
	
	@Autowired
	private CidadaoAcreToCidadao cidadaoAcreToCidadao;
	
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
		List<CidadaoAcre> cidadaos = cidadaoAcreApiClient.findByCpf(cpf);
		if(cidadaos == null
				|| cidadaos.isEmpty()) {
			throw new RuntimeException("Não foram encontrados cidadaos para o cpf: " + cpf);
		}
		CidadaoAcre cidadaoAcre = cidadaos.get(0);
		if(cidadaoAcre == null || cidadaoAcre.getCpf().isBlank()) {
			throw new RuntimeException("Não foram encontrados cidadaos para o cpf: " + cpf);
		}
		Cidadao cidadao = cidadaoAcreToCidadao.parse(cidadaoAcre);
		respostaCidadaoProducer.send(respostaCidadaoBuilder.build(headers, cidadao));
	}

}
