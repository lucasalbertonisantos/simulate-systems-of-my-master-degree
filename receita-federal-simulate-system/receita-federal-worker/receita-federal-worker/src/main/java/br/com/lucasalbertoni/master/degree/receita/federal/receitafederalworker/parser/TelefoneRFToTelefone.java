package br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.parser;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.citizenrequesttopic.Telefone;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.receitafederalapisystem.TelefoneRF;

@Component
public class TelefoneRFToTelefone {
	
	public Telefone parse(TelefoneRF telefoneRF) {
		Telefone telefone = new Telefone();
		if(telefoneRF != null) {
			telefone.setNumero(telefoneRF.getNumero());
			telefone.setDdi(telefoneRF.getDdi());
			telefone.setDdd(telefoneRF.getDdd());
			telefone.setCelular(telefoneRF.isCelular());
		}
		return telefone;
	}

}
