package br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.builder;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.citizenrequesttopic.Cidadao;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.citizenrequesttopic.Header;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.citizenrequesttopic.RespostaCidadao;

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
