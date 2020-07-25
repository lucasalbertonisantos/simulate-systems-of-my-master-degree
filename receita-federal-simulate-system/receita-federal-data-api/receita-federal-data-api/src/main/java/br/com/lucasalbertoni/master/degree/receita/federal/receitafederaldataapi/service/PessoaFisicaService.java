package br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.entity.EmpresaPagadora;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.entity.Endereco;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.entity.PessoaFisica;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.entity.Telefone;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.repository.EmpresaPagadoraRepository;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.repository.EnderecoRepository;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.repository.PessoaFisicaRepository;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.repository.TelefoneRepository;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.validate.CpfValidator;

@Service
public class PessoaFisicaService {
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private EmpresaPagadoraRepository empresaPagadoraRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private CpfValidator cpfValidator;
	
	@Autowired
	private EnderecoService enderecoService;
	
	@Autowired
	private EmpresaPagadoraService empresaPagadoraService;
	
	@Autowired
	private TelefoneService telefoneService;

	public PessoaFisica save(PessoaFisica pessoaFisica) {
		if(pessoaFisica.getId() != null) {
			throw new RuntimeException("O id deve ser nulo!");
		}
		validacaoDadosBasicos(pessoaFisica);
		if(pessoaFisica.getEnderecos() != null && !pessoaFisica.getEnderecos().isEmpty()) {
			for(Endereco endereco : pessoaFisica.getEnderecos()) {
				endereco.setPessoaFisica(pessoaFisica);
			}
		}
		if(pessoaFisica.getEmpresasPagadoras() != null && !pessoaFisica.getEmpresasPagadoras().isEmpty()) {
			for(EmpresaPagadora empresaPagadora : pessoaFisica.getEmpresasPagadoras()) {
				empresaPagadora.setPessoaFisica(pessoaFisica);
			}
		}
		pessoaFisica.setDataUltimaAtualizacao(LocalDateTime.now());
		pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
		return pessoaFisica;
	}

	public PessoaFisica update(PessoaFisica pessoaFisica) {
		if(pessoaFisica.getId() == null) {
			throw new RuntimeException("Deve conter id");
		}
		validacaoDadosBasicos(pessoaFisica);
		findById(pessoaFisica.getId());
		if(pessoaFisica.getEnderecos() != null && !pessoaFisica.getEnderecos().isEmpty()) {
			for(Endereco endereco : pessoaFisica.getEnderecos()) {
				endereco.setPessoaFisica(pessoaFisica);
			}
		}
		if(pessoaFisica.getEmpresasPagadoras() != null && !pessoaFisica.getEmpresasPagadoras().isEmpty()) {
			for(EmpresaPagadora empresaPagadora : pessoaFisica.getEmpresasPagadoras()) {
				empresaPagadora.setPessoaFisica(pessoaFisica);
			}
		}
		pessoaFisica.setDataUltimaAtualizacao(LocalDateTime.now());
		pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
		return findByIdForcandoEndereco(pessoaFisica.getId());
	}
	
	public List<PessoaFisica> findAll(){
		List<PessoaFisica> pessoasFisicas = new ArrayList<>();
		pessoaFisicaRepository.findAll().forEach(pessoasFisicas::add);
		return pessoasFisicas;
	}
	
	public PessoaFisica findByCpf(String cpf){
		cpf = cpfValidator.validate(cpf);
		PessoaFisica pessoaFisica = pessoaFisicaRepository.findByCpf(cpf);
		if(pessoaFisica == null) {
			throw new RuntimeException("PessoaFisica não encontrada");
		}
		return pessoaFisica;
	}
	
	public PessoaFisica findById(Long id) {
		Optional<PessoaFisica> pessoaFisica = pessoaFisicaRepository.findById(id);
		if(!pessoaFisica.isPresent()) {
			throw new RuntimeException("PessoaFisica não encontrada");
		}
		return pessoaFisica.get();
	}

	private void validacaoDadosBasicos(PessoaFisica pessoaFisica) {
		if(pessoaFisica.getCpf() == null || pessoaFisica.getCpf().isBlank()) {
			throw new RuntimeException("Deve conter um cpf");
		}
		pessoaFisica.setCpf(cpfValidator.validate(pessoaFisica.getCpf()));
		if(pessoaFisica.getNome() == null || pessoaFisica.getNome().isBlank()) {
			throw new RuntimeException("Deve conter um nome");
		}
		if(pessoaFisica.getNomeMae() == null || pessoaFisica.getNomeMae().isBlank()) {
			throw new RuntimeException("Deve conter um nome da mae");
		}
		if(pessoaFisica.getNomePai() == null || pessoaFisica.getNomePai().isBlank()) {
			throw new RuntimeException("Deve conter um nome do pai");
		}
		if(pessoaFisica.getSituacaoCadastral() == null || pessoaFisica.getSituacaoCadastral().isBlank()) {
			throw new RuntimeException("Deve conter uma situacao cadastral");
		}
		if(pessoaFisica.getEnderecos() != null && !pessoaFisica.getEnderecos().isEmpty()) {
			pessoaFisica.getEnderecos().forEach(endereco->{
				endereco.setPessoaFisica(pessoaFisica);
				endereco.setId(null);
			});
		}
		if(pessoaFisica.getEmpresasPagadoras() != null && !pessoaFisica.getEmpresasPagadoras().isEmpty()) {
			pessoaFisica.getEmpresasPagadoras().forEach(empresaPagadora->{
				empresaPagadora.setPessoaFisica(pessoaFisica);
				empresaPagadora.setId(null);
			});
		}
		if(pessoaFisica.getTelefones() != null && !pessoaFisica.getTelefones().isEmpty()) {
			pessoaFisica.getTelefones().forEach(telefone->{
				telefone.setPessoaFisica(pessoaFisica);
				telefone.setId(null);
			});
		}
	}

	public PessoaFisica deleteById(Long id) {
		PessoaFisica pessoaFisica = findById(id);
		pessoaFisicaRepository.deleteById(id);
		return pessoaFisica;
	}

	public PessoaFisica deleteEndereco(Long idPessoaFisica, Long idEndereco) {
		Endereco endereco = enderecoService.findById(idEndereco);
		PessoaFisica pessoaFisica = findById(idPessoaFisica);
		if(endereco.getPessoaFisica().getId() != pessoaFisica.getId()) {
			throw new RuntimeException("Endereco não pertence a pessoa física informada!");
		}
		enderecoService.deleteById(idEndereco);
		return findByIdForcandoEndereco(idPessoaFisica);
	}

	public PessoaFisica deleteEmpresaPagadora(Long idPessoaFisica, Long idEmpresaPagadora) {
		EmpresaPagadora empresaPagadora = empresaPagadoraService.findById(idEmpresaPagadora);
		PessoaFisica pessoaFisica = findById(idPessoaFisica);
		if(empresaPagadora.getPessoaFisica().getId() != pessoaFisica.getId()) {
			throw new RuntimeException("Empresa Pagadora não pertence a pessoa física informada!");
		}
		empresaPagadoraService.deleteById(idEmpresaPagadora);
		return findByIdForcandoEndereco(idPessoaFisica);
	}

	public PessoaFisica deleteTelefone(Long idPessoaFisica, Long idTelefone) {
		Telefone telefone = telefoneService.findById(idTelefone);
		PessoaFisica pessoaFisica = findById(idPessoaFisica);
		if(telefone.getPessoaFisica().getId() != pessoaFisica.getId()) {
			throw new RuntimeException("Telefone não pertence a pessoa física informada!");
		}
		telefoneService.deleteById(idTelefone);
		return findByIdForcandoEndereco(idPessoaFisica);
	}
	
	private PessoaFisica findByIdForcandoEndereco(Long id) {
		PessoaFisica cidadaoRetornadar = findById(id);
		cidadaoRetornadar.setEnderecos(enderecoRepository.findByPessoaFisicaId(id));
		cidadaoRetornadar.setEmpresasPagadoras(empresaPagadoraRepository.findByPessoaFisicaId(id));
		cidadaoRetornadar.setTelefones(telefoneRepository.findByPessoaFisicaId(id));
		return cidadaoRetornadar;
	}

}
