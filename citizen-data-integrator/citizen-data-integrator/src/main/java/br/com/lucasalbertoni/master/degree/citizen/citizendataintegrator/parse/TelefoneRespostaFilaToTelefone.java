package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Telefone;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.TelefoneRespostaFila;

@Component
public class TelefoneRespostaFilaToTelefone {
	
	public Telefone parse(TelefoneRespostaFila telefoneRespostaFila) {
		Telefone telefone = new Telefone();
		if(telefoneRespostaFila != null) {
			telefone.setDdi(telefoneRespostaFila.getDdi());
			telefone.setDdd(telefoneRespostaFila.getDdd());
			telefone.setNumero(telefoneRespostaFila.getNumero());
			telefone.setCelular(telefoneRespostaFila.isCelular());
		}
		return telefone;
	}

}
