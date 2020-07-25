package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Email;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;

public class RespostaColunaEmail implements RespostaColunaStrategy{

	@Override
	public void avaliar(Resposta resposta, Cidadao cidadao) {
		RespostaColunaStrategy.super.avaliar(resposta, cidadao);
		if(cidadao.getEmails() == null || cidadao.getEmails().isEmpty()) {
			throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
		}
		boolean encontrou = false;
		for(Email emailCidadao : cidadao.getEmails()) {
			if(resposta.getResposta().trim().equalsIgnoreCase(emailCidadao.getEmail())) {
				encontrou = true;
				break;
			}
		}
		if(!encontrou) {
			throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
		}
	}
	
}
