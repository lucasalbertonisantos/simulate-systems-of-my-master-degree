package br.com.lucasalbertoni.master.degree.uf.sp.spufworker.parser;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.citizenrequesttopic.EnderecoRespostaCidadao;
import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.ufspapisystem.EnderecoCidadaoSaoPaulo;

@Component
public class EnderecoCidadaoSaoPauloToEnderecoRespostaCidadao {
	
	public EnderecoRespostaCidadao parse(EnderecoCidadaoSaoPaulo enderecoSaoPaulo) {
		EnderecoRespostaCidadao enderecoRespostaCidadao = new EnderecoRespostaCidadao();
		if(enderecoSaoPaulo != null) {
			enderecoRespostaCidadao.setBairro(enderecoSaoPaulo.getBairro());
			enderecoRespostaCidadao.setCep(enderecoSaoPaulo.getCep());
			enderecoRespostaCidadao.setComplemento(enderecoSaoPaulo.getComplemento());
			enderecoRespostaCidadao.setLogradouro(enderecoSaoPaulo.getLogradouro());
			enderecoRespostaCidadao.setMunicipio(enderecoSaoPaulo.getCidade());
			enderecoRespostaCidadao.setNumero(enderecoSaoPaulo.getNumero());
			enderecoRespostaCidadao.setPais(enderecoSaoPaulo.getPais());
			enderecoRespostaCidadao.setUf(enderecoSaoPaulo.getUf());
		}
		return enderecoRespostaCidadao;
	}

}
