package br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.receitafederalapisystem;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EmpresaPagadoraRF {
	
	private Long id;
	private String cargoOcupado;
	private Double salarioMensal;
	private Double salarioAnual;
	private String nome;
	private String cnpj;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataInicio;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataFim;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCargoOcupado() {
		return cargoOcupado;
	}
	public void setCargoOcupado(String cargoOcupado) {
		this.cargoOcupado = cargoOcupado;
	}
	public Double getSalarioMensal() {
		return salarioMensal;
	}
	public void setSalarioMensal(Double salarioMensal) {
		this.salarioMensal = salarioMensal;
	}
	public Double getSalarioAnual() {
		return salarioAnual;
	}
	public void setSalarioAnual(Double salarioAnual) {
		this.salarioAnual = salarioAnual;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDate getDataFim() {
		return dataFim;
	}
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

}
