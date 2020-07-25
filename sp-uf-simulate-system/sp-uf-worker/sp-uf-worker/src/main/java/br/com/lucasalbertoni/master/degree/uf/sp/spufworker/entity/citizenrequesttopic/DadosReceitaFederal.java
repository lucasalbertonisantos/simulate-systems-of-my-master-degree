package br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.citizenrequesttopic;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DadosReceitaFederal {
	
	private String situacaoCadastral;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataInscricao;
	private String digitoVerificador;
	
	public String getSituacaoCadastral() {
		return situacaoCadastral;
	}
	public void setSituacaoCadastral(String situacaoCadastral) {
		this.situacaoCadastral = situacaoCadastral;
	}
	public LocalDate getDataInscricao() {
		return dataInscricao;
	}
	public void setDataInscricao(LocalDate dataInscricao) {
		this.dataInscricao = dataInscricao;
	}
	public String getDigitoVerificador() {
		return digitoVerificador;
	}
	public void setDigitoVerificador(String digitoVerificador) {
		this.digitoVerificador = digitoVerificador;
	}

}
