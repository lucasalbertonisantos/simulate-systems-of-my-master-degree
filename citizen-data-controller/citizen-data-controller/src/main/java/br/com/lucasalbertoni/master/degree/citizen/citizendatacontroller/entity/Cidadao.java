package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cidadao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String cpf;
	private String nome;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataNascimento;
	private String nomeMae;
	private String nomePai;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataUltimaAtualizacao;
	private String formacao;
	private String profissao;
	private String situacaoReceitaFederal;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataInscricaoReceitaFederal;
	private String digitoVerificadorReceitaFederal;
	
	@OneToMany(mappedBy="cidadao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Endereco> enderecos;
	@OneToMany(mappedBy="cidadao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<RG> rgs;
	@OneToMany(mappedBy="cidadao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Email> emails;
	@OneToMany(mappedBy="cidadao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Telefone> telefones;
	@OneToMany(mappedBy="cidadao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Emprego> historicoProfissional;
	@OneToMany(mappedBy="cidadao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Pergunta> perguntas;
	@OneToMany(mappedBy="cidadao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Resposta> respostas;
	
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
	public LocalDateTime getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}
	public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}
	public String getFormacao() {
		return formacao;
	}
	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}
	public Set<Endereco> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
	public String getProfissao() {
		return profissao;
	}
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}
	public Set<RG> getRgs() {
		return rgs;
	}
	public void setRgs(Set<RG> rgs) {
		this.rgs = rgs;
	}
	public Set<Email> getEmails() {
		return emails;
	}
	public void setEmails(Set<Email> emails) {
		this.emails = emails;
	}
	public Set<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(Set<Telefone> telefones) {
		this.telefones = telefones;
	}
	public String getSituacaoReceitaFederal() {
		return situacaoReceitaFederal;
	}
	public void setSituacaoReceitaFederal(String situacaoReceitaFederal) {
		this.situacaoReceitaFederal = situacaoReceitaFederal;
	}
	public LocalDate getDataInscricaoReceitaFederal() {
		return dataInscricaoReceitaFederal;
	}
	public void setDataInscricaoReceitaFederal(LocalDate dataInscricaoReceitaFederal) {
		this.dataInscricaoReceitaFederal = dataInscricaoReceitaFederal;
	}
	public String getDigitoVerificadorReceitaFederal() {
		return digitoVerificadorReceitaFederal;
	}
	public void setDigitoVerificadorReceitaFederal(String digitoVerificadorReceitaFederal) {
		this.digitoVerificadorReceitaFederal = digitoVerificadorReceitaFederal;
	}
	public Set<Emprego> getHistoricoProfissional() {
		return historicoProfissional;
	}
	public void setHistoricoProfissional(Set<Emprego> historicoProfissional) {
		this.historicoProfissional = historicoProfissional;
	}
	public Set<Pergunta> getPerguntas() {
		return perguntas;
	}
	public void setPerguntas(Set<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}
	public Set<Resposta> getRespostas() {
		return respostas;
	}
	public void setRespostas(Set<Resposta> respostas) {
		this.respostas = respostas;
	}

}
