package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.PerguntaDTO;

@Component
public class PerguntaDTOToPergunta {
	
	public Pergunta parse(PerguntaDTO perguntaDTO) {
		Pergunta pergunta = new Pergunta();
		if(perguntaDTO != null) {
			pergunta.setId(perguntaDTO.getId());
			pergunta.setPergunta(perguntaDTO.getDescricao());
			pergunta.setOrdem(perguntaDTO.getOrdem());
			pergunta.setColuna(perguntaDTO.getColuna());
		}
		return pergunta;
	}

}
