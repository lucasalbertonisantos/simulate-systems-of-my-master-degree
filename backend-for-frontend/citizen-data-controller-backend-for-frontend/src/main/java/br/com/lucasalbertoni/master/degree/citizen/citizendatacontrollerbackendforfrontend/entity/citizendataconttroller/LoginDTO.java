package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginStatusEnum;

public class LoginDTO {
	
	private Long id;
	private String cpfCnpj;
	private String email;
	private String senha;
	private boolean senhaGeradaAutomaticamente;
	private boolean loginAtivo;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataCriacao;
	private LoginStatusEnum status;
	private String perfil;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean isSenhaGeradaAutomaticamente() {
		return senhaGeradaAutomaticamente;
	}
	public void setSenhaGeradaAutomaticamente(boolean senhaGeradaAutomaticamente) {
		this.senhaGeradaAutomaticamente = senhaGeradaAutomaticamente;
	}
	public boolean isLoginAtivo() {
		return loginAtivo;
	}
	public void setLoginAtivo(boolean loginAtivo) {
		this.loginAtivo = loginAtivo;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public LoginStatusEnum getStatus() {
		return status;
	}
	public void setStatus(LoginStatusEnum status) {
		this.status = status;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

}
