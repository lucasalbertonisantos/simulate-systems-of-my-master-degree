package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PermissaoDadosCidadaoEmpresaDTO {
	
	private Long id;
	private String tokenAcesso;
	private boolean confirmado;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataExpiracaoToken;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataConfirmacao;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public boolean isConfirmado() {
		return confirmado;
	}
	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}
	public LocalDateTime getDataConfirmacao() {
		return dataConfirmacao;
	}
	public void setDataConfirmacao(LocalDateTime dataConfirmacao) {
		this.dataConfirmacao = dataConfirmacao;
	}

}
