package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity;

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
public class CidadaoDadosProfissionais {
	
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpjEmpresaPagadora == null) ? 0 : cnpjEmpresaPagadora.hashCode());
		result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((salarioAnual == null) ? 0 : salarioAnual.hashCode());
		result = prime * result + ((salarioMensal == null) ? 0 : salarioMensal.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CidadaoDadosProfissionais other = (CidadaoDadosProfissionais) obj;
		if (cnpjEmpresaPagadora == null) {
			if (other.cnpjEmpresaPagadora != null)
				return false;
		} else if (!cnpjEmpresaPagadora.equals(other.cnpjEmpresaPagadora))
			return false;
		if (dataFim == null) {
			if (other.dataFim != null)
				return false;
		} else if (!dataFim.equals(other.dataFim))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (salarioAnual == null) {
			if (other.salarioAnual != null)
				return false;
		} else if (!salarioAnual.equals(other.salarioAnual))
			return false;
		if (salarioMensal == null) {
			if (other.salarioMensal != null)
				return false;
		} else if (!salarioMensal.equals(other.salarioMensal))
			return false;
		return true;
	}
	
}
