package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity;

import java.io.Serializable;

public class Pergunta implements Serializable{

	private static final long serialVersionUID = -8506327561854378651L;
	private Long id;
	private String pergunta;
	private int ordem;
	private ColunaEnum coluna;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPergunta() {
		return pergunta;
	}
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	public int getOrdem() {
		return ordem;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	public ColunaEnum getColuna() {
		return coluna;
	}
	public void setColuna(ColunaEnum coluna) {
		this.coluna = coluna;
	}

}
