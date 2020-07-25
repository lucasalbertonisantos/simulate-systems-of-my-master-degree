package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.RespostaDTO;

@Component
public class RespostaToRespostaDTO {
	
	@Autowired
	private PerguntaToPerguntaDTO perguntaToPerguntaDTO;
	
	public RespostaDTO parse(Resposta resposta, String cpf) {
		RespostaDTO respostaDTO = new RespostaDTO();
		if(resposta != null) {
			respostaDTO.setAtiva(true);
			respostaDTO.setCpf(cpf);
			respostaDTO.setResposta(resposta.getResposta());
			respostaDTO.setPergunta(perguntaToPerguntaDTO.parse(resposta.getPergunta()));
		}
		return respostaDTO;
	}

}
