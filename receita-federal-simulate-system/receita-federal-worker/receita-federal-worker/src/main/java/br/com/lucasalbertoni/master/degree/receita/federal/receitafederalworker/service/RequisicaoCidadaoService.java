package br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.apiclient.PessoaFisicaApiClient;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.builder.HeaderBuilder;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.builder.RespostaCidadaoBuilder;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.citizenrequesttopic.Cidadao;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.citizenrequesttopic.Header;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.receitafederalapisystem.PessoaFisicaRF;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.parser.PessoaFisicaRFToCidadao;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.producer.RespostaCidadaoProducer;

@Service
public class RequisicaoCidadaoService {
	
	@Autowired
	private PessoaFisicaApiClient pessoaFisicaApiClient;
	
	@Autowired
	private HeaderBuilder headerBuilder;
	
	@Autowired
	private PessoaFisicaRFToCidadao pessoaFisicaToRespostaCidadao;
	
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
		List<PessoaFisicaRF> pessoas = pessoaFisicaApiClient.findByCpf(cpf);
		if(pessoas == null
				|| pessoas.isEmpty()) {
			throw new RuntimeException("Não foram encontrados pessoas para o cpf: " + cpf);
		}
		PessoaFisicaRF pessoaFisica = pessoas.get(0);
		if(pessoaFisica == null || pessoaFisica.getCpf().isBlank()) {
			throw new RuntimeException("Não foram encontrados cidadaos para o cpf: " + cpf);
		}
		Cidadao cidadao = pessoaFisicaToRespostaCidadao.parse(pessoaFisica);
		respostaCidadaoProducer.send(respostaCidadaoBuilder.build(headers, cidadao));
	}

}
