package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.bdd.mock;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.RespostaDTO;

@RestController
@RequestMapping("/respostam2mock")
public class RespostaM2APIMock {
	
	private List<RespostaDTO> respostasRecebidas;
	private boolean respostaIncorreta;
	
	@PutMapping
	public List<RespostaDTO> enviarRespostas(@RequestParam String cpf,
			@RequestBody List<RespostaDTO> respostasDTO) {
		if(respostaIncorreta) {
			throw new RuntimeException("Alguma resposta esta incorreta!");
		}
		long cont = 1;
		for(RespostaDTO respostaDTO : respostasDTO) {
			respostaDTO.setAtiva(true);
			respostaDTO.setCpf(cpf);
			respostaDTO.setDataCriacao(LocalDateTime.now());
			respostaDTO.setId(cont);
			cont++;
		}
		respostasRecebidas = respostasDTO;
		return respostasDTO;
	}

	public List<RespostaDTO> getRespostasRecebidas() {
		return respostasRecebidas;
	}

	public void respostaIncorreta() {
		respostaIncorreta = true;
	}

}
