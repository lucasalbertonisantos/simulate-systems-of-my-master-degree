package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.RespostaDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.RespostaToRespostaDTO;

@Component
public class RespostaDTOListBuilder {
	
	@Autowired
	private RespostaToRespostaDTO respostaToRespostaDTO;
	
	public List<RespostaDTO> build(List<Resposta> respostas, String cpf){
		List<RespostaDTO> respostasDTO = new ArrayList<>();
		respostas.forEach(resposta->respostasDTO.add(respostaToRespostaDTO.parse(resposta, cpf)));
		return respostasDTO;
	}

}
