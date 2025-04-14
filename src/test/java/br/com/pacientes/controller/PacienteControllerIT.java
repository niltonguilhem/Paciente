package br.com.pacientes.controller;

import br.com.pacientes.cadastro.PacienteServiceApplication;
import br.com.pacientes.cadastro.controller.json.PacienteJson;
import br.com.pacientes.cadastro.gateway.database.jpa.entity.PacienteEntity;
import br.com.pacientes.cadastro.gateway.database.jpa.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PacienteServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PacienteControllerIT {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    private String getUrl(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    @Sql(scripts = {"/clean.sql"})
    void deveCadastrarPaciente() throws Exception {
        PacienteJson pacienteJson = new PacienteJson(
                "12345678901",
                "João da Silva",
                "30",
                "M",
                "São Paulo",
                "Rua A, 123",
                "12345-678",
                "joao@email.com",
                "11999999999"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(pacienteJson), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(getUrl("/api/v1/pacientes"), request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Paciente cadastrado com sucesso!", response.getBody());

        List<PacienteEntity> pacientesCadastrados = pacienteRepository.findByNome("João da Silva");
        assertFalse(pacientesCadastrados.isEmpty(), "Deveria encontrar o paciente");
        assertEquals(pacienteJson.getCpf(), pacientesCadastrados.get(0).getCpf());
        assertEquals(pacienteJson.getNome(), pacientesCadastrados.get(0).getNome());
        assertEquals(pacienteJson.getIdade(), pacientesCadastrados.get(0).getIdade());
        assertEquals(pacienteJson.getSexo(), pacientesCadastrados.get(0).getSexo());
        assertEquals(pacienteJson.getCidade(), pacientesCadastrados.get(0).getCidade());
        assertEquals(pacienteJson.getEndereco(), pacientesCadastrados.get(0).getEndereco());
        assertEquals(pacienteJson.getCep(), pacientesCadastrados.get(0).getCep());
        assertEquals(pacienteJson.getEmail(), pacientesCadastrados.get(0).getEmail());
        assertEquals(pacienteJson.getTelefone(), pacientesCadastrados.get(0).getTelefone());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/paciente.sql"})
    void deveBuscarPacientePorNome() throws Exception {
        String nome = "João da Silva";

        ResponseEntity<List<PacienteJson>> response = restTemplate.exchange(
                getUrl("/api/v1/pacientes/nome/" + nome),
                HttpMethod.GET,
                null,
                new org.springframework.core.ParameterizedTypeReference<List<PacienteJson>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<PacienteJson> pacientesRetornados = response.getBody();

        assertFalse(pacientesRetornados.isEmpty());
        assertEquals("12345678901", pacientesRetornados.get(0).getCpf());
        assertEquals("João da Silva", pacientesRetornados.get(0).getNome());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/paciente.sql"})
    void deveAtualizarPaciente() throws Exception {
        String cpf = "12345678901";

        PacienteJson pacienteAtualizado = new PacienteJson(
                "12345678901",
                "Novo Nome",
                "40",
                "F",
                "Nova Cidade",
                "Novo Endereco",
                "98765-432",
                "novo@email.com",
                "22222222222"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(pacienteAtualizado), headers);

        ResponseEntity<PacienteJson> response = restTemplate.exchange(
                getUrl("/api/v1/pacientes/" + cpf),
                HttpMethod.PUT,
                request,
                PacienteJson.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(pacienteAtualizado.getCpf(), response.getBody().getCpf());
        assertEquals(pacienteAtualizado.getNome(), response.getBody().getNome());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/paciente.sql"})
    void deveRemoverPaciente() {
        String cpf = "12345678901";

        restTemplate.delete(getUrl("/api/v1/pacientes/" + cpf));

        Optional<PacienteEntity> pacienteRetornado = pacienteRepository.findByCpf(cpf);

        assertFalse(pacienteRetornado.isPresent(), "Não deveria encontrar o paciente");
    }
}