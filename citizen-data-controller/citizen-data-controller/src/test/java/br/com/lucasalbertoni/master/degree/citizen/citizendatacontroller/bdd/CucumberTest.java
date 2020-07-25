package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.bdd;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", 
tags = "@ServicoAutenticacao or @ValidacaoToken "
		+ "or @CriacaoAlteracaoEmpresa or @CriacaoAlteracaoLoginEmpresa or @BuscaStatusLoginCpf "
		+ "or @CriacaoLoginCidadaoPerguntas or @LoginCidadao or @RecuperacaoSenhaCidadao "
		+ "or @LiberacaoAcessoCidadaoEmpresa or @AlteracaoSenhaCidadao "
		+ "or @ConsultaDadosCidadaoEmpresa", 
glue = "br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.bdd.steps",
monochrome = true, dryRun = false)
public class CucumberTest {

}
