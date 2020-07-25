package br.com.lucasalbertoni.master.degree.uf.ac.acufworker.builder;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.uf.ac.acufworker.entity.citizenrequesttopic.Telefone;
import br.com.lucasalbertoni.master.degree.uf.ac.acufworker.entity.ufacapisystem.CidadaoAcre;

@Component
public class TelefoneBuilder {
	
	public Telefone build(CidadaoAcre cidadaoAcre) {
		Telefone telefone = new Telefone();
		if(cidadaoAcre.getTelefone() != null && !cidadaoAcre.getTelefone().isBlank()) {
			String numeroTelefone = cidadaoAcre.getTelefone().trim().replace("-", "").replace(".", "");
			telefone.setCelular(numeroTelefone.length()>8);
			telefone.setDdi(55);
			try {
				telefone.setDdd(Integer.parseInt(cidadaoAcre.getDddTelefone()));
				telefone.setNumero(Long.parseLong(numeroTelefone));
				return telefone;
			}catch (Exception e) {
				throw new RuntimeException("Erro ao converter o número!", e);
			}
		}
		throw new RuntimeException("Telefone não encontrado dentro do cidadao");
	}

}
