package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value="CidadaoPerguntaResposta", timeToLive=900)
public class CidadaoResposta implements Serializable{
	
	private static final long serialVersionUID = 5233806588312336299L;
	@Id
	private String cpfColuna;
	private Resposta resposta;
	
	public String getCpfColuna() {
		return cpfColuna;
	}
	public void setCpfColuna(String cpfColuna) {
		this.cpfColuna = cpfColuna;
	}
	public Resposta getResposta() {
		return resposta;
	}
	public void setResposta(Resposta resposta) {
		this.resposta = resposta;
	}

}
