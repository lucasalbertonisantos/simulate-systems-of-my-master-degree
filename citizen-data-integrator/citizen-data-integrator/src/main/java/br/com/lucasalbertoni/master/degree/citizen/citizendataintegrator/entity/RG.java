package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RG {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String numero;
	private String uf;
	private String secretariaEmissao;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataEmissao;
	
	@ManyToOne
    @JoinColumn(nullable = false)
	@JsonIgnore
    private Cidadao cidadao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getSecretariaEmissao() {
		return secretariaEmissao;
	}
	public void setSecretariaEmissao(String secretariaEmissao) {
		this.secretariaEmissao = secretariaEmissao;
	}
	public LocalDate getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public Cidadao getCidadao() {
		return cidadao;
	}
	public void setCidadao(Cidadao cidadao) {
		this.cidadao = cidadao;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.toUpperCase().hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.toUpperCase().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RG other = (RG) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equalsIgnoreCase(other.numero))
			return false;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equalsIgnoreCase(other.uf))
			return false;
		return true;
	}

}
