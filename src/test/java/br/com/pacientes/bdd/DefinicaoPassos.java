package br.com.pacientes.bdd;

import br.com.pacientes.cadastro.controller.json.PacienteJson;
import br.com.pacientes.cadastro.domain.Paciente;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DefinicaoPassos {

    private Response response;
    private PacienteJson pacienteJson;
    private String endpoint = "http://localhost:8080/api/v1/pacientes";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Dado("que eu tenho os dados de um paciente")
    public void dadoQueEuTenhoOsDadosDeUmPaciente() {
        pacienteJson = new PacienteJson("12345678901",
                "Jão da Silva",
                "30",
                "M",
                "São Paulo",
                "Rua Aura",
                "09981400",
                "nand.rodrigues@gmail.com",
                "11 985937410"
        );
    }

    @Quando("envio requisição para cadastrar o paciente")
    public void quandoEnvioRequisicaoParaCadastrarOPaciente() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pacienteJson)
                .when()
                .post(endpoint);
    }

    @Entao("o paciente deve ser cadastrado com sucesso")
    public void entaoOPacienteDeveSerCadastradoComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo("Paciente cadastrado com sucesso!"));
    }

    @Quando("envio requisição para buscar pacientes por nome")
    public void quandoEnvioRequisicaoParaBuscarPacientesPorNome() {
        response = given().get(endpoint + "/nome/{nome}", pacienteJson.getNome());
    }

    @Entao("a resposta deve conter os pacientes buscados por nome")
    public void entaoARespostaDeveConterOsPacientesBuscadosPorNome() {
        List<Paciente> pacientesRetornados = Arrays.asList(response.getBody().as(Paciente[].class));
        assertFalse(pacientesRetornados.isEmpty(), "A lista de pacientes não deve estar vazia");
        for (Paciente paciente : pacientesRetornados) {
            assertEquals(pacienteJson.getNome(), paciente.getNome());
        }
    }

    @Quando("envio requisição para buscar pacientes por cpf")
    public void quandoEnvioRequisicaoParaBuscarPacientesPorCpf() {
        response = given().get(endpoint + "/{cpf}", pacienteJson.getCpf());
    }

    @Entao("a resposta deve conter o paciente buscado por cpf")
    public void entaoARespostaDeveConterOPacienteBuscadoPorCpf() {
        Paciente pacienteRetornado = response.getBody().as(Paciente.class);
        assertEquals(pacienteJson.getCpf(), pacienteRetornado.getCpf());
    }

    @Quando("envio requisição para atualizar o paciente")
    public void quandoEnvioRequisicaoParaAtualizarOPaciente() {
        pacienteJson = new PacienteJson(
                pacienteJson.getCpf(),
                pacienteJson.getNome() + "_atualizado",
                pacienteJson.getIdade() + " atualizado",
                pacienteJson.getSexo() + " atualizado",
                pacienteJson.getCidade() + " atualizado",
                pacienteJson.getEndereco() + " atualizado",
                pacienteJson.getCep() + "_atualizado",
                pacienteJson.getEmail() + " atualizado",
                pacienteJson.getTelefone() + " atualizado"
        );
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pacienteJson)
                .put(endpoint + "/" + pacienteJson.getCpf());
    }

    @Entao("a resposta deve conter o paciente atualizado")
    public void entaoARespostaDeveConterOPacienteAtualizado() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        PacienteJson pacienteJsonRetornado = response.getBody().as(PacienteJson.class);
        assertEquals(pacienteJson.getCpf(), pacienteJsonRetornado.getCpf());
        assertEquals(pacienteJson.getNome(), pacienteJsonRetornado.getNome());
        assertEquals(pacienteJson.getIdade(), pacienteJsonRetornado.getIdade());
        assertEquals(pacienteJson.getSexo(), pacienteJsonRetornado.getSexo());
        assertEquals(pacienteJson.getEndereco(), pacienteJsonRetornado.getEndereco());
        assertEquals(pacienteJson.getCep(), pacienteJsonRetornado.getCep());
        assertEquals(pacienteJson.getEmail(), pacienteJsonRetornado.getEmail());
        assertEquals(pacienteJson.getTelefone(), pacienteJsonRetornado.getTelefone());
    }

    @Quando("envio requisição para remover o paciente")
    public void quandoEnvioRequisicaoParaRemoverOPaciente() {
        response = given().delete(endpoint + "/" + pacienteJson.getCpf());
    }

    @Entao("o paciente deve ser removido")
    public void entaoOPacienteDeveSerRemovido() {
        response.then().statusCode(HttpStatus.OK.value());
    }
}