package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value="LoginTokenInterno", timeToLive=540)
public class LoginTokenInterno implements Serializable{
	
	private static final long serialVersionUID = 7872118548472855001L;
	@Id
	private String token;
	private String tipo;
	
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
