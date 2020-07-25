package br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.entity.PessoaFisica;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.service.PessoaFisicaService;

@RestController
@RequestMapping("/pessoas-fisicas")
public class PessoaFisicaAPI {
	
	@Autowired
	private PessoaFisicaService pessoaFisicaService;
	
	@PostMapping
	public PessoaFisica save(@RequestBody PessoaFisica pessoaFisica) {
		return pessoaFisicaService.save(pessoaFisica);
	}
	
	@PatchMapping
	public PessoaFisica update(@RequestBody PessoaFisica pessoaFisica) {
		return pessoaFisicaService.update(pessoaFisica);
	}
	
	@DeleteMapping
	public PessoaFisica delete(@RequestParam Long id) {
		return pessoaFisicaService.deleteById(id);
	}
	
	@DeleteMapping("/endereco")
	public PessoaFisica deleteEndereco(@RequestParam Long idPessoaFisica, 
			@RequestParam Long idEndereco) {
		return pessoaFisicaService.deleteEndereco(idPessoaFisica, idEndereco);
	}
	
	@DeleteMapping("/empresa-pagadora")
	public PessoaFisica deleteEmpresaPagadora(@RequestParam Long idPessoaFisica, 
			@RequestParam Long idEmpresaPagadora) {
		return pessoaFisicaService.deleteEmpresaPagadora(idPessoaFisica, idEmpresaPagadora);
	}
	
	@DeleteMapping("/telefone")
	public PessoaFisica deleteTelefone(@RequestParam Long idPessoaFisica, 
			@RequestParam Long idTelefone) {
		return pessoaFisicaService.deleteTelefone(idPessoaFisica, idTelefone);
	}
	
	@GetMapping
	public List<PessoaFisica> find(@RequestParam(name = "cpf", required = false) String cpf,
			@RequestParam(name = "id", required = false) Long id) {
		if(cpf != null) {
			return Arrays.asList(pessoaFisicaService.findByCpf(cpf));
		}else if(id != null) {
			return Arrays.asList(pessoaFisicaService.findById(id));
		}else {
			return pessoaFisicaService.findAll();
		}
	}

}
