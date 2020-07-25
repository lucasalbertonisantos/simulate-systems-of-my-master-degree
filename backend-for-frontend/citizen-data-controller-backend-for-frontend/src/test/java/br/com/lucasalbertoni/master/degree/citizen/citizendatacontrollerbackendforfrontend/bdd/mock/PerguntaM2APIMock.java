package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.bdd.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.ColunaEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Pergunta;

@RestController
@RequestMapping("/perguntam2mock")
public class PerguntaM2APIMock {
	
	private List<Pergunta> perguntasPassadas = new ArrayList<>();
	
	@PutMapping
	public List<Pergunta> createUpdate(@RequestParam String cpf) {
		return perguntasPassadas;
	}
	
	public void adicionaPergunta(String nome, ColunaEnum coluna) {
		Pergunta pergunta = new Pergunta();
		pergunta.setColuna(coluna);
		pergunta.setPergunta(nome);
		pergunta.setId(Long.valueOf(perguntasPassadas.size()+1));
		pergunta.setOrdem(perguntasPassadas.size()+1);
		perguntasPassadas.add(pergunta);
	}
	
	public Pergunta getPergunta(int posicao) {
		return perguntasPassadas.get(posicao);
	}

}
