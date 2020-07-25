package br.com.lucasalbertoni.master.degree.uf.ac.acufworker.parser;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.uf.ac.acufworker.builder.TelefoneBuilder;
import br.com.lucasalbertoni.master.degree.uf.ac.acufworker.entity.citizenrequesttopic.Cidadao;
import br.com.lucasalbertoni.master.degree.uf.ac.acufworker.entity.ufacapisystem.CidadaoAcre;

@Component
public class CidadaoAcreToCidadao {
	
	@Autowired
	private TelefoneBuilder telefoneBuilder;
	
	public Cidadao parse(CidadaoAcre cidadaoAcre) {
		Cidadao cidadao = new Cidadao();
		if(cidadaoAcre != null) {
			cidadao.setCpf(cidadaoAcre.getCpf());
			cidadao.setDataNascimento(cidadaoAcre.getDataNascimento());
			cidadao.setDataUltimaAtualizacao(cidadaoAcre.getDataUltimaAtualizacao());
			cidadao.setNome(cidadaoAcre.getNome());
			cidadao.setNomeMae(cidadaoAcre.getNomeMae());
			cidadao.setNomePai(cidadaoAcre.getNomePai());
			cidadao.setRg(cidadaoAcre.getRg());
			cidadao.setUfRg(cidadaoAcre.getUfRg());
			cidadao.setSecretariaEmissaoRG(cidadaoAcre.getSecretariaEmissoraRg());
			cidadao.setUfNascimento(cidadaoAcre.getUfNascimento());
			cidadao.setMunicipioNascimento(cidadaoAcre.getMunicipioNascimento());
			cidadao.setDataEmissaoRG(cidadaoAcre.getDataEmissaoRG());
			cidadao.setTelefones(new ArrayList<>());
			try {
				cidadao.getTelefones().add(telefoneBuilder.build(cidadaoAcre));
			}catch (RuntimeException e) {
				e.printStackTrace();
			}
			cidadao.setEmails(new ArrayList<>());
			if(cidadaoAcre.getEmail() != null && !cidadaoAcre.getEmail().isBlank()) {
				cidadao.getEmails().add(cidadaoAcre.getEmail());
			}
		}
		return cidadao;
	}

}
