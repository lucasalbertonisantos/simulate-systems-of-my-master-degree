package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CidadaoDadosProfissionaisDadosIntegrados {
	
	private Long id;
	private String cargo;
	private Double salarioAnual;
	private Double salarioMensal;
	private String nomeEmpresaPagadora;
	private String cnpjEmpresaPagadora;
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
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public Double getSalarioAnual() {
		return salarioAnual;
	}
	public void setSalarioAnual(Double salarioAnual) {
		this.salarioAnual = salarioAnual;
	}
	public Double getSalarioMensal() {
		return salarioMensal;
	}
	public void setSalarioMensal(Double salarioMensal) {
		this.salarioMensal = salarioMensal;
	}
	public String getNomeEmpresaPagadora() {
		return nomeEmpresaPagadora;
	}
	public void setNomeEmpresaPagadora(String nomeEmpresaPagadora) {
		this.nomeEmpresaPagadora = nomeEmpresaPagadora;
	}
	public String getCnpjEmpresaPagadora() {
		return cnpjEmpresaPagadora;
	}
	public void setCnpjEmpresaPagadora(String cnpjEmpresaPagadora) {
		this.cnpjEmpresaPagadora = cnpjEmpresaPagadora;
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
