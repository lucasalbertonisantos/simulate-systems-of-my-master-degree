package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity;

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
public class Emprego {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	@ManyToOne
    @JoinColumn(nullable = true)
	@JsonIgnore
    private Cidadao cidadao;
	
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
	public Cidadao getCidadao() {
		return cidadao;
	}
	public void setCidadao(Cidadao cidadao) {
		this.cidadao = cidadao;
	}
	
}
