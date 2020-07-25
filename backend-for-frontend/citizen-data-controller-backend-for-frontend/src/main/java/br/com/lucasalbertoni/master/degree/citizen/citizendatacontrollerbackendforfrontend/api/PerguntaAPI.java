package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.StatusPerguntaFlow;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service.PerguntaService;

@RestController
@RequestMapping("/perguntas")
public class PerguntaAPI {
	
	@Autowired
	private PerguntaService perguntaService;
	
	@PutMapping
	public StatusPerguntaFlow solicitarProximaPergunta(@RequestParam String cpf,
			@RequestBody Resposta resposta) {
		return perguntaService.solicitarPergunta(cpf, resposta);
	}
	
	@PostMapping
	public StatusPerguntaFlow iniciar(@RequestParam String cpf) {
		return perguntaService.iniciar(cpf);
	}

}
