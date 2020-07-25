package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RGDadosIntegrados {
	
	private Long id;
	private String numero;
	private String uf;
	private String secretariaEmissao;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataEmissao;
	
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

}
