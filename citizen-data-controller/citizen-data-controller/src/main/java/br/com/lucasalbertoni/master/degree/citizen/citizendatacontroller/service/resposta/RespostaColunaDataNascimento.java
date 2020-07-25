package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;

public class RespostaColunaDataNascimento implements RespostaColunaStrategy{

	@Override
	public void avaliar(Resposta resposta, Cidadao cidadao) {
		RespostaColunaStrategy.super.avaliar(resposta, cidadao);
		try {
			LocalDate dataNascimento = LocalDate.parse(resposta.getResposta());
			if(!dataNascimento.equals(cidadao.getDataNascimento())) {
				throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
			}
		}catch (DateTimeParseException e) {
			throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
		}
	}
	
}
