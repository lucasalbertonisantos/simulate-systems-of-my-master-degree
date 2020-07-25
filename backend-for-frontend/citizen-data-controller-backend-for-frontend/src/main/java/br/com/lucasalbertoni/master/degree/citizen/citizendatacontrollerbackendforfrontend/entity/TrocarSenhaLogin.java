package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity;

public class TrocarSenhaLogin {
	
	private LoginToken token;
	private String senhaAtual;
	private String novaSenha;
	
	public LoginToken getToken() {
		return token;
	}
	public void setToken(LoginToken token) {
		this.token = token;
	}
	public String getSenhaAtual() {
		return senhaAtual;
	}
	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
	public String getNovaSenha() {
		return novaSenha;
	}
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

}
