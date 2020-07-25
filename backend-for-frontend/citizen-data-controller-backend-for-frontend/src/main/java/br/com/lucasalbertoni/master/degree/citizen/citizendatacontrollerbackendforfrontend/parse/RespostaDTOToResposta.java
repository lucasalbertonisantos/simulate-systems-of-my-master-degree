package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.RespostaDTO;

@Component
public class RespostaDTOToResposta {
	
	@Autowired
	private PerguntaDTOToPergunta perguntaDTOToPergunta;
	
	public Resposta parse(RespostaDTO respostaDTO) {
		Resposta resposta = new Resposta();
		if(respostaDTO != null) {
			resposta.setResposta(resposta.getResposta());
			resposta.setPergunta(perguntaDTOToPergunta.parse(respostaDTO.getPergunta()));
		}
		return resposta;
	}

}
