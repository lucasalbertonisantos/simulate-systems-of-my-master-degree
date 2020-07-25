package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.PerguntaDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.ListaPerguntaDTOToListaPergunta;

@Service
public class PerguntaApiClient {
	
	@Value("${citizen.controller.pergunta.criar.login}")
	private String url;
	
	@Autowired
	private GenericApiClient genericApiClient;
	
	@Autowired
	private ListaPerguntaDTOToListaPergunta listaPerguntaDTOToListaPergunta;
	
	public List<Pergunta> solicitar(String cpf) {
		PerguntaDTO[] resposta = genericApiClient.putRequestWithInternalCredentials(url+"?cpf="+cpf, PerguntaDTO[].class);
		return listaPerguntaDTOToListaPergunta.parse(resposta);
	}

}
