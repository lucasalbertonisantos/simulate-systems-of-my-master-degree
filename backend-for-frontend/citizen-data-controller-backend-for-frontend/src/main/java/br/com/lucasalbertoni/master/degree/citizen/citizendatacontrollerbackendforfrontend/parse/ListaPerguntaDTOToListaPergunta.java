package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.PerguntaDTO;

@Component
public class ListaPerguntaDTOToListaPergunta {
	
	public List<Pergunta> parse(PerguntaDTO[] listaPerguntaDTO){
		if(listaPerguntaDTO != null && listaPerguntaDTO.length > 0) {
			List<Pergunta> perguntas = new ArrayList<>();
			for(PerguntaDTO perguntaDTO : listaPerguntaDTO) {
				if(perguntaDTO != null) {
					Pergunta pergunta = new Pergunta();
					pergunta.setId(perguntaDTO.getId());
					pergunta.setPergunta(perguntaDTO.getDescricao());
					pergunta.setOrdem(perguntaDTO.getOrdem());
					pergunta.setColuna(perguntaDTO.getColuna());
					perguntas.add(pergunta);
				}
			}
			return perguntas;
		}
		return Collections.emptyList();
	}

}
