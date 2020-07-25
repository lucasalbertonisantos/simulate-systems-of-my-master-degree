package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;

public interface RespostaColunaStrategy {
	
	static final String ERRO_RESPOSTAS_INVALIDAS = "Respostas são inválidas!";
	default void avaliar(Resposta resposta, Cidadao cidadao) {
		if(resposta == null) {
			throw new RuntimeException("Resposta não pode ser nula!");
		}
		if(cidadao == null) {
			throw new RuntimeException("Cidadao não pode ser nulo!");
		}
		if(resposta.getResposta() == null || resposta.getResposta().isBlank()) {
			throw new RuntimeException("A resposta do objeto Resposta não pode ser nem nula nem vazia!");
		}
	}

}
