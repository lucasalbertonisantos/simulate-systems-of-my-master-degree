package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.parse;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.builder.EmailBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoDadosProfissionais;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoSistemas;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Email;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Endereco;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.RG;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Telefone;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.CidadaoRespostaFila;

@Component
public class CidadaoRespostaFilaToCidadao {
	
	@Autowired
	private DadosReceitaFederalToCidadaoReceitaFederal dadosReceitaFederalToCidadaoReceitaFederal;
	
	@Autowired
	private ProfissaoRespostaFilaToCidadaoDadosProfissionais profissaoRespostaFilaToCidadaoDadosProfissionais;
	
	@Autowired
	private EnderecoRespostaFilaToEndereco enderecoRespostaFilaToEndereco;
	
	@Autowired
	private EmailBuilder emailBuilder;
	
	@Autowired
	private TelefoneRespostaFilaToTelefone telefoneRespostaFilaToTelefone;
	
	public Cidadao parse(CidadaoRespostaFila cidadaoRespostaFila, CidadaoSistemas cidadaoSistemas) {
		Cidadao cidadao = new Cidadao();
		cidadao.setCidadaoSistema(cidadaoSistemas);
		if(cidadaoRespostaFila != null) {
			if(cidadaoRespostaFila.getDadosReceitaFederal() != null) {
				cidadao.setCidadaoReceitaFederal(dadosReceitaFederalToCidadaoReceitaFederal.parse(cidadaoRespostaFila.getDadosReceitaFederal()));
				if(cidadao.getCidadaoReceitaFederal() != null) {
					cidadao.getCidadaoReceitaFederal().setCidadao(cidadao);
				}
			}
			cidadao.setCpf(cidadaoRespostaFila.getCpf());
			if(cidadaoRespostaFila.getProfissoes() != null 
					&& !cidadaoRespostaFila.getProfissoes().isEmpty()) {
				cidadao.setDadosProfissionais(new HashSet<>());
				cidadaoRespostaFila.getProfissoes().forEach(profissao->{
					CidadaoDadosProfissionais dadosProfissionais = profissaoRespostaFilaToCidadaoDadosProfissionais.parse(profissao);
					if(dadosProfissionais != null) {
						dadosProfissionais.setCidadao(cidadao);
						cidadao.getDadosProfissionais().add(dadosProfissionais);
					}
				});
			}
			if(cidadaoRespostaFila.getEnderecos() != null 
					&& !cidadaoRespostaFila.getEnderecos().isEmpty()) {
				cidadao.setEnderecos(new HashSet<>());
				cidadaoRespostaFila.getEnderecos().forEach(endereco->{
					Endereco enderecoCidadao = enderecoRespostaFilaToEndereco.parse(endereco);
					if(enderecoCidadao != null) {
						enderecoCidadao.setCidadao(cidadao);
						cidadao.getEnderecos().add(enderecoCidadao);
					}
				});
			}
			cidadao.setDataNascimento(cidadaoRespostaFila.getDataNascimento());
			cidadao.setDataUltimaAtualizacao(cidadaoRespostaFila.getDataUltimaAtualizacao());
			cidadao.setFormacao(cidadaoRespostaFila.getFormacao());
			cidadao.setNome(cidadaoRespostaFila.getNome());
			cidadao.setNomeMae(cidadaoRespostaFila.getNomeMae());
			cidadao.setNomePai(cidadaoRespostaFila.getNomePai());
			cidadao.setProfissao(cidadaoRespostaFila.getProfissao());
			
			if(cidadaoRespostaFila.getRg() != null && !cidadaoRespostaFila.getRg().isBlank()) {
				cidadao.setRgs(new HashSet<>());
				RG rg = new RG();
				rg.setUf(cidadaoRespostaFila.getUfRg());
				rg.setNumero(cidadaoRespostaFila.getRg());
				rg.setCidadao(cidadao);
				rg.setDataEmissao(cidadaoRespostaFila.getDataEmissaoRG());
				rg.setSecretariaEmissao(cidadaoRespostaFila.getSecretariaEmissaoRG());
				cidadao.getRgs().add(rg);
			}
			
			if(cidadaoRespostaFila.getEmails() != null && !cidadaoRespostaFila.getEmails().isEmpty()) {
				cidadao.setEmails(new HashSet<>());
				cidadaoRespostaFila.getEmails().forEach(email->{
					Email emailCidadao = emailBuilder.build(email);
					if(emailCidadao != null) {
						emailCidadao.setCidadao(cidadao);
						cidadao.getEmails().add(emailCidadao);
					}
				});
			}
			
			if(cidadaoRespostaFila.getTelefones() != null && !cidadaoRespostaFila.getTelefones().isEmpty()) {
				cidadao.setTelefones(new HashSet<>());
				cidadaoRespostaFila.getTelefones().forEach(telefone->{
					Telefone telefoneCidadao = telefoneRespostaFilaToTelefone.parse(telefone);
					if(telefoneCidadao != null) {
						telefoneCidadao.setCidadao(cidadao);
						cidadao.getTelefones().add(telefoneCidadao);
					}
				});
			}
			
		}
		return cidadao;
	}

}
