package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Endereco;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata.EnderecoDadosIntegrados;

@Component
public class EnderecoDadosIntegradosToEndereco {

	public Endereco parse(EnderecoDadosIntegrados enderecoDadosIntegrados, Cidadao cidadao) {
		Endereco endereco = new Endereco();
		if(enderecoDadosIntegrados != null) {
			endereco.setCep(enderecoDadosIntegrados.getCep());
			endereco.setCidadao(cidadao);
			endereco.setComplemento(enderecoDadosIntegrados.getComplemento());
			endereco.setLogradouro(enderecoDadosIntegrados.getLogradouro());
			endereco.setMunicipio(enderecoDadosIntegrados.getMunicipio());
			endereco.setNumero(enderecoDadosIntegrados.getNumero());
			endereco.setPais(enderecoDadosIntegrados.getPais());
			endereco.setUf(enderecoDadosIntegrados.getUf());
		}
		return endereco;
	}

}
