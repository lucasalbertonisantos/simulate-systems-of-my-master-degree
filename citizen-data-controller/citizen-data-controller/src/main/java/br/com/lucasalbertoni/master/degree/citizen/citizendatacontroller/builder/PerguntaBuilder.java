package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.builder;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.ColunaEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Pergunta;

@Component
public class PerguntaBuilder {
	
	public Pergunta build(String cpf, int ordem, String descricao, LocalDateTime horario, ColunaEnum coluna) {
		Pergunta pergunta = new Pergunta();
		pergunta.setCpf(cpf);
		pergunta.setDataCriacao(horario);
		pergunta.setDescricao(descricao);
		pergunta.setOrdem(ordem);
		pergunta.setAtiva(true);
		pergunta.setColuna(coluna);
		return pergunta;
	}

}
