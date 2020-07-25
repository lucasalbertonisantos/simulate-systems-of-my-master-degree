package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity;

import java.io.Serializable;

public class SolicitarLiberacao implements Serializable{
	
	private static final long serialVersionUID = 768658728511297200L;
	private LoginToken token;
	private String cnpj;
	
	public LoginToken getToken() {
		return token;
	}
	public void setToken(LoginToken token) {
		this.token = token;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

}
