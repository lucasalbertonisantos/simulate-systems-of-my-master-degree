package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class BuscaCidadao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String idSolicitacao;
	@OneToMany(mappedBy="buscaCidadao", fetch = FetchType.EAGER)
	private Set<SistemaBuscaDados> retorno;
	private String cpf;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdSolicitacao() {
		return idSolicitacao;
	}
	public void setIdSolicitacao(String idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}
	public Set<SistemaBuscaDados> getRetorno() {
		return retorno;
	}
	public void setRetorno(Set<SistemaBuscaDados> retorno) {
		this.retorno = retorno;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
