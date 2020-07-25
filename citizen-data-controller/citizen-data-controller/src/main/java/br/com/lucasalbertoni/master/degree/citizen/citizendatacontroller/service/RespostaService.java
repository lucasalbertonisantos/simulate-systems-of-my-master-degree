package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.ColunaEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.RespostaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.TokenConfirmarCadastroService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta.RespostaColunaCelular;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta.RespostaColunaDataNascimento;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta.RespostaColunaEmail;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta.RespostaColunaNome;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta.RespostaColunaNomeMae;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta.RespostaColunaNomePai;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta.RespostaColunaQuaisRGs;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta.RespostaColunaQuantosRGs;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta.RespostaColunaStrategy;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CpfValidator;

@Service
public class RespostaService {
	
	private static final String ERRO_RESPOSTAS_INVALIDAS = "Respostas são inválidas!";
	
	@Value("${citizen.controller.data.cidadao.liberacao.acesso.url}")
	private String url;
	
	@Autowired
	private RespostaRepository respostaRepository;
	
	@Autowired
	private PerguntaService perguntaService;
	
	@Autowired
	private CidadaoService cidadaoService;
	
	@Autowired
	private TokenConfirmarCadastroService tokenConfirmarCadastroService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private GeradorSenhaService geradorSenhaService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private CpfValidator cpfValidator;
	
	private Map<ColunaEnum, RespostaColunaStrategy> respostaColuna;
	
	private Map<ColunaEnum, RespostaColunaStrategy> getStrategyMap(){
		if(respostaColuna == null) {
			respostaColuna = new HashMap<>();
			respostaColuna.put(ColunaEnum.DATA_NASCIMENTO, new RespostaColunaDataNascimento());
			respostaColuna.put(ColunaEnum.CELULAR, new RespostaColunaCelular());
			respostaColuna.put(ColunaEnum.EMAIL, new RespostaColunaEmail());
			respostaColuna.put(ColunaEnum.NOME, new RespostaColunaNome());
			respostaColuna.put(ColunaEnum.NOME_MAE, new RespostaColunaNomeMae());
			respostaColuna.put(ColunaEnum.NOME_PAI, new RespostaColunaNomePai());
			respostaColuna.put(ColunaEnum.QUAIS_RGS_UFS, new RespostaColunaQuaisRGs());
			respostaColuna.put(ColunaEnum.QUANTOS_RGS_POSSUI, new RespostaColunaQuantosRGs());
		}
		return respostaColuna;
	}
	
	private RespostaColunaStrategy getAvaliador(ColunaEnum coluna) {
		return getStrategyMap().get(coluna);
	}
	
	public List<Resposta> avaliarSalvar(String cpf, List<Resposta> respostas, String email){
		cpf = cpfValidator.validate(cpf);
		List<Pergunta> perguntas = perguntaService.findByCpf(cpf);
		Cidadao cidadao = cidadaoService.findByCpf(cpf);
		if(cidadao == null || perguntas == null || respostas == null || perguntas.isEmpty() || perguntas.size() != respostas.size()) {
			throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
		}
		LocalDateTime dataCriacao = LocalDateTime.now();
		for(Resposta resposta : respostas) {
			resposta.setCpf(cpf);
			resposta.setDataCriacao(dataCriacao);
			resposta.setAtiva(true);
			if(resposta.getPergunta() == null || resposta.getPergunta().getId() == null 
					|| resposta.getResposta() == null || resposta.getResposta().isBlank()
					|| resposta.getId() != null) {
				throw new RuntimeException(ERRO_RESPOSTAS_INVALIDAS);
			}
			Pergunta pergunta = perguntaService.findById(resposta.getPergunta().getId());
			if(pergunta == null) {
				throw new RuntimeException("Pergunta não encontrada!");
			}
			RespostaColunaStrategy respostaStrategy = getAvaliador(pergunta.getColuna());
			if(respostaStrategy == null) {
				throw new RuntimeException("Pergunta inválida!");
			}
			respostaStrategy.avaliar(resposta, cidadao);
			if(ColunaEnum.EMAIL.equals(pergunta.getColuna())) {
				email = resposta.getResposta();
			}
			resposta.setCidadao(cidadao);
		}
		List<Resposta> respostasExistentes = findByCpf(cpf);
		if(respostasExistentes != null && !respostasExistentes.isEmpty()) {
			respostasExistentes.forEach(respostaExistente->respostaExistente.setAtiva(false));
			respostaRepository.saveAll(respostasExistentes);
		}
		for(Pergunta pergunta : perguntas) {
			pergunta.setCidadao(cidadao);
			perguntaService.update(pergunta);
		}
		respostaRepository.saveAll(respostas);
		String token = tokenConfirmarCadastroService.gerarTokenEnvioEmail(cpf);
		String senhaGerada = geradorSenhaService.gerarSenha();
		emailService.sendToken(url, token, senhaGerada, email);
		loginService.gerarLoginCidadaoRespostas(cpf, email, senhaGerada);
		return findByCpf(cpf);
	}
	
	public List<Resposta> findByCpf(String cpf){
		return respostaRepository.findByCpfAndAtiva(cpf, true);
	}

}
