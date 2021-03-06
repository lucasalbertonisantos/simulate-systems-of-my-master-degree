package br.com.lucasalbertoni.master.degree.uf.sp.spufworker.entity.ufspapisystem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CidadaoSaoPaulo {
	
	private Long id;
	private String cpf;
	private String nome;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataNascimento;
	private String nomeMae;
	private String nomePai;
	private String rg;
	private String ufRg;
	private String localEmissaoRg;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataEmissaoRG;
	private String ufNascimento;
	private String municipioNascimento;
	private String ddiCelular;
	private String dddCelular;
	private String numeroCelular;
	private String email;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataUltimaAtualizacao;
	private Set<EnderecoCidadaoSaoPaulo> enderecos;
	
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
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getUfRg() {
		return ufRg;
	}
	public void setUfRg(String ufRg) {
		this.ufRg = ufRg;
	}
	public LocalDateTime getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}
	public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}
	public String getUfNascimento() {
		return ufNascimento;
	}
	public void setUfNascimento(String ufNascimento) {
		this.ufNascimento = ufNascimento;
	}
	public String getMunicipioNascimento() {
		return municipioNascimento;
	}
	public void setMunicipioNascimento(String municipioNascimento) {
		this.municipioNascimento = municipioNascimento;
	}
	public Set<EnderecoCidadaoSaoPaulo> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(Set<EnderecoCidadaoSaoPaulo> enderecos) {
		this.enderecos = enderecos;
	}
	public LocalDate getDataEmissaoRG() {
		return dataEmissaoRG;
	}
	public void setDataEmissaoRG(LocalDate dataEmissaoRG) {
		this.dataEmissaoRG = dataEmissaoRG;
	}
	public String getDdiCelular() {
		return ddiCelular;
	}
	public void setDdiCelular(String ddiCelular) {
		this.ddiCelular = ddiCelular;
	}
	public String getDddCelular() {
		return dddCelular;
	}
	public void setDddCelular(String dddCelular) {
		this.dddCelular = dddCelular;
	}
	public String getNumeroCelular() {
		return numeroCelular;
	}
	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocalEmissaoRg() {
		return localEmissaoRg;
	}
	public void setLocalEmissaoRg(String localEmissaoRg) {
		this.localEmissaoRg = localEmissaoRg;
	}

}
