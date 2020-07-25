package br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.apiclient;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.receitafederalapisystem.PessoaFisicaRF;

@Component
public class PessoaFisicaApiClient {
	
	@Autowired
	private RestTemplate restTemplate;

	public List<PessoaFisicaRF> findByCpf(String cpf) {
		String url = "http://localhost:9082/pessoas-fisicas?cpf="+cpf;
		ResponseEntity<PessoaFisicaRF[]> resposta = restTemplate.getForEntity(url, PessoaFisicaRF[].class);
		return Arrays.asList(resposta.getBody());
	}

}
