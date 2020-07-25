package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.RespostaApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.CidadaoResposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.StatusPerguntaFlow;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.CidadaoRespostaRepository;

@Service
public class PerguntaUltimaPosicaoService {
	
	@Autowired
	private CidadaoRespostaRepository cidadaoRespostaRepository;
	
	@Autowired
	private RespostaApiClient respostaApiClient;
	
	public StatusPerguntaFlow processarUltimaPosicao(String cpf, Resposta resposta, List<String> chaves) {
		if(chaves == null || chaves.isEmpty()) {
			throw new RuntimeException("As chaves não podem ser nulos nem vazio");
		}
		if(resposta == null) {
			throw new RuntimeException("A resposta não pode ser nula!");
		}
		List<Resposta> respostas = new ArrayList<>();
		for(String chaveEncontrar : chaves) {
			Optional<CidadaoResposta> respostaEncontradaO = cidadaoRespostaRepository.findById(chaveEncontrar);
			if(respostaEncontradaO.isEmpty()) {
				throw new RuntimeException("Existem dados obrigatórios não preenchidos na resposta!");
			}
			respostas.add(respostaEncontradaO.get().getResposta());
		}
		respostaApiClient.responder(respostas, cpf);
		StatusPerguntaFlow statusPerguntaFlow = new StatusPerguntaFlow();
		//Para evitar problema no flutter
		statusPerguntaFlow.setPergunta(resposta.getPergunta());
		statusPerguntaFlow.setStatus("SUCESSO");
		return statusPerguntaFlow;
	}

}
