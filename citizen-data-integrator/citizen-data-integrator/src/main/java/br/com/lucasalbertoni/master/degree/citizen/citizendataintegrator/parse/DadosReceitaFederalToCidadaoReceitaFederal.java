package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoReceitaFederal;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.DadosReceitaFederal;

@Component
public class DadosReceitaFederalToCidadaoReceitaFederal {
	
	public CidadaoReceitaFederal parse(DadosReceitaFederal dadosReceitaFederal) {
		CidadaoReceitaFederal cidadaoReceitaFederal = new CidadaoReceitaFederal();
		if(dadosReceitaFederal != null) {
			cidadaoReceitaFederal.setDataInscricao(dadosReceitaFederal.getDataInscricao());
			cidadaoReceitaFederal.setDigitoVerificador(dadosReceitaFederal.getDigitoVerificador());
			cidadaoReceitaFederal.setSituacaoCadastral(dadosReceitaFederal.getSituacaoCadastral());
		}
		return cidadaoReceitaFederal;
	}

}
