package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value="CidadaoPerguntaResposta", timeToLive=900)
public class CidadaoPergunta implements Serializable{
	
	private static final long serialVersionUID = 5233806588312336299L;
	@Id
	private String cpf;
	private List<Pergunta> perguntas;
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public List<Pergunta> getPerguntas() {
		return perguntas;
	}
	public void setPerguntas(List<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}

}
