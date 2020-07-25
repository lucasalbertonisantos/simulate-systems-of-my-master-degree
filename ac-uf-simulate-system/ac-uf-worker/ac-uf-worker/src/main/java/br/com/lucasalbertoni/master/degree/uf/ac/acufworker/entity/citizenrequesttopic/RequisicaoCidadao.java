package br.com.lucasalbertoni.master.degree.uf.ac.acufworker.entity.citizenrequesttopic;

import java.util.List;

public class RequisicaoCidadao {
	
	private List<Header> headers;
	private String cpf;
	
	public List<Header> getHeaders() {
		return headers;
	}
	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
