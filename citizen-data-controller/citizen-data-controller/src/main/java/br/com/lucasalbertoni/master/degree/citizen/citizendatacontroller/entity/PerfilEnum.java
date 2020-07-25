package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity;

import org.springframework.security.core.GrantedAuthority;

public enum PerfilEnum implements GrantedAuthority{
	
	CIDADAO, EMPRESA, SISTEMA_INTERNO;

	@Override
	public String getAuthority() {
		return this.name();
	}

}
