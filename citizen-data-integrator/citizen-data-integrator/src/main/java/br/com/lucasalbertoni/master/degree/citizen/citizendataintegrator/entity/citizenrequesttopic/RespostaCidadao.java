package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic;

import java.util.List;

public class RespostaCidadao {
	
	private List<Header> headers;
	private CidadaoRespostaFila cidadao;
	
	public List<Header> getHeaders() {
		return headers;
	}
	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}
	public CidadaoRespostaFila getCidadao() {
		return cidadao;
	}
	public void setCidadao(CidadaoRespostaFila cidadao) {
		this.cidadao = cidadao;
	}

}
