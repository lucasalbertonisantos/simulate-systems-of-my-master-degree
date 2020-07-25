package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.dto;

public class TokenDTO {
	
	private String token;
	private String tipo;
	
	public TokenDTO(String token) {
		this.tipo = "Bearer";
		this.token = token;
	}
	
	public TokenDTO() {
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
