package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TokenLiberacaoAcesso {

	private String tokenAcesso;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataExpiracaoToken;
	
	public String getTokenAcesso() {
		return tokenAcesso;
	}
	public void setTokenAcesso(String tokenAcesso) {
		this.tokenAcesso = tokenAcesso;
	}
	public LocalDateTime getDataExpiracaoToken() {
		return dataExpiracaoToken;
	}
	public void setDataExpiracaoToken(LocalDateTime dataExpiracaoToken) {
		this.dataExpiracaoToken = dataExpiracaoToken;
	}
	
}
