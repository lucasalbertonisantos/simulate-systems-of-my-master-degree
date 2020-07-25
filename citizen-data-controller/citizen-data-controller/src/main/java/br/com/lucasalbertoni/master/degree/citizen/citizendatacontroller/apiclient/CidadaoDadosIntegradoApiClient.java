package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.apiclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata.CidadaoDadosIntegrados;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.parse.CidadaoDadosIntegradosToCidadao;

@Service
public class CidadaoDadosIntegradoApiClient {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CidadaoDadosIntegradosToCidadao cidadaoDadosIntegradosToCidadao;
	
	@Value("${citizen.integrated.data.cidadao.princial.url}")
	private String urlCidadaoPrincipal;
	
	public Cidadao findByCpf(String cpf) {
		String url = urlCidadaoPrincipal+"?cpf="+cpf;
		try {
			ResponseEntity<CidadaoDadosIntegrados> resposta = restTemplate.getForEntity(url, CidadaoDadosIntegrados.class);
			return cidadaoDadosIntegradosToCidadao.parse(resposta.getBody());
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
