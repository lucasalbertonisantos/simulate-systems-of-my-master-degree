package br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.parser;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.citizenrequesttopic.ProfissaoRespostaCidadao;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.receitafederalapisystem.EmpresaPagadoraRF;

@Component
public class EmpresaPagadoraRFToProfissaoRespostaCidadao {
	
	public ProfissaoRespostaCidadao parse(EmpresaPagadoraRF empresaPagadora) {
		ProfissaoRespostaCidadao profissaoRespostaCidadao = new ProfissaoRespostaCidadao();
		if(empresaPagadora != null) {
			profissaoRespostaCidadao.setCargo(empresaPagadora.getCargoOcupado());
			profissaoRespostaCidadao.setCnpjEmpresaPagadora(empresaPagadora.getCnpj());
			profissaoRespostaCidadao.setDataFim(empresaPagadora.getDataFim());
			profissaoRespostaCidadao.setDataInicio(empresaPagadora.getDataInicio());
			profissaoRespostaCidadao.setNomeEmpresaPagadora(empresaPagadora.getNome());
			profissaoRespostaCidadao.setSalarioAnual(empresaPagadora.getSalarioAnual());
			profissaoRespostaCidadao.setSalarioMensal(empresaPagadora.getSalarioMensal());
		}
		return profissaoRespostaCidadao;
	}

}
