package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.utils;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.TipoOrgao;

@Component
public class OrdenacaoTipoSistema {
	
	public int getOrder(TipoOrgao tipoOrgao) {
		if(TipoOrgao.FEDERAL.equals(tipoOrgao)) {
			return 1;
		}else if(TipoOrgao.ESTADUAL.equals(tipoOrgao)) {
			return 2;
		}else if(TipoOrgao.MUNICIPAL.equals(tipoOrgao)) {
			return 3;
		}
		throw new RuntimeException("Não foi possivel encontrar!");
	}

}
