package br.com.lucasalbertoni.master.degree.uf.ac.acufworker.entity.citizenrequesttopic;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Header {
	
	private String id;
	private String nomeSistema;
	private TipoOrgao tipoOrgao;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime data;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNomeSistema() {
		return nomeSistema;
	}
	public void setNomeSistema(String nomeSistema) {
		this.nomeSistema = nomeSistema;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public TipoOrgao getTipoOrgao() {
		return tipoOrgao;
	}
	public void setTipoOrgao(TipoOrgao tipoOrgao) {
		this.tipoOrgao = tipoOrgao;
	}

}
