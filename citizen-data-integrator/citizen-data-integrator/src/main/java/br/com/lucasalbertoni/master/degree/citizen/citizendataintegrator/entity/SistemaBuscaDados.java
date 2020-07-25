package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SistemaBuscaDados {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
    @JoinColumn(nullable = true)
    private Sistema sistema;
	@ManyToOne
    @JoinColumn(nullable = true)
	@JsonIgnore
    private BuscaCidadao buscaCidadao;
	@OneToMany(mappedBy="sistema", fetch = FetchType.EAGER)
	private List<CidadaoSistemas> cidadaosHistorico;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Sistema getSistema() {
		return sistema;
	}
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	public BuscaCidadao getBuscaCidadao() {
		return buscaCidadao;
	}
	public void setBuscaCidadao(BuscaCidadao buscaCidadao) {
		this.buscaCidadao = buscaCidadao;
	}
	public List<CidadaoSistemas> getCidadaosHistorico() {
		return cidadaosHistorico;
	}
	public void setCidadaosHistorico(List<CidadaoSistemas> cidadaosHistorico) {
		this.cidadaosHistorico = cidadaosHistorico;
	}

}
