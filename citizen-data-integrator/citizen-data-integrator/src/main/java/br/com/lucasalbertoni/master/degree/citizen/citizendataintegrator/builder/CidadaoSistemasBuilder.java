package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.builder;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoSistemas;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.SistemaBuscaDados;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.TipoOrgao;

@Component
public class CidadaoSistemasBuilder {
	
	public CidadaoSistemas build(SistemaBuscaDados sistemaBuscaDados, 
			String cpf, TipoOrgao tipoOrgao) {
		CidadaoSistemas cidadaoSistemas = new CidadaoSistemas();
		cidadaoSistemas.setSistema(sistemaBuscaDados);
		cidadaoSistemas.setDataBusca(LocalDateTime.now());
		cidadaoSistemas.setCpf(cpf);
		if(tipoOrgao != null) {
			cidadaoSistemas.setTipoSistema(tipoOrgao);
		}
		return cidadaoSistemas;
	}

}
