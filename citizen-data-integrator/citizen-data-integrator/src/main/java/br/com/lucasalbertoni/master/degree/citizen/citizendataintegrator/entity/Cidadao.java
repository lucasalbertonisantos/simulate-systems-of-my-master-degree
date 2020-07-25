package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cidadao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
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
	private boolean principal;
	
	@OneToOne
    @JoinColumn(nullable = true, unique = true)
	@JsonIgnore
    private CidadaoSistemas cidadaoSistema;
	
	@OneToOne(mappedBy = "cidadao",
			cascade = CascadeType.ALL, 
			fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, unique = true)
    private CidadaoReceitaFederal cidadaoReceitaFederal;
	
	@OneToMany(mappedBy="cidadao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Endereco> enderecos;
	@OneToMany(mappedBy="cidadao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<CidadaoDadosProfissionais> dadosProfissionais;
	@OneToMany(mappedBy="cidadao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<RG> rgs;
	@OneToMany(mappedBy="cidadao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Email> emails;
	@OneToMany(mappedBy="cidadao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Telefone> telefones;
	
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
	public CidadaoSistemas getCidadaoSistema() {
		return cidadaoSistema;
	}
	public void setCidadaoSistema(CidadaoSistemas cidadaoSistema) {
		this.cidadaoSistema = cidadaoSistema;
	}
	public Set<Endereco> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
	public Set<CidadaoDadosProfissionais> getDadosProfissionais() {
		return dadosProfissionais;
	}
	public void setDadosProfissionais(Set<CidadaoDadosProfissionais> dadosProfissionais) {
		this.dadosProfissionais = dadosProfissionais;
	}
	public String getProfissao() {
		return profissao;
	}
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}
	public boolean isPrincipal() {
		return principal;
	}
	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}
	public Set<RG> getRgs() {
		return rgs;
	}
	public void setRgs(Set<RG> rgs) {
		this.rgs = rgs;
	}
	public CidadaoReceitaFederal getCidadaoReceitaFederal() {
		return cidadaoReceitaFederal;
	}
	public void setCidadaoReceitaFederal(CidadaoReceitaFederal cidadaoReceitaFederal) {
		this.cidadaoReceitaFederal = cidadaoReceitaFederal;
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

}
