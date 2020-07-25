package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;

public class RespostaColunaNome implements RespostaColunaStrategy{

	@Override
	public void avaliar(Resposta resposta, Cidadao cidadao) {
		RespostaColunaStrategy.super.avaliar(resposta, cidadao);
		if(!resposta.getResposta().trim().equalsIgnoreCase(cidadao.getNome())) {
			throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
		}
	}
	
}
