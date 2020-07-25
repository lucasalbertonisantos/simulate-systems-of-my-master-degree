package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.builder.PerguntaBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.ColunaEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Telefone;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.PerguntaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CpfValidator;

@Service
public class PerguntaService {
	
	private static final String[] PERGUNTAS = {
			"Qual sua data de nascimento?",
			"Qual seu nome completo?",
			"Qual o nome completo da sua mãe?",
			"Qual o nome completo do seu pai?",
			"Quantos RGs possui?",
			"Qual/is é/são seu/s RG/s e qual/is unidade/s federativa/s?",
			"Qual email para contato?",
			"Qual telefone celular para contato?"
	};
	
	@Autowired
	private CidadaoService cidadaoService;
	
	@Autowired
	private CpfValidator cpfValidator;
	
	@Autowired
	private PerguntaRepository perguntaRepository;
	
	@Autowired
	private PerguntaBuilder perguntaBuilder;
	
	public List<Pergunta> findByCpf(String cpf){
		cpf = cpfValidator.validate(cpf);
		return perguntaRepository.findByCpfAndAtivaOrderByOrdemAsc(cpf, true);
	}
	
	public List<Pergunta> createIfNotExistOrNotUpdated(String cpf){
		cpf = cpfValidator.validate(cpf);
		Cidadao cidadao = cidadaoService.findByCpf(cpf);
		if(cidadao == null) {
			throw new RuntimeException("Cidadão não encontrado para o CPF informado");
		}
		List<Pergunta> perguntas = findByCpf(cpf);
		if(perguntas != null 
				&& !perguntas.isEmpty() 
				&& perguntas.get(0) != null
				&& perguntas.get(0).getDataCriacao() != null
				&& cidadao.getDataUltimaAtualizacao() != null
				&& perguntas.get(0).getDataCriacao().isAfter(cidadao.getDataUltimaAtualizacao())) {
			return perguntas;
		}else {
			if(perguntas != null && !perguntas.isEmpty()) {
				perguntas.forEach(pergunta->pergunta.setAtiva(false));
				perguntaRepository.saveAll(perguntas);
			}
			perguntas = new ArrayList<>();
			LocalDateTime now = LocalDateTime.now();
			int cont = 1;
			if(cidadao.getDataNascimento() != null) {
				perguntas.add(perguntaBuilder.build(cpf, cont, PERGUNTAS[0], now, ColunaEnum.DATA_NASCIMENTO));
				cont++;
			}
			if(cidadao.getNome() != null && !cidadao.getNome().isBlank()) {
				perguntas.add(perguntaBuilder.build(cpf, cont, PERGUNTAS[1], now, ColunaEnum.NOME));
				cont++;
			}
			if(cidadao.getNomeMae() != null && !cidadao.getNomeMae().isBlank()) {
				perguntas.add(perguntaBuilder.build(cpf, cont, PERGUNTAS[2], now, ColunaEnum.NOME_MAE));
				cont++;
			}
			if(cidadao.getNomePai() != null && !cidadao.getNomePai().isBlank()) {
				perguntas.add(perguntaBuilder.build(cpf, cont, PERGUNTAS[3], now, ColunaEnum.NOME_PAI));
				cont++;
			}
			if(cidadao.getRgs() != null && !cidadao.getRgs().isEmpty()) {
				perguntas.add(perguntaBuilder.build(cpf, cont, PERGUNTAS[4], now, ColunaEnum.QUANTOS_RGS_POSSUI));
				cont++;
				perguntas.add(perguntaBuilder.build(cpf, cont, PERGUNTAS[5], now, ColunaEnum.QUAIS_RGS_UFS));
				cont++;
			}
			if(cidadao.getEmails() != null && !cidadao.getEmails().isEmpty()) {
				perguntas.add(perguntaBuilder.build(cpf, cont, PERGUNTAS[6], now, ColunaEnum.EMAIL));
				cont++;
			}
			if(cidadao.getTelefones() != null && !cidadao.getTelefones().isEmpty()) {
				for(Telefone telefone : cidadao.getTelefones()) {
					if(telefone.isCelular()) {
						perguntas.add(perguntaBuilder.build(cpf, cont, PERGUNTAS[7], now, ColunaEnum.CELULAR));
						cont++;
						break;
					}
				}
			}
			if(cont == 1) {
				throw new RuntimeException("Não foi possível encontrar informação dentro do cidadão!");
			}
			perguntas = StreamSupport.stream(perguntaRepository.saveAll(perguntas).spliterator(), false).collect(Collectors.toList());
		}
		return perguntas;
	}

	public Pergunta findById(Long id) {
		Optional<Pergunta> pergunta = perguntaRepository.findById(id);
		if(pergunta.isPresent()) {
			return pergunta.get();
		}
		throw new RuntimeException("Pergunta não encontrada!");
	}

	public Pergunta update(Pergunta pergunta) {
		if(pergunta == null 
				|| pergunta.getId() == null
				|| pergunta.getColuna() == null
				|| pergunta.getCpf() == null) {
			throw new RuntimeException("Existem dados obrigatórios não preenchidos!");
		}
		return perguntaRepository.save(pergunta);
	}

}
