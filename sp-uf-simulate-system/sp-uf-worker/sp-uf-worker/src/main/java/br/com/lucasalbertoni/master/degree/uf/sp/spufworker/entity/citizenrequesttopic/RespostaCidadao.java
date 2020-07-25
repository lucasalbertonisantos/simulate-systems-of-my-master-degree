package br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.citizenrequesttopic;

import java.util.List;

public class RespostaCidadao {
	
	private List<Header> headers;
	private Cidadao cidadao;
	
	public List<Header> getHeaders() {
		return headers;
	}
	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}
	public Cidadao getCidadao() {
		return cidadao;
	}
	public void setCidadao(Cidadao cidadao) {
		this.cidadao = cidadao;
	}

}
