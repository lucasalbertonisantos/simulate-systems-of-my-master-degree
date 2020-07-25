package br.com.lucasalbertoni.master.degree.uf.ac.acufworker.apiclient;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import br.com.lucasalbertoni.master.degree.uf.ac.acufworker.entity.ufacapisystem.CidadaoAcre;

@Component
public class CidadaoAcreApiClient {
	
	@Autowired
	private RestTemplate restTemplate;

	public List<CidadaoAcre> findByCpf(@RequestParam(name = "cpf") String cpf) {
		String url = "http://localhost:9080/cidadaos?cpf="+cpf;
		ResponseEntity<CidadaoAcre[]> resposta = restTemplate.getForEntity(url, CidadaoAcre[].class);
		return Arrays.asList(resposta.getBody());
	}

}
