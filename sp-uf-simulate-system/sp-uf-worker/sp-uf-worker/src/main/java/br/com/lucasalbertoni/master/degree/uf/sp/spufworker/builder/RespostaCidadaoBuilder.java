package br.com.lucasalbertoni.master.degree.uf.sp.spufworker.builder;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.citizenrequesttopic.Cidadao;
import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.citizenrequesttopic.Header;
import br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.citizenrequesttopic.RespostaCidadao;

@Component
public class RespostaCidadaoBuilder {
	
	public RespostaCidadao build(List<Header> headers, 
			Cidadao cidadao) {
		RespostaCidadao respostaCidadao = new RespostaCidadao();
		respostaCidadao.setHeaders(headers);
		respostaCidadao.setCidadao(cidadao);
		return respostaCidadao;
	}

}
