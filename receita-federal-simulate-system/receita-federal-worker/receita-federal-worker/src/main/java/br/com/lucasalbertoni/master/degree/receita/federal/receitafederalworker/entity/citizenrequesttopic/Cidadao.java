package br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.citizenrequesttopic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Cidadao {
	
	private String cpf;
	private String nome;
	private String nomeMae;
	private String nomePai;
	private String ufNascimento;
	private String municipioNascimento;
	private String formacao;
	private String profissao;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataNascimento;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataUltimaAtualizacao;
	private String rg;
	private String ufRg;
	private String secretariaEmissaoRG;
	private List<EnderecoRespostaCidadao> enderecos;
	private List<ProfissaoRespostaCidadao> profissoes;
	private List<String> emails;
	private List<Telefone> telefones;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataEmissaoRG;
	private DadosReceitaFederal dadosReceitaFederal;
	
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
	public String getUfNascimento() {
		return ufNascimento;
	}
	public void setUfNascimento(String ufNascimento) {
		this.ufNascimento = ufNascimento;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public LocalDateTime getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}
	public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
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
	public List<EnderecoRespostaCidadao> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<EnderecoRespostaCidadao> enderecos) {
		this.enderecos = enderecos;
	}
	public List<ProfissaoRespostaCidadao> getProfissoes() {
		return profissoes;
	}
	public void setProfissoes(List<ProfissaoRespostaCidadao> profissoes) {
		this.profissoes = profissoes;
	}
	public String getFormacao() {
		return formacao;
	}
	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}
	public String getProfissao() {
		return profissao;
	}
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}
	public LocalDate getDataEmissaoRG() {
		return dataEmissaoRG;
	}
	public void setDataEmissaoRG(LocalDate dataEmissaoRG) {
		this.dataEmissaoRG = dataEmissaoRG;
	}
	public DadosReceitaFederal getDadosReceitaFederal() {
		return dadosReceitaFederal;
	}
	public void setDadosReceitaFederal(DadosReceitaFederal dadosReceitaFederal) {
		this.dadosReceitaFederal = dadosReceitaFederal;
	}
	public String getMunicipioNascimento() {
		return municipioNascimento;
	}
	public void setMunicipioNascimento(String municipioNascimento) {
		this.municipioNascimento = municipioNascimento;
	}
	public List<String> getEmails() {
		return emails;
	}
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	public String getSecretariaEmissaoRG() {
		return secretariaEmissaoRG;
	}
	public void setSecretariaEmissaoRG(String secretariaEmissaoRG) {
		this.secretariaEmissaoRG = secretariaEmissaoRG;
	}

}
