package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.bdd;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", 
		tags = "@BuscaStatusLoginCpf or @CriacaoLoginCidadaoPerguntas "
		+ "or @LoginCidadao or @RecuperacaoSenhaCidadao or @LiberacaoAcessoCidadaoEmpresa "
		+ "or @AlteracaoSenhaCidadao", 
glue = "br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.bdd", monochrome = true, dryRun = false)
public class CucumberTest {

}
