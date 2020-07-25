package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.RG;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata.RGDadosIntegrados;

@Component
public class RGDadosIntegradosToRG {
	
	public RG parse(RGDadosIntegrados rgDadosIntegrados, Cidadao cidadao) {
		RG rg = new RG();
		if(rgDadosIntegrados != null) {
			rg.setCidadao(cidadao);
			rg.setDataEmissao(rgDadosIntegrados.getDataEmissao());
			rg.setNumero(rgDadosIntegrados.getNumero());
			rg.setSecretariaEmissao(rgDadosIntegrados.getSecretariaEmissao());
			rg.setUf(rgDadosIntegrados.getUf());
		}
		return rg;
	}

}
