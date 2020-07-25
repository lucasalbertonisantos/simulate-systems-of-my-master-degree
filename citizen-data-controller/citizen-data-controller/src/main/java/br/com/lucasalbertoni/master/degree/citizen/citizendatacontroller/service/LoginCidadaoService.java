package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.LoginRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.utils.Encryption;

@Service
public class LoginCidadaoService {

	private static final String LOGIN_NULL = "Login não pode ser nulo!";
	private static final String LOGIN_INCONSISTENTE = "Login já existe para esse Documento!";
	
	@Autowired
	private CidadaoService cidadaoService;
	
	@Autowired
	private RespostaService respostaService;
	
	@Autowired
	private PerguntaService perguntaService;
	
	@Autowired
	private Encryption encryption;
	
	@Autowired
	private LoginRepository loginRepository;
	
	public Login create(Login login) {
		if(login == null) {
			throw new RuntimeException(LOGIN_NULL);
		}
		Cidadao cidadao = cidadaoService.findByCpf(login.getCpfCnpj());
		List<Resposta> respostas = respostaService.findByCpf(login.getCpfCnpj());
		if(respostas == null || respostas.isEmpty()) {
			throw new RuntimeException(LOGIN_INCONSISTENTE);
		}
		List<Pergunta> perguntas = perguntaService.findByCpf(login.getCpfCnpj());
		if(perguntas == null || perguntas.isEmpty() || perguntas.size() != respostas.size()) {
			throw new RuntimeException(LOGIN_INCONSISTENTE);
		}
		if(login.getEmail() == null || login.getEmail().isBlank()) {
			throw new RuntimeException(LOGIN_INCONSISTENTE);
		}
		if(login.getSenha() == null || login.getSenha().isBlank()) {
			throw new RuntimeException(LOGIN_INCONSISTENTE);
		}
		cidadao.setRespostas(new HashSet<>());
		cidadao.setPerguntas(new HashSet<>());
		for(Resposta resposta : respostas) {
			resposta.setCidadao(cidadao);
			cidadao.getRespostas().add(resposta);
		}
		for(Pergunta pergunta : perguntas) {
			pergunta.setCidadao(cidadao);
			cidadao.getPerguntas().add(pergunta);
		}
		cidadaoService.save(cidadao);
		login.setSenha(encryption.encrypt(login.getSenha()));
		login.setDataCriacao(LocalDateTime.now());
		Login loginSalvo = loginRepository.save(login);
		loginSalvo.setSenha(null);
		return loginSalvo;
	}

}
