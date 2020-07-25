package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.builder.EmailBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoDadosProfissionais;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoReceitaFederal;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoSistemas;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Endereco;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.RG;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Telefone;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.utils.OrdenacaoTipoSistema;

@Service
public class DefinirCidadaoPrincipal {
	
	@Autowired
	private EmailBuilder emailBuilder;
	
	@Autowired
	private OrdenacaoTipoSistema ordenacaoTipoSistema;
	
	public Cidadao definir(List<CidadaoSistemas> cidadaos) {
		if(cidadaos == null || cidadaos.isEmpty()) {
			throw new RuntimeException("Deve existir ao menos um cidadao na lista!");
		}
		Map<String, CidadaoSistemas> cidadaosSistemasMap = new HashMap<>();
		for(CidadaoSistemas cidadaoSistemas : cidadaos) {
			if(cidadaoSistemas.getSistema() == null 
					|| cidadaoSistemas.getSistema().getSistema() == null
					|| cidadaoSistemas.getSistema().getSistema().getNome() == null
					|| cidadaoSistemas.getSistema().getSistema().getNome().isBlank()
					|| cidadaoSistemas.getTipoSistema() == null
					|| cidadaoSistemas.getDataBusca() == null
					|| cidadaoSistemas.getCidadao() == null) {
				continue;
			}
			if(!cidadaosSistemasMap.containsKey(cidadaoSistemas.getSistema().getSistema().getNome())) {
				cidadaosSistemasMap.put(cidadaoSistemas.getSistema().getSistema().getNome(), cidadaoSistemas);
			}else if(cidadaosSistemasMap.get(cidadaoSistemas.getSistema().getSistema().getNome()).getDataBusca().isBefore(cidadaoSistemas.getDataBusca())){
				cidadaosSistemasMap.put(cidadaoSistemas.getSistema().getSistema().getNome(), cidadaoSistemas);
			}
		}
		if(cidadaosSistemasMap.isEmpty()) {
			throw new RuntimeException("Nenhum cidadão válido encontrado!");
		}
		List<CidadaoSistemas> cidadaosOrdenados = new ArrayList<>(cidadaosSistemasMap.values());
		Cidadao cidadaoPrincipal = new Cidadao();
		cidadaoPrincipal.setEmails(new HashSet<>());
		cidadaoPrincipal.setDadosProfissionais(new HashSet<>());
		Set<String> emails = new HashSet<>();
		cidadaosOrdenados.sort((CidadaoSistemas c1, CidadaoSistemas c2) -> {
			if(c1.getTipoSistema().equals(c2.getTipoSistema())) {
				return c2.getDataBusca().compareTo(c1.getDataBusca());
			}else {
				Integer orderA = ordenacaoTipoSistema.getOrder(c1.getTipoSistema());
				Integer orderB = ordenacaoTipoSistema.getOrder(c2.getTipoSistema());
				return orderA.compareTo(orderB);
			}
		});
		for(CidadaoSistemas cidadaoBusca : cidadaosOrdenados) {
			if(cidadaoPrincipal.getNome() == null || cidadaoPrincipal.getNome().isBlank()) {
				cidadaoPrincipal.setNome(cidadaoBusca.getCidadao().getNome());
			}
			if(cidadaoPrincipal.getCpf() == null || cidadaoPrincipal.getCpf().isBlank()) {
				cidadaoPrincipal.setCpf(cidadaoBusca.getCidadao().getCpf());
			}
			if(cidadaoPrincipal.getDataNascimento() == null) {
				cidadaoPrincipal.setDataNascimento(cidadaoBusca.getCidadao().getDataNascimento());
			}
			if(cidadaoPrincipal.getProfissao() == null || cidadaoPrincipal.getProfissao().isBlank()) {
				cidadaoPrincipal.setProfissao(cidadaoBusca.getCidadao().getProfissao());
			}
			if(cidadaoPrincipal.getNomeMae() == null || cidadaoPrincipal.getNomeMae().isBlank()) {
				cidadaoPrincipal.setNomeMae(cidadaoBusca.getCidadao().getNomeMae());
			}
			if(cidadaoPrincipal.getNomePai() == null || cidadaoPrincipal.getNomePai().isBlank()) {
				cidadaoPrincipal.setNomePai(cidadaoBusca.getCidadao().getNomePai());
			}
			if(cidadaoPrincipal.getFormacao() == null || cidadaoPrincipal.getFormacao().isBlank()) {
				cidadaoPrincipal.setFormacao(cidadaoBusca.getCidadao().getFormacao());
			}
			if(cidadaoBusca.getCidadao().getEmails() != null && !cidadaoBusca.getCidadao().getEmails().isEmpty()) {
				cidadaoBusca.getCidadao().getEmails().forEach(email -> emails.add(email.getEmail()));
			}
			if(cidadaoBusca.getCidadao().getRgs() != null && !cidadaoBusca.getCidadao().getRgs().isEmpty()) {
				if(cidadaoPrincipal.getRgs() == null) {
					cidadaoPrincipal.setRgs(new HashSet<>());
				}
				cidadaoBusca.getCidadao().getRgs().forEach(rg->{
					RG rgNovo = new RG();
					rgNovo.setCidadao(cidadaoPrincipal);
					rgNovo.setDataEmissao(rg.getDataEmissao());
					rgNovo.setNumero(rg.getNumero());
					rgNovo.setSecretariaEmissao(rg.getSecretariaEmissao());
					rgNovo.setUf(rg.getUf());
					cidadaoPrincipal.getRgs().add(rgNovo);
				});
			}
			if(cidadaoBusca.getCidadao().getEnderecos() != null 
					&& !cidadaoBusca.getCidadao().getEnderecos().isEmpty()) {
				cidadaoBusca.getCidadao().getEnderecos().forEach(endereco->{
					if(cidadaoPrincipal.getEnderecos() == null) {
						cidadaoPrincipal.setEnderecos(new HashSet<>());
					}
					Endereco enderecoNovo = new Endereco();
					enderecoNovo.setCep(endereco.getCep());
					enderecoNovo.setCidadao(cidadaoPrincipal);
					enderecoNovo.setComplemento(endereco.getComplemento());
					enderecoNovo.setLogradouro(endereco.getLogradouro());
					enderecoNovo.setMunicipio(endereco.getMunicipio());
					enderecoNovo.setNumero(endereco.getNumero());
					enderecoNovo.setPais(endereco.getPais());
					enderecoNovo.setUf(endereco.getUf());
					cidadaoPrincipal.getEnderecos().add(enderecoNovo);
				});
			}
			if(cidadaoBusca.getCidadao().getTelefones() != null 
					&& !cidadaoBusca.getCidadao().getTelefones().isEmpty()) {
				if(cidadaoPrincipal.getTelefones() == null) {
					cidadaoPrincipal.setTelefones(new HashSet<>());
				}
				cidadaoBusca.getCidadao().getTelefones().forEach(telefone->{
					Telefone telefoneNovo = new Telefone();
					telefoneNovo.setCidadao(cidadaoPrincipal);
					telefoneNovo.setCelular(telefone.isCelular());
					telefoneNovo.setDdd(telefone.getDdd());
					telefoneNovo.setDdi(telefone.getDdi());
					telefoneNovo.setNumero(telefone.getNumero());
					cidadaoPrincipal.getTelefones().add(telefoneNovo);
				});
			}
			if(cidadaoBusca.getCidadao().getCidadaoReceitaFederal() != null) {
				if(cidadaoPrincipal.getCidadaoReceitaFederal() == null) {
					cidadaoPrincipal.setCidadaoReceitaFederal(new CidadaoReceitaFederal());
				}
				cidadaoPrincipal.getCidadaoReceitaFederal().setCidadao(cidadaoPrincipal);
				if(cidadaoPrincipal.getCidadaoReceitaFederal().getDataInscricao() == null) {
					cidadaoPrincipal.getCidadaoReceitaFederal().setDataInscricao(cidadaoBusca.getCidadao().getCidadaoReceitaFederal().getDataInscricao());
				}
				if(cidadaoPrincipal.getCidadaoReceitaFederal().getDigitoVerificador() == null 
						|| cidadaoPrincipal.getCidadaoReceitaFederal().getDigitoVerificador().isBlank()) {
					cidadaoPrincipal.getCidadaoReceitaFederal().setDigitoVerificador(cidadaoBusca.getCidadao().getCidadaoReceitaFederal().getDigitoVerificador());
				}
				if(cidadaoPrincipal.getCidadaoReceitaFederal().getSituacaoCadastral() == null 
						|| cidadaoPrincipal.getCidadaoReceitaFederal().getSituacaoCadastral().isBlank()) {
					cidadaoPrincipal.getCidadaoReceitaFederal().setSituacaoCadastral(cidadaoBusca.getCidadao().getCidadaoReceitaFederal().getSituacaoCadastral());
				}
			}
			if(cidadaoBusca.getCidadao().getDadosProfissionais() != null && !cidadaoBusca.getCidadao().getDadosProfissionais().isEmpty()) {
				cidadaoBusca.getCidadao().getDadosProfissionais().forEach(dadoProfissional->{
					CidadaoDadosProfissionais dadosProfissionais = new CidadaoDadosProfissionais();
					dadosProfissionais.setCidadao(cidadaoPrincipal);
					dadosProfissionais.setCargo(dadoProfissional.getCargo());
					dadosProfissionais.setCnpjEmpresaPagadora(dadoProfissional.getCnpjEmpresaPagadora());
					dadosProfissionais.setDataFim(dadoProfissional.getDataFim());
					dadosProfissionais.setDataInicio(dadoProfissional.getDataInicio());
					dadosProfissionais.setNomeEmpresaPagadora(dadoProfissional.getNomeEmpresaPagadora());
					dadosProfissionais.setSalarioAnual(dadoProfissional.getSalarioAnual());
					dadosProfissionais.setSalarioMensal(dadoProfissional.getSalarioMensal());
					cidadaoPrincipal.getDadosProfissionais().add(dadosProfissionais);
				});
			}
		}
		if(cidadaoPrincipal.getRgs() != null) {
			cidadaoPrincipal.getRgs().forEach(rg->rg.setCidadao(cidadaoPrincipal));
		}
		emails.forEach(email->cidadaoPrincipal.getEmails().add(emailBuilder.build(email, cidadaoPrincipal)));
		cidadaoPrincipal.setDataUltimaAtualizacao(LocalDateTime.now());
		cidadaoPrincipal.setPrincipal(true);
		return cidadaoPrincipal;
	}

}
