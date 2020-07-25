package br.com.lucasalbertoni.master.degree.uf.sp.spufworker.parser;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.builder.TelefoneBuilder;
import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.citizenrequesttopic.Cidadao;
import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.ufspapisystem.CidadaoSaoPaulo;

@Component
public class CidadaoSaoPauloToCidadao {
	
	@Autowired
	private EnderecoCidadaoSaoPauloToEnderecoRespostaCidadao enderecoCidadaoSaoPauloToEnderecoRespostaCidadao;
	
	@Autowired
	private TelefoneBuilder telefoneBuilder;
	
	public Cidadao parse(CidadaoSaoPaulo cidadaoSaoPaulo) {
		Cidadao cidadao = new Cidadao();
		if(cidadaoSaoPaulo != null) {
			cidadao.setCpf(cidadaoSaoPaulo.getCpf());
			cidadao.setDataNascimento(cidadaoSaoPaulo.getDataNascimento());
			cidadao.setDataUltimaAtualizacao(cidadaoSaoPaulo.getDataUltimaAtualizacao());
			cidadao.setNome(cidadaoSaoPaulo.getNome());
			cidadao.setNomeMae(cidadaoSaoPaulo.getNomeMae());
			cidadao.setNomePai(cidadaoSaoPaulo.getNomePai());
			cidadao.setRg(cidadaoSaoPaulo.getRg());
			cidadao.setUfRg(cidadaoSaoPaulo.getUfRg());
			cidadao.setSecretariaEmissaoRG(cidadaoSaoPaulo.getLocalEmissaoRg());
			cidadao.setUfNascimento(cidadaoSaoPaulo.getUfNascimento());
			cidadao.setMunicipioNascimento(cidadaoSaoPaulo.getMunicipioNascimento());
			cidadao.setDataEmissaoRG(cidadaoSaoPaulo.getDataEmissaoRG());
			cidadao.setTelefones(new ArrayList<>());
			try {
				cidadao.getTelefones().add(telefoneBuilder.build(cidadaoSaoPaulo));
			}catch (RuntimeException e) {
				e.printStackTrace();
			}
			cidadao.setEmails(new ArrayList<>());
			if(cidadaoSaoPaulo.getEmail() != null 
					&& !cidadaoSaoPaulo.getEmail().isBlank()) {
				cidadao.getEmails().add(cidadaoSaoPaulo.getEmail());
			}
			if(cidadaoSaoPaulo.getEnderecos() != null) {
				cidadao.setEnderecos(new ArrayList<>());
				cidadaoSaoPaulo.getEnderecos().forEach(endereco->cidadao.getEnderecos().add(enderecoCidadaoSaoPauloToEnderecoRespostaCidadao.parse(endereco)));
			}
		}
		return cidadao;
	}

}
