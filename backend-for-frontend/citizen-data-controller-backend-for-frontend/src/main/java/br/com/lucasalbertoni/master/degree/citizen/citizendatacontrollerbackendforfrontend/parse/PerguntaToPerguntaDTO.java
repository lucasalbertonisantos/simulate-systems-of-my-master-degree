package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.PerguntaDTO;

@Component
public class PerguntaToPerguntaDTO {
	
	public PerguntaDTO parse(Pergunta pergunta) {
		PerguntaDTO perguntaDTO = new PerguntaDTO();
		if(pergunta != null) {
			perguntaDTO.setId(pergunta.getId());
			perguntaDTO.setAtiva(true);
			perguntaDTO.setDescricao(pergunta.getPergunta());
			perguntaDTO.setOrdem(pergunta.getOrdem());
			perguntaDTO.setColuna(pergunta.getColuna());
		}
		return perguntaDTO;
	}

}
