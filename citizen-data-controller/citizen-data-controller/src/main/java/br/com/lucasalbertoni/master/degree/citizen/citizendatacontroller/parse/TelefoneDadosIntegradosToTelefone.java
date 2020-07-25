package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Telefone;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata.TelefoneDadosIntegrados;

@Component
public class TelefoneDadosIntegradosToTelefone {
	
	public Telefone parse(TelefoneDadosIntegrados telefoneDadosIntegrados, Cidadao cidadao) {
		Telefone telefone = new Telefone();
		if(telefoneDadosIntegrados != null) {
			telefone.setCelular(telefoneDadosIntegrados.isCelular());
			telefone.setDdd(telefoneDadosIntegrados.getDdd());
			telefone.setDdi(telefoneDadosIntegrados.getDdi());
			telefone.setNumero(telefoneDadosIntegrados.getNumero());
			telefone.setCidadao(cidadao);
		}
		return telefone;
	}

}
