package br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.parser;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.citizenrequesttopic.EnderecoRespostaCidadao;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.receitafederalapisystem.EnderecoRF;

@Component
public class EnderecoRFToEnderecoRespostaCidadao {
	
	public EnderecoRespostaCidadao parse(EnderecoRF endereco) {
		EnderecoRespostaCidadao enderecoRespostaCidadao = new EnderecoRespostaCidadao();
		if(endereco != null) {
			enderecoRespostaCidadao.setBairro(endereco.getBairro());
			enderecoRespostaCidadao.setCep(endereco.getCep());
			enderecoRespostaCidadao.setComplemento(endereco.getComplemento());
			enderecoRespostaCidadao.setLogradouro(endereco.getLogradouro());
			enderecoRespostaCidadao.setMunicipio(endereco.getCidade());
			enderecoRespostaCidadao.setNumero(endereco.getNumero());
			enderecoRespostaCidadao.setPais(endereco.getPais());
			enderecoRespostaCidadao.setUf(endereco.getUf());
		}
		return enderecoRespostaCidadao;
	}

}
