package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.bdd.mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata.CidadaoDadosIntegrados;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata.CidadaoReceitaFederalDadosIntegrados;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata.EmailDadosIntegrados;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata.EnderecoDadosIntegrados;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata.RGDadosIntegrados;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata.TelefoneDadosIntegrados;

@RestController
@RequestMapping("/cidadaom1apimock")
public class CidadaoM1APIMock {
	
	private boolean cadastrado;
	private String nome;
	
	public void estaCadastrado() {
		cadastrado = true;
	}

	public void naoEstaCadastrado() {
		cadastrado = false;
	}
	
	@GetMapping
	public CidadaoDadosIntegrados findPrincipalByCpf(@RequestParam String cpf) {
		if(!cadastrado) {
			throw new RuntimeException("Não esta cadastrado!");
		}
		CidadaoDadosIntegrados cidadaoDadosIntegrados = new CidadaoDadosIntegrados();
		cidadaoDadosIntegrados.setCpf(cpf);
		cidadaoDadosIntegrados.setCidadaoReceitaFederal(new CidadaoReceitaFederalDadosIntegrados());
		cidadaoDadosIntegrados.getCidadaoReceitaFederal().setDataInscricao(LocalDate.now());
		cidadaoDadosIntegrados.getCidadaoReceitaFederal().setDigitoVerificador(cpf.substring(cpf.length()-1));
		cidadaoDadosIntegrados.getCidadaoReceitaFederal().setId(1l);
		cidadaoDadosIntegrados.getCidadaoReceitaFederal().setSituacaoCadastral("ATIVO");
		cidadaoDadosIntegrados.setDataNascimento(LocalDate.now());
		cidadaoDadosIntegrados.setDataUltimaAtualizacao(LocalDateTime.now());
		cidadaoDadosIntegrados.setEmails(new HashSet<>());
		EmailDadosIntegrados email = new EmailDadosIntegrados();
		email.setAtivo(true);
		email.setEmail("teste@exemplo.com");
		email.setId(1l);
		cidadaoDadosIntegrados.getEmails().add(email);
		cidadaoDadosIntegrados.setEnderecos(new HashSet<>());
		EnderecoDadosIntegrados endereco = new EnderecoDadosIntegrados();
		endereco.setCep("12345-888");
		endereco.setComplemento("APTO");
		endereco.setId(1l);
		endereco.setLogradouro("avenida bla");
		endereco.setMunicipio("São Paulo");
		endereco.setNumero("666");
		endereco.setPais("BR");
		endereco.setUf("SP");
		cidadaoDadosIntegrados.getEnderecos().add(endereco);
		cidadaoDadosIntegrados.setFormacao("SUPERIOR COMPLETO");
		cidadaoDadosIntegrados.setId(1l);
		cidadaoDadosIntegrados.setNome("TESTE");
		cidadaoDadosIntegrados.setNomeMae("MAE TESTE");
		cidadaoDadosIntegrados.setNomePai("PAI TESTE");
		cidadaoDadosIntegrados.setPrincipal(true);
		cidadaoDadosIntegrados.setProfissao("ANALISTA DE SISTEMAS");
		cidadaoDadosIntegrados.setRgs(new HashSet<>());
		RGDadosIntegrados rg = new RGDadosIntegrados();
		rg.setDataEmissao(LocalDate.now());
		rg.setId(1l);
		rg.setNumero("897564-5");
		rg.setSecretariaEmissao("SSP");
		rg.setUf("SP");
		cidadaoDadosIntegrados.getRgs().add(rg);
		cidadaoDadosIntegrados.setTelefones(new HashSet<>());
		TelefoneDadosIntegrados telefone = new TelefoneDadosIntegrados();
		telefone.setCelular(true);
		telefone.setDdd(11);
		telefone.setDdi(55);
		telefone.setId(1l);
		telefone.setNumero(885588874l);
		cidadaoDadosIntegrados.getTelefones().add(telefone);
		return cidadaoDadosIntegrados;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
