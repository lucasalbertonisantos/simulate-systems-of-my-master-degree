package br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.parser;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.citizenrequesttopic.Cidadao;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.citizenrequesttopic.DadosReceitaFederal;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederalworker.entity.receitafederalapisystem.PessoaFisicaRF;

@Component
public class PessoaFisicaRFToCidadao {
	
	@Autowired
	private EnderecoRFToEnderecoRespostaCidadao enderecoToEnderecoRespostaCidadao;
	
	@Autowired
	private EmpresaPagadoraRFToProfissaoRespostaCidadao empresaPagadoraToProfissaoRespostaCidadao;
	
	@Autowired
	private TelefoneRFToTelefone telefoneRFToTelefone;
	
	public Cidadao parse(PessoaFisicaRF pessoaFisica) {
		Cidadao cidadao = new Cidadao();
		if(pessoaFisica != null) {
			cidadao.setCpf(pessoaFisica.getCpf());
			cidadao.setDadosReceitaFederal(new DadosReceitaFederal());
			cidadao.getDadosReceitaFederal().setDataInscricao(pessoaFisica.getDataInscricao());
			cidadao.getDadosReceitaFederal().setDigitoVerificador(pessoaFisica.getDigitoVerificador());
			cidadao.getDadosReceitaFederal().setSituacaoCadastral(pessoaFisica.getSituacaoCadastral());
			cidadao.setDataNascimento(pessoaFisica.getDataNascimento());
			cidadao.setDataUltimaAtualizacao(pessoaFisica.getDataUltimaAtualizacao());
			if(pessoaFisica.getEnderecos() != null 
					&& !pessoaFisica.getEnderecos().isEmpty()) {
				cidadao.setEnderecos(new ArrayList<>());
				pessoaFisica.getEnderecos().forEach(endereco->cidadao.getEnderecos().add(enderecoToEnderecoRespostaCidadao.parse(endereco)));
			}
			cidadao.setFormacao(pessoaFisica.getNivelFormacao());
			cidadao.setNome(pessoaFisica.getNome());
			cidadao.setNomeMae(pessoaFisica.getNomeMae());
			cidadao.setNomePai(pessoaFisica.getNomePai());
			cidadao.setProfissao(pessoaFisica.getProfissao());
			if(pessoaFisica.getEmpresasPagadoras() != null
					&& !pessoaFisica.getEmpresasPagadoras().isEmpty()) {
				cidadao.setProfissoes(new ArrayList<>());
				pessoaFisica.getEmpresasPagadoras().forEach(empresaPagadora->cidadao.getProfissoes().add(empresaPagadoraToProfissaoRespostaCidadao.parse(empresaPagadora)));
			}
			cidadao.setTelefones(new ArrayList<>());
			if(pessoaFisica.getTelefones() != null 
					&& !pessoaFisica.getTelefones().isEmpty()) {
				pessoaFisica.getTelefones().forEach(telefone->cidadao.getTelefones().add(telefoneRFToTelefone.parse(telefone)));
			}
			cidadao.setEmails(new ArrayList<>());
			if(pessoaFisica.getEmail() != null 
					&& !pessoaFisica.getEmail().isBlank()) {
				cidadao.getEmails().add(pessoaFisica.getEmail());
			}
		}
		return cidadao;
	}

}
