package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.builder.CidadaoSistemasBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.builder.HeaderBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.BuscaCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoSistemas;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Sistema;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.SistemaBuscaDados;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.CidadaoRespostaFila;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.Header;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.RequisicaoCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.RespostaCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.TipoOrgao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.parse.CidadaoRespostaFilaToCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.producer.RequisicaoBuscaCidadaoProducer;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.CidadaoSistemasRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.validate.CpfValidator;

@Service
public class CidadaoSistemasService {
	
	@Autowired
	private RequisicaoBuscaCidadaoProducer requisicaoBuscaCidadaoProducer;
	
	@Autowired
	private HeaderBuilder headerBuilder;
	
	@Autowired
	private BuscaCidadaoService buscaCidadaoService;
	
	@Autowired
	private SistemaService sistemaService;
	
	@Autowired
	private SistemaBuscaDadosService sistemaBuscaDadosService;
	
	@Autowired
	private CidadaoSistemasRepository cidadaoSistemasRepository;
	
	@Autowired
	private CidadaoRespostaFilaToCidadao cidadaoRespostaFilaToCidadao;
	
	@Autowired
	private CpfValidator cpfValidator;
	
	@Autowired
	private CidadaoSistemasBuilder cidadaoSistemasBuilder;
	
	public String buscar(String cpf){
		cpf = cpfValidator.validate(cpf);
		RequisicaoCidadao requisicaoCidadao = new RequisicaoCidadao();
		requisicaoCidadao.setHeaders(new ArrayList<>());
		requisicaoCidadao.getHeaders().add(headerBuilder.build());
		requisicaoCidadao.setCpf(cpf);
		String idSolicitacao = requisicaoCidadao.getHeaders().get(0).getId();
		requisicaoBuscaCidadaoProducer.send(requisicaoCidadao);
		BuscaCidadao buscaCidadao = new BuscaCidadao();
		buscaCidadao.setIdSolicitacao(idSolicitacao);
		buscaCidadao.setCpf(cpf);
		buscaCidadaoService.save(buscaCidadao);
		return idSolicitacao;
	}
	
	public void receberRetornoBuscaSalvar(RespostaCidadao respostaCidadao) {
		if(respostaCidadao == null) {
			throw new RuntimeException("Resposta nula");
		}
		if(respostaCidadao.getHeaders() == null
				|| respostaCidadao.getHeaders().size() != 2) {
			throw new RuntimeException("Header invalido");
		}
		if(respostaCidadao.getCidadao() == null
				|| respostaCidadao.getCidadao().getCpf() == null
				|| respostaCidadao.getCidadao().getCpf().isBlank()) {
			throw new RuntimeException("Cidadao invalido");
		}
		Header headerSistema = headerBuilder.build();
		String idSolicitacao = null;
		String nomeSistemaRetornado = null;
		TipoOrgao tipoOrgao = null;
		for(Header header : respostaCidadao.getHeaders()) {
			if(header.getNomeSistema() == null
					|| header.getNomeSistema().isBlank()) {
				throw new RuntimeException("Nome do sistema no header é inválido!");
			}else if(headerSistema.getNomeSistema().equals(header.getNomeSistema())){
				idSolicitacao = header.getId();
			}else {
				nomeSistemaRetornado = header.getNomeSistema();
				tipoOrgao = header.getTipoOrgao();
			}
		}
		if(tipoOrgao == null) {
			throw new RuntimeException("O órgão não pode ser nulo!");
		}
		if(idSolicitacao == null || idSolicitacao.isBlank()) {
			throw new RuntimeException("Header originador não encontrado!");
		}
		BuscaCidadao buscaCidadao = buscaCidadaoService.findByIdSolicitacao(idSolicitacao);
		if(buscaCidadao == null) {
			throw new RuntimeException("Não foi possível encontrar a busca do cidadão!");
		}
		if(!respostaCidadao.getCidadao().getCpf().equals(buscaCidadao.getCpf())) {
			throw new RuntimeException("O CPF do cidadão deve ser igual o cadastrado na busca!");
		}

		Sistema sistema = sistemaService.findByNomeOrCreate(nomeSistemaRetornado);
		SistemaBuscaDados sistemaBuscaDados = sistemaBuscaDadosService.save(buscaCidadao, sistema);
		
		CidadaoRespostaFila cidadaoRetornado = respostaCidadao.getCidadao();
		CidadaoSistemas cidadaoSistemas = cidadaoSistemasBuilder.build(sistemaBuscaDados, buscaCidadao.getCpf(), tipoOrgao);
		Cidadao cidadao = cidadaoRespostaFilaToCidadao.parse(cidadaoRetornado, cidadaoSistemas);
		cidadaoSistemas.setCidadao(cidadao);
		
		cidadaoSistemasRepository.save(cidadaoSistemas);
	}

	public BuscaCidadao consultarStatus(String idSolicitacao) {
		return buscaCidadaoService.findByIdSolicitacao(idSolicitacao);
	}

	public List<CidadaoSistemas> findByCpf(String cpf) {
		return cidadaoSistemasRepository.findByCpf(cpf);
	}

}
