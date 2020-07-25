package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity;

import java.io.Serializable;

public class Login implements Serializable{
	
	private static final long serialVersionUID = -8178238374106882943L;
	private String usuario;
	private String senha;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

}
