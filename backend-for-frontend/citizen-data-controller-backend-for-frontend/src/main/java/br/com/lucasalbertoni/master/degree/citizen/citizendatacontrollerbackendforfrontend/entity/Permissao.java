package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity;

import java.io.Serializable;

public class Permissao implements Serializable{
	
	private static final long serialVersionUID = -7806295949542870182L;
	private String cnpj;
	private TokenLiberacaoAcesso tokenLiberacaoAcesso;
	private LoginToken loginToken;
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public TokenLiberacaoAcesso getTokenLiberacaoAcesso() {
		return tokenLiberacaoAcesso;
	}
	public void setTokenLiberacaoAcesso(TokenLiberacaoAcesso tokenLiberacaoAcesso) {
		this.tokenLiberacaoAcesso = tokenLiberacaoAcesso;
	}
	public LoginToken getLoginToken() {
		return loginToken;
	}
	public void setLoginToken(LoginToken loginToken) {
		this.loginToken = loginToken;
	}

}
