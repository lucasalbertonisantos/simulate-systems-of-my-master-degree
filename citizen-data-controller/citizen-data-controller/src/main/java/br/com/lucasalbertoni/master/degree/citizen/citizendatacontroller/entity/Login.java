package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Login implements UserDetails{
	
	private static final long serialVersionUID = -4379262435224085812L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String cpfCnpj;
	@Column(unique = true)
	private String email;
	private String senha;
	private boolean senhaGeradaAutomaticamente;
	private boolean loginAtivo;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataCriacao;
	@Enumerated(EnumType.STRING)
	private LoginStatusEnum status;
	@Enumerated(EnumType.STRING)
	private PerfilEnum perfil;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(perfil);
	}
	@Override
	public String getPassword() {
		return getSenha();
	}
	@Override
	public String getUsername() {
		return getCpfCnpj();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return loginAtivo;
	}
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	public PerfilEnum getPerfil() {
		return perfil;
	}
	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
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

}
