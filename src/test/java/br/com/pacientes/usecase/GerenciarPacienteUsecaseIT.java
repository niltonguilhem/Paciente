package br.com.pacientes.usecase;

import br.com.pacientes.cadastro.domain.Paciente;
import br.com.pacientes.cadastro.gateway.database.jpa.entity.PacienteEntity;
import br.com.pacientes.cadastro.gateway.database.jpa.repository.PacienteRepository;
import br.com.pacientes.cadastro.usecase.GerenciarPacienteUsecase;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = br.com.pacientes.cadastro.PacienteServiceApplication.class)
@AutoConfigureTestDatabase
@Transactional
public class GerenciarPacienteUsecaseIT {
    @Autowired
    private GerenciarPacienteUsecase gerenciarPacienteUsecase;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    @Sql(scripts = {"/clean.sql"})
    void deveCadastrarRestaurante() {
        Paciente paciente = new Paciente(
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

        gerenciarPacienteUsecase.cadastrarPaciente(paciente);

        List<PacienteEntity> pacientesCadastrados = pacienteRepository.findByNome("João da Silva");
        assertFalse(pacientesCadastrados.isEmpty(), "Deveria encontrar o paciente");
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/paciente.sql"})
    void deveBuscarPacientePorNome() {
        String nome = "João da Silva";

        List<Paciente> pacientesEsperados = Arrays.asList(new Paciente(
                "12345678901",
                "João da Silva",
                "30",
                "M",
                "São Paulo",
                "Rua A, 123",
                "12345-678",
                "joao@email.com",
                "11999999999"
        ));
        List<Paciente> pacientesRetornados = gerenciarPacienteUsecase.buscarPacientePorNome(nome);

        assertNotNull(pacientesRetornados);
        assertFalse(pacientesRetornados.isEmpty());
        assertEquals(pacientesEsperados.get(0).getCpf(), pacientesRetornados.get(0).getCpf());
        assertEquals(pacientesEsperados.get(0).getNome(), pacientesRetornados.get(0).getNome());
        assertEquals(pacientesEsperados.get(0).getIdade(), pacientesRetornados.get(0).getIdade());
        assertEquals(pacientesEsperados.get(0).getSexo(), pacientesRetornados.get(0).getSexo());
        assertEquals(pacientesEsperados.get(0).getCidade(), pacientesRetornados.get(0).getCidade());
        assertEquals(pacientesEsperados.get(0).getEndereco(), pacientesRetornados.get(0).getEndereco());
        assertEquals(pacientesEsperados.get(0).getCep(), pacientesRetornados.get(0).getCep());
        assertEquals(pacientesEsperados.get(0).getEmail(), pacientesRetornados.get(0).getEmail());
        assertEquals(pacientesEsperados.get(0).getTelefone(), pacientesRetornados.get(0).getTelefone());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/paciente.sql"})
    void deveAtualizarPaciente() {
        String cpf = "12345678901";

        Paciente pacienteAtualizado = new Paciente(
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

        Optional<Paciente> pacienteRetornado = gerenciarPacienteUsecase.atualizarPaciente(cpf, pacienteAtualizado);

        assertTrue(pacienteRetornado.isPresent());
        assertEquals(pacienteAtualizado.getCpf(), pacienteRetornado.get().getCpf());
        assertEquals(pacienteAtualizado.getNome(), pacienteRetornado.get().getNome());
        assertEquals(pacienteAtualizado.getIdade(), pacienteRetornado.get().getIdade());
        assertEquals(pacienteAtualizado.getSexo(), pacienteRetornado.get().getSexo());
        assertEquals(pacienteAtualizado.getCidade(), pacienteRetornado.get().getCidade());
        assertEquals(pacienteAtualizado.getEndereco(), pacienteRetornado.get().getEndereco());
        assertEquals(pacienteAtualizado.getCep(), pacienteRetornado.get().getCep());
        assertEquals(pacienteAtualizado.getEmail(), pacienteRetornado.get().getEmail());
        assertEquals(pacienteAtualizado.getTelefone(), pacienteRetornado.get().getTelefone());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/paciente.sql"})
    void deveRemoverPaciente() {
        String cpf = "12345678901";

        gerenciarPacienteUsecase.removerPaciente(cpf);

        Optional<PacienteEntity> pacienteRetornado = pacienteRepository.findByCpf(cpf);

        assertFalse(pacienteRetornado.isPresent(), "Não deveria encontrar o paciente");
    }
}
