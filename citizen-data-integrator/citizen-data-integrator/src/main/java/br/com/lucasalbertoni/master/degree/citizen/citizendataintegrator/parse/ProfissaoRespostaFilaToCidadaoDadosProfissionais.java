package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoDadosProfissionais;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.ProfissaoRespostaFila;

@Component
public class ProfissaoRespostaFilaToCidadaoDadosProfissionais {
	
	public CidadaoDadosProfissionais parse(ProfissaoRespostaFila profissaoRespostaFila) {
		CidadaoDadosProfissionais cidadaoDadosProfissionais = new CidadaoDadosProfissionais();
		if(profissaoRespostaFila != null) {
			cidadaoDadosProfissionais.setCargo(profissaoRespostaFila.getCargo());
			cidadaoDadosProfissionais.setDataFim(profissaoRespostaFila.getDataFim());
			cidadaoDadosProfissionais.setDataInicio(profissaoRespostaFila.getDataInicio());
			cidadaoDadosProfissionais.setNomeEmpresaPagadora(profissaoRespostaFila.getNomeEmpresaPagadora());
			cidadaoDadosProfissionais.setCnpjEmpresaPagadora(profissaoRespostaFila.getCnpjEmpresaPagadora());
			cidadaoDadosProfissionais.setSalarioAnual(profissaoRespostaFila.getSalarioAnual());
			cidadaoDadosProfissionais.setSalarioMensal(profissaoRespostaFila.getSalarioMensal());
		}
		return cidadaoDadosProfissionais;
	}

}
