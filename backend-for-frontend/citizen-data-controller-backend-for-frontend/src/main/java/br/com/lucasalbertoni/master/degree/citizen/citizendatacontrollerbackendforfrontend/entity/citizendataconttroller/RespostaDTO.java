package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RespostaDTO {
	
	private Long id;
	private String resposta;
	private String cpf;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataCriacao;
    private PerguntaDTO pergunta;
	private boolean ativa;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public PerguntaDTO getPergunta() {
		return pergunta;
	}
	public void setPergunta(PerguntaDTO pergunta) {
		this.pergunta = pergunta;
	}
	public boolean isAtiva() {
		return ativa;
	}
	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

}
