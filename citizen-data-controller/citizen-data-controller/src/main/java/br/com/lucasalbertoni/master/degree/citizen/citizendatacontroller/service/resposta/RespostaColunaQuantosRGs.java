package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;

public class RespostaColunaQuantosRGs implements RespostaColunaStrategy{

	@Override
	public void avaliar(Resposta resposta, Cidadao cidadao) {
		RespostaColunaStrategy.super.avaliar(resposta, cidadao);
		try {
			Integer quantidadeRGs = Integer.parseInt(resposta.getResposta().trim());
			if(cidadao.getRgs() == null || quantidadeRGs != cidadao.getRgs().size()) {
				throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
			}
		}catch (NumberFormatException e) {
			throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
		}
	}
	
}
