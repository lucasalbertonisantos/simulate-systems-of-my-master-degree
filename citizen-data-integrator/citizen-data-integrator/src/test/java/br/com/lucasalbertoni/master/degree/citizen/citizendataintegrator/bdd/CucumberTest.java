package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.bdd;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", tags = "@BuscaStatusLoginCpf", 
glue = "br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.bdd.steps", monochrome = true, dryRun = false)
public class CucumberTest {

}
