package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Telefone;

public class RespostaColunaCelular implements RespostaColunaStrategy{

	@Override
	public void avaliar(Resposta resposta, Cidadao cidadao) {
		RespostaColunaStrategy.super.avaliar(resposta, cidadao);
		String[] celular = resposta.getResposta().trim().split("___");
		if(celular.length != 3) {
			throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
		}
		if(cidadao.getTelefones() == null
				|| cidadao.getTelefones().isEmpty()) {
			throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
		}
		try {
			Integer ddi = Integer.parseInt(celular[0]);
			Integer ddd = Integer.parseInt(celular[1]);
			Long numero = Long.parseLong(celular[2]);
			boolean encontrou = false;
			for(Telefone telefone : cidadao.getTelefones()) {
				if(telefone.isCelular()
						&& ddi.equals(telefone.getDdi())
						&& ddd.equals(telefone.getDdd())
						&& numero.equals(telefone.getNumero())) {
					encontrou = true;
					break;
				}
			}
			if(!encontrou) {
				throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
			}
		}catch (Exception e) {
			throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
		}
	}
	
}
