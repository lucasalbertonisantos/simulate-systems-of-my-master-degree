package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.PerguntaApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.CidadaoPergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.CidadaoResposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.ColunaEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.StatusPerguntaFlow;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.CidadaoPerguntaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.CidadaoRespostaRepository;

@Service
public class PerguntaService {
	
	private static final String DELIMITADOR = "___";
	
	@Autowired
	private CidadaoPerguntaRepository cidadaoPerguntaRepository;
	
	@Autowired
	private CidadaoRespostaRepository cidadaoRespostaRepository;
	
	@Autowired
	private PerguntaApiClient perguntaApiClient;
	
	@Autowired
	private PerguntaUltimaPosicaoService perguntaUltimaPosicaoService;
	
	public StatusPerguntaFlow iniciar(String cpf) {
		cidadaoPerguntaRepository.deleteById(cpf);
		List<Pergunta> perguntas = perguntaApiClient.solicitar(cpf);
		if(perguntas == null || perguntas.isEmpty()) {
			throw new RuntimeException("Perguntas não encontradas na inicialização do cpf: " + cpf);
		}
		CidadaoPergunta cidadao = new CidadaoPergunta();
		cidadao.setCpf(cpf);
		cidadao.setPerguntas(perguntas);
		cidadaoPerguntaRepository.save(cidadao);
		StatusPerguntaFlow statusPerguntaFlow = new StatusPerguntaFlow();
		statusPerguntaFlow.setPergunta(cidadao.getPerguntas().get(0));
		statusPerguntaFlow.setStatus("");
		return statusPerguntaFlow;
	}
	
	public StatusPerguntaFlow solicitarPergunta(String cpf, Resposta resposta) {
		if(cpf == null || cpf.isBlank()) {
			throw new RuntimeException("Cpf não pode ser nulo!");
		}
		if(resposta == null 
				|| resposta.getResposta() == null 
				|| resposta.getResposta().isBlank()
				|| resposta.getPergunta() == null
				|| resposta.getPergunta().getId() == null
				|| resposta.getPergunta().getColuna() == null) {
			throw new RuntimeException("Existem dados obrigatórios não preenchidos na resposta!");
		}
		String chave = cpf+DELIMITADOR+resposta.getPergunta().getColuna().name();
		if(ColunaEnum.DATA_NASCIMENTO.equals(resposta.getPergunta().getColuna())){
			String[] dataNascimentoFormatada = resposta.getResposta().split("/");
			if(dataNascimentoFormatada.length != 3) {
				throw new RuntimeException("Existem dados obrigatórios não preenchidos na resposta!");
			}
			resposta.setResposta(dataNascimentoFormatada[2]+"-"+dataNascimentoFormatada[1]+"-"+dataNascimentoFormatada[0]);
		}
		Optional<CidadaoPergunta> cidadaoPerguntaO = cidadaoPerguntaRepository.findById(cpf);
		if(cidadaoPerguntaO.isEmpty()
				|| cidadaoPerguntaO.get().getPerguntas() == null
				|| cidadaoPerguntaO.get().getPerguntas().isEmpty()) {
			return iniciar(cpf);
		}
		Pergunta proximaPergunta = null;
		boolean encontrou = false;
		boolean ultimaPosicao = true;
		List<String> chaves = new ArrayList<>();
		for(Pergunta perguntaAtual : cidadaoPerguntaO.get().getPerguntas()) {
			if(perguntaAtual.getColuna() == null) {
				throw new RuntimeException("A coluna da pergunta não foi encontrada na pergunta atual!");
			}
			if(encontrou) {
				System.out.println(perguntaAtual.getColuna().name());
				proximaPergunta = perguntaAtual;
				ultimaPosicao = false;
				break;
			}
			if(perguntaAtual.getColuna().equals(resposta.getPergunta().getColuna())) {
				encontrou = true;
			}
			chaves.add(cpf+DELIMITADOR+perguntaAtual.getColuna());
		}
		cidadaoRespostaRepository.deleteById(chave);
		CidadaoResposta cidadaoResposta = new CidadaoResposta();
		cidadaoResposta.setCpfColuna(chave);
		cidadaoResposta.setResposta(resposta);
		cidadaoRespostaRepository.save(cidadaoResposta);
		if(ultimaPosicao) {
			return perguntaUltimaPosicaoService.processarUltimaPosicao(cpf, resposta, chaves);
		}
		StatusPerguntaFlow statusPerguntaFlow = new StatusPerguntaFlow();
		statusPerguntaFlow.setPergunta(proximaPergunta);
		statusPerguntaFlow.setStatus("");
		return statusPerguntaFlow;
	}

}
