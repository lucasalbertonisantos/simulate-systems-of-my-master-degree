package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Endereco;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.EnderecoRespostaFila;

@Component
public class EnderecoRespostaFilaToEndereco {
	
	public Endereco parse(EnderecoRespostaFila enderecoRespostaFila) {
		Endereco endereco = new Endereco();
		if(enderecoRespostaFila != null) {
			endereco.setCep(enderecoRespostaFila.getCep());
			endereco.setComplemento(enderecoRespostaFila.getComplemento());
			endereco.setLogradouro(enderecoRespostaFila.getLogradouro());
			endereco.setBairro(enderecoRespostaFila.getBairro());
			endereco.setMunicipio(enderecoRespostaFila.getMunicipio());
			endereco.setNumero(enderecoRespostaFila.getNumero());
			endereco.setPais(enderecoRespostaFila.getPais());
			endereco.setUf(enderecoRespostaFila.getUf());
		}
		return endereco;
	}

}
