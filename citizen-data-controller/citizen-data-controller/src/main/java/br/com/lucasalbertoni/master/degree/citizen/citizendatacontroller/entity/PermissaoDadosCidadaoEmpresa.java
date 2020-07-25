package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PermissaoDadosCidadaoEmpresa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true)
	private String tokenAcesso;
	private boolean confirmado;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataExpiracaoToken;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataConfirmacao;
	
	@ManyToOne
    @JoinColumn(nullable = false)
	@JsonIgnore
    private Cidadao cidadao;
	
	@ManyToOne
    @JoinColumn(nullable = false)
	@JsonIgnore
    private Empresa empresa;

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
	public Cidadao getCidadao() {
		return cidadao;
	}
	public void setCidadao(Cidadao cidadao) {
		this.cidadao = cidadao;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
