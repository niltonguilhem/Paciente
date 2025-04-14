package br.com.pacientes.bdd;

import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@Cucumber
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class CucumberTest {
}
