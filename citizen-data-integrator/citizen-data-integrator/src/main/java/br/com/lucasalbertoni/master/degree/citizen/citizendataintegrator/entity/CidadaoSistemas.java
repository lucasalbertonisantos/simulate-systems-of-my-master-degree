package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.TipoOrgao;

@Entity
public class CidadaoSistemas {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@OneToOne(mappedBy = "cidadaoSistema", 
			cascade = CascadeType.ALL, 
			fetch = FetchType.EAGER, 
			optional = false)
	private Cidadao cidadao;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataBusca;
	@ManyToOne
    @JoinColumn(nullable = false)
	@JsonIgnore
    private SistemaBuscaDados sistema;
	@Enumerated(EnumType.STRING)
	private TipoOrgao tipoSistema;
	private String cpf;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cidadao getCidadao() {
		return cidadao;
	}
	public void setCidadao(Cidadao cidadao) {
		this.cidadao = cidadao;
	}
	public LocalDateTime getDataBusca() {
		return dataBusca;
	}
	public void setDataBusca(LocalDateTime dataBusca) {
		this.dataBusca = dataBusca;
	}
	public SistemaBuscaDados getSistema() {
		return sistema;
	}
	public void setSistema(SistemaBuscaDados sistema) {
		this.sistema = sistema;
	}
	public TipoOrgao getTipoSistema() {
		return tipoSistema;
	}
	public void setTipoSistema(TipoOrgao tipoSistema) {
		this.tipoSistema = tipoSistema;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
