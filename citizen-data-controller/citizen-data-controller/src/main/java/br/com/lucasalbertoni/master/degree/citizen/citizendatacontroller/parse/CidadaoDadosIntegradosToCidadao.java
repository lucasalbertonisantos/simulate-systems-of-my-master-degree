package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.parse;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata.CidadaoDadosIntegrados;

@Component
public class CidadaoDadosIntegradosToCidadao {
	
	@Autowired
	private EmailDadosIntegradosToEmail emailDadosIntegradosToEmail;
	
	@Autowired
	private TelefoneDadosIntegradosToTelefone telefoneDadosIntegradosToTelefone;
	
	@Autowired
	private EnderecoDadosIntegradosToEndereco enderecoDadosIntegradosToEndereco;
	
	@Autowired
	private RGDadosIntegradosToRG rGDadosIntegradosToRG;
	
	@Autowired
	private CidadaoDadosProfissionaisDadosIntegradosToEmprego cidadaoDadosProfissionaisDadosIntegradosToEmprego;
	
	public Cidadao parse(CidadaoDadosIntegrados cidadaoDadosIntegrados) {
		Cidadao cidadao = new Cidadao();
		if(cidadaoDadosIntegrados != null) {
			cidadao.setCpf(cidadaoDadosIntegrados.getCpf());
			if(cidadaoDadosIntegrados.getCidadaoReceitaFederal() != null) {
				cidadao.setDataInscricaoReceitaFederal(cidadaoDadosIntegrados.getCidadaoReceitaFederal().getDataInscricao());
				cidadao.setDigitoVerificadorReceitaFederal(cidadaoDadosIntegrados.getCidadaoReceitaFederal().getDigitoVerificador());
				cidadao.setSituacaoReceitaFederal(cidadaoDadosIntegrados.getCidadaoReceitaFederal().getSituacaoCadastral());
			}
			cidadao.setDataNascimento(cidadaoDadosIntegrados.getDataNascimento());
			cidadao.setFormacao(cidadaoDadosIntegrados.getFormacao());
			cidadao.setNome(cidadaoDadosIntegrados.getNome());
			cidadao.setNomeMae(cidadaoDadosIntegrados.getNomeMae());
			cidadao.setNomePai(cidadaoDadosIntegrados.getNomePai());
			cidadao.setProfissao(cidadaoDadosIntegrados.getProfissao());
			if(cidadaoDadosIntegrados.getEmails() != null && !cidadaoDadosIntegrados.getEmails().isEmpty()) {
				cidadao.setEmails(new HashSet<>());
				cidadaoDadosIntegrados.getEmails().forEach(email->cidadao.getEmails().add(emailDadosIntegradosToEmail.parse(email, cidadao)));
			}
			if(cidadaoDadosIntegrados.getTelefones() != null && !cidadaoDadosIntegrados.getTelefones().isEmpty()) {
				cidadao.setTelefones(new HashSet<>());
				cidadaoDadosIntegrados.getTelefones().forEach(telefone->cidadao.getTelefones().add(telefoneDadosIntegradosToTelefone.parse(telefone, cidadao)));
			}
			if(cidadaoDadosIntegrados.getEnderecos() != null && !cidadaoDadosIntegrados.getEnderecos().isEmpty()) {
				cidadao.setEnderecos(new HashSet<>());
				cidadaoDadosIntegrados.getEnderecos().forEach(endereco->cidadao.getEnderecos().add(enderecoDadosIntegradosToEndereco.parse(endereco, cidadao)));
			}
			if(cidadaoDadosIntegrados.getRgs() != null && !cidadaoDadosIntegrados.getRgs().isEmpty()) {
				cidadao.setRgs(new HashSet<>());
				cidadaoDadosIntegrados.getRgs().forEach(rg->cidadao.getRgs().add(rGDadosIntegradosToRG.parse(rg, cidadao)));
			}
			if(cidadaoDadosIntegrados.getDadosProfissionais() != null && !cidadaoDadosIntegrados.getDadosProfissionais().isEmpty()) {
				cidadao.setHistoricoProfissional(new HashSet<>());
				cidadaoDadosIntegrados.getDadosProfissionais().forEach(dadoProfissional->cidadao.getHistoricoProfissional().add(cidadaoDadosProfissionaisDadosIntegradosToEmprego.parse(dadoProfissional, cidadao)));
			}
		}
		return cidadao;
	}

}
