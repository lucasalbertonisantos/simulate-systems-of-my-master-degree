package br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.citizenrequesttopic;

public class Telefone {
	
	private Integer ddi;
	private Integer ddd;
	private Long numero;
	private boolean celular;
	
	public Integer getDdi() {
		return ddi;
	}
	public void setDdi(Integer ddi) {
		this.ddi = ddi;
	}
	public Integer getDdd() {
		return ddd;
	}
	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public boolean isCelular() {
		return celular;
	}
	public void setCelular(boolean celular) {
		this.celular = celular;
	}

}
