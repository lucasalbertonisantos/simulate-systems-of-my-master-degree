package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;


public class CidadaoDadosIntegrados {
	
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
    private CidadaoReceitaFederalDadosIntegrados cidadaoReceitaFederal;
	private Set<EnderecoDadosIntegrados> enderecos;
	private Set<CidadaoDadosProfissionaisDadosIntegrados> dadosProfissionais;
	private Set<RGDadosIntegrados> rgs;
	private Set<EmailDadosIntegrados> emails;
	private Set<TelefoneDadosIntegrados> telefones;
	
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
	public Set<EnderecoDadosIntegrados> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(Set<EnderecoDadosIntegrados> enderecos) {
		this.enderecos = enderecos;
	}
	public Set<CidadaoDadosProfissionaisDadosIntegrados> getDadosProfissionais() {
		return dadosProfissionais;
	}
	public void setDadosProfissionais(Set<CidadaoDadosProfissionaisDadosIntegrados> dadosProfissionais) {
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
	public Set<RGDadosIntegrados> getRgs() {
		return rgs;
	}
	public void setRgs(Set<RGDadosIntegrados> rgs) {
		this.rgs = rgs;
	}
	public CidadaoReceitaFederalDadosIntegrados getCidadaoReceitaFederal() {
		return cidadaoReceitaFederal;
	}
	public void setCidadaoReceitaFederal(CidadaoReceitaFederalDadosIntegrados cidadaoReceitaFederal) {
		this.cidadaoReceitaFederal = cidadaoReceitaFederal;
	}
	public Set<EmailDadosIntegrados> getEmails() {
		return emails;
	}
	public void setEmails(Set<EmailDadosIntegrados> emails) {
		this.emails = emails;
	}
	public Set<TelefoneDadosIntegrados> getTelefones() {
		return telefones;
	}
	public void setTelefones(Set<TelefoneDadosIntegrados> telefones) {
		this.telefones = telefones;
	}

}
