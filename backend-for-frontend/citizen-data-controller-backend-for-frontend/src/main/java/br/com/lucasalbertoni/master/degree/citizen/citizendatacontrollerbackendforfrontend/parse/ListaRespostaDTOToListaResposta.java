package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.RespostaDTO;

@Component
public class ListaRespostaDTOToListaResposta {
	
	@Autowired
	private RespostaDTOToResposta respostaDTOToResposta;
	
	public List<Resposta> parse(RespostaDTO[] listaRespostaDTO){
		if(listaRespostaDTO != null && listaRespostaDTO.length > 0) {
			List<Resposta> respostas = new ArrayList<>();
			for(RespostaDTO respostaDTO : listaRespostaDTO) {
				respostas.add(respostaDTOToResposta.parse(respostaDTO));
			}
		}
		return Collections.emptyList();
	}

}
