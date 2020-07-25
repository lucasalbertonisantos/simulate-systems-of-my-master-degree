package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Emprego;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata.CidadaoDadosProfissionaisDadosIntegrados;

@Component
public class CidadaoDadosProfissionaisDadosIntegradosToEmprego {
	
	public Emprego parse(CidadaoDadosProfissionaisDadosIntegrados dadoProfissional, Cidadao cidadao) {
		Emprego emprego = new Emprego();
		if(dadoProfissional != null) {
			emprego.setCargo(dadoProfissional.getCargo());
			emprego.setCidadao(cidadao);
			emprego.setCnpjEmpresaPagadora(dadoProfissional.getCnpjEmpresaPagadora());
			emprego.setDataFim(dadoProfissional.getDataFim());
			emprego.setDataInicio(dadoProfissional.getDataInicio());
			emprego.setNomeEmpresaPagadora(dadoProfissional.getNomeEmpresaPagadora());
			emprego.setSalarioAnual(dadoProfissional.getSalarioAnual());
			emprego.setSalarioMensal(dadoProfissional.getSalarioMensal());
		}
		return emprego;
	}

}
