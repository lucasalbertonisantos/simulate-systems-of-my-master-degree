package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class GeradorSenhaService {
	
	public String gerarSenha() {
		char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚÃÕÂÊÎÔÛÀÈÌÒÙÇ".toCharArray();
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<8; i++) {
			int valor = random.nextInt();
			if((valor % 2) == 0) {
				sb.append(letras[random.nextInt(letras.length)]);
			}else {
				sb.append(random.nextInt(10));
			}
		}
		return sb.toString();
	}

}
