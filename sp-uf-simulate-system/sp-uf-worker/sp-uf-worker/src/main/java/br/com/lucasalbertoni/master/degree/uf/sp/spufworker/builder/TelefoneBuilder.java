package br.com.lucasalbertoni.master.degree.uf.sp.spufworker.builder;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.citizenrequesttopic.Telefone;
import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.ufspapisystem.CidadaoSaoPaulo;

@Component
public class TelefoneBuilder {
	
	public Telefone build(CidadaoSaoPaulo cidadaoSaoPaulo) {
		Telefone telefone = new Telefone();
		if(cidadaoSaoPaulo.getNumeroCelular() != null 
				&& !cidadaoSaoPaulo.getNumeroCelular().isBlank()
				&& cidadaoSaoPaulo.getDddCelular() != null
				&& !cidadaoSaoPaulo.getDddCelular().isBlank()
				&& cidadaoSaoPaulo.getDdiCelular() != null
				&& !cidadaoSaoPaulo.getDdiCelular().isBlank()) {
			String numeroCelular = cidadaoSaoPaulo.getNumeroCelular().trim().replace("-", "").replace(".", "");
			telefone.setCelular(true);
			try {
				telefone.setDdi(Integer.parseInt(cidadaoSaoPaulo.getDdiCelular()));
				telefone.setDdd(Integer.parseInt(cidadaoSaoPaulo.getDddCelular()));
				telefone.setNumero(Long.parseLong(numeroCelular));
				return telefone;
			}catch (Exception e) {
				throw new RuntimeException("Erro ao converter o número!", e);
			}
		}
		throw new RuntimeException("Telefone não encontrado dentro do cidadao");
	}

}
