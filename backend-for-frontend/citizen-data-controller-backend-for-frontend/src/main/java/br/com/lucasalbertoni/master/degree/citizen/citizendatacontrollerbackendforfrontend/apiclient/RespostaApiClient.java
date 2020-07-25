package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder.RespostaDTOListBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.RespostaDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.ListaRespostaDTOToListaResposta;

@Service
public class RespostaApiClient {
	
	@Value("${citizen.controller.resposta.criar.login}")
	private String url;
	
	@Autowired
	private GenericApiClient genericApiClient;
	
	@Autowired
	private ListaRespostaDTOToListaResposta listaRespostaDTOToListaResposta;
	
	@Autowired
	private RespostaDTOListBuilder respostaDTOListBuilder;
	
	public List<Resposta> responder(List<Resposta> respostas, String cpf) {
		List<RespostaDTO> respostasDTO = respostaDTOListBuilder.build(respostas, cpf);
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		sb.append("?cpf=");
		sb.append(cpf);
		RespostaDTO[] resposta = genericApiClient.putRequestWithInternalCredentials(sb.toString(), respostasDTO, RespostaDTO[].class);
		return listaRespostaDTOToListaResposta.parse(resposta);
	}

}
