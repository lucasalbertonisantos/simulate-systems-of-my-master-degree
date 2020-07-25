package br.com.lucasalbertoni.master.degree.uf.sp.spufworker.apiclient;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.ufspapisystem.CidadaoSaoPaulo;

@Component
public class CidadaoSaoPauloApiClient {
	
	@Autowired
	private RestTemplate restTemplate;

	public List<CidadaoSaoPaulo> findByCpf(@RequestParam(name = "cpf") String cpf) {
		String url = "http://localhost:9084/cidadaos?cpf="+cpf;
		ResponseEntity<CidadaoSaoPaulo[]> resposta = restTemplate.getForEntity(url, CidadaoSaoPaulo[].class);
		return Arrays.asList(resposta.getBody());
	}

}
