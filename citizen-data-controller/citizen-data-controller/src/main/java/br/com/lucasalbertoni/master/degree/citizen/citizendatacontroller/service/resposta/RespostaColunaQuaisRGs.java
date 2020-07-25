package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.RG;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;

public class RespostaColunaQuaisRGs implements RespostaColunaStrategy{

	@Override
	public void avaliar(Resposta resposta, Cidadao cidadao) {
		RespostaColunaStrategy.super.avaliar(resposta, cidadao);
		if(cidadao.getRgs() == null || cidadao.getRgs().isEmpty()) {
			throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
		}
		String[] rgsUfs = resposta.getResposta().trim().split("___");
		if(rgsUfs.length != cidadao.getRgs().size()) {
			throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
		}
		for(RG rg : cidadao.getRgs()) {
			boolean encontrou = false;
			for(String rgUf : rgsUfs) {
				String[] rgUfSeparado = rgUf.split("__");
				if(rgUfSeparado.length != 2) {
					throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
				}
				if(rgUfSeparado[0].equalsIgnoreCase(rg.getNumero())
						&& rgUfSeparado[1].equalsIgnoreCase(rg.getUf())) {
					encontrou = true;
				}
			}
			if(!encontrou) {
				throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
			}
		}
	}
	
}
