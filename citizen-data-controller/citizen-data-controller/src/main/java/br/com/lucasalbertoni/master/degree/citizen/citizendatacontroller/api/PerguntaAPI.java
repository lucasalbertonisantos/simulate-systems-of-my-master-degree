package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.PerguntaService;

@RestController
@RequestMapping("/perguntas")
public class PerguntaAPI {
	
	@Autowired
	private PerguntaService perguntaService;
	
	@PutMapping
	public List<Pergunta> put(@RequestParam(name = "cpf") String cpf){
		return perguntaService.createIfNotExistOrNotUpdated(cpf);
	}

}
