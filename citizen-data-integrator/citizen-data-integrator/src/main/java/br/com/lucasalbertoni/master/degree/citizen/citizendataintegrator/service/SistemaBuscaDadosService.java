package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.BuscaCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Sistema;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.SistemaBuscaDados;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.SistemaBuscaDadosRepository;

@Service
public class SistemaBuscaDadosService {
	
	@Autowired
	private SistemaBuscaDadosRepository sistemaBuscaDadosRepository;
	
	public SistemaBuscaDados save(BuscaCidadao buscaCidadao, Sistema sistema) {
		SistemaBuscaDados sistemaBuscaDados = new SistemaBuscaDados();
		sistemaBuscaDados.setBuscaCidadao(buscaCidadao);
		sistemaBuscaDados.setSistema(sistema);
		return save(sistemaBuscaDados);
	}
	
	public SistemaBuscaDados save(SistemaBuscaDados sistemaBuscaDados) {
		if(sistemaBuscaDados == null) {
			throw new RuntimeException("Objeto nulo");
		}
		if(sistemaBuscaDados.getBuscaCidadao() == null
				|| sistemaBuscaDados.getBuscaCidadao().getId() == null) {
			throw new RuntimeException("BuscaDados esta nulo ou vazio");
		}
		if(sistemaBuscaDados.getSistema() == null
				|| sistemaBuscaDados.getSistema().getId() == null) {
			throw new RuntimeException("Sistema esta nulo ou vazio");
		}
		return sistemaBuscaDadosRepository.save(sistemaBuscaDados);
	}

}
