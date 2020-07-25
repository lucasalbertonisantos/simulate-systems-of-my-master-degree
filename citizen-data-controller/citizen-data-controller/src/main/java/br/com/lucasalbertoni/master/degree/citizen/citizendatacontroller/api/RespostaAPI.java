package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.RespostaService;

@RestController
@RequestMapping("/respostas")
public class RespostaAPI {
	
	@Autowired
	private RespostaService respostaService;
	
	@PutMapping
	public List<Resposta> put(@RequestParam(name = "cpf") String cpf, @RequestParam(name = "email", required = false) String email, @RequestBody List<Resposta> respostas){
		return respostaService.avaliarSalvar(cpf, respostas, email);
	}

}
