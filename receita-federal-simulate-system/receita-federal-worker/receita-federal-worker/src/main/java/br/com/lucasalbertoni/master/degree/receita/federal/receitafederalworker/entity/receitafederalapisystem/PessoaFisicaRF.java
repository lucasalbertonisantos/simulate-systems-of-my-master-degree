package br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.receitafederalapisystem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PessoaFisicaRF {
	
	private Long id;
	private String cpf;
	private String nome;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataNascimento;
	private String nomeMae;
	private String nomePai;
	private Double rendaMensal;
	private Double rendaAnual;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataUltimaAtualizacao;
	private String nivelFormacao;
	private String profissao;
	private String situacaoCadastral;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataInscricao;
	private String digitoVerificador;
	private String email;
	private Set<EnderecoRF> enderecos;
	private Set<EmpresaPagadoraRF> empresasPagadoras;
	private Set<TelefoneRF> telefones;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	public String getNomePai() {
		return nomePai;
	}
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}
	public Double getRendaMensal() {
		return rendaMensal;
	}
	public void setRendaMensal(Double rendaMensal) {
		this.rendaMensal = rendaMensal;
	}
	public Double getRendaAnual() {
		return rendaAnual;
	}
	public void setRendaAnual(Double rendaAnual) {
		this.rendaAnual = rendaAnual;
	}
	public LocalDateTime getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}
	public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}
	public String getNivelFormacao() {
		return nivelFormacao;
	}
	public void setNivelFormacao(String nivelFormacao) {
		this.nivelFormacao = nivelFormacao;
	}
	public String getProfissao() {
		return profissao;
	}
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}
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
	public Set<EnderecoRF> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(Set<EnderecoRF> enderecos) {
		this.enderecos = enderecos;
	}
	public Set<EmpresaPagadoraRF> getEmpresasPagadoras() {
		return empresasPagadoras;
	}
	public void setEmpresasPagadoras(Set<EmpresaPagadoraRF> empresasPagadoras) {
		this.empresasPagadoras = empresasPagadoras;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<TelefoneRF> getTelefones() {
		return telefones;
	}
	public void setTelefones(Set<TelefoneRF> telefones) {
		this.telefones = telefones;
	}

}
