package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoSistemas;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service.CidadaoService;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service.CidadaoSistemasService;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service.DefinirCidadaoPrincipal;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.validate.CpfValidator;

@Service
public class CidadaoFacade {
	
	@Autowired
	private CidadaoService cidadaoService;
	
	@Autowired
	private CidadaoSistemasService cidadaoSistemasService;
	
	@Autowired
	private CpfValidator cpfValidator;
	
	@Autowired
	private DefinirCidadaoPrincipal definirCidadaoPrincipal;
	
	public Cidadao find(String cpf) {
		cpf = cpfValidator.validate(cpf);
		if(cidadaoService.existsPrincipalByCpf(cpf)) {
			return cidadaoService.findPrincipalByCpf(cpf);
		}else if(cidadaoService.existsByCpf(cpf)){
			List<CidadaoSistemas> cidadaos = cidadaoSistemasService.findByCpf(cpf);
			Cidadao cidadaoPrincipal = definirCidadaoPrincipal.definir(cidadaos);
			return cidadaoService.save(cidadaoPrincipal);
		}else {
			String numeroSolicitacaoBusca = cidadaoSistemasService.buscar(cpf);
			throw new RuntimeException("Buscando cidadão: " + numeroSolicitacaoBusca);
		}
	}

}
