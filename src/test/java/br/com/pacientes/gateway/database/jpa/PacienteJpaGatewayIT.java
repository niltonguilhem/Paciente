package br.com.pacientes.gateway.database.jpa;

import br.com.pacientes.cadastro.PacienteServiceApplication;
import br.com.pacientes.cadastro.domain.Paciente;
import br.com.pacientes.cadastro.exception.PacienteNaoEncontradoException;
import br.com.pacientes.cadastro.gateway.database.jpa.PacienteJpaGateway;
import br.com.pacientes.cadastro.gateway.database.jpa.entity.PacienteEntity;
import br.com.pacientes.cadastro.gateway.database.jpa.repository.PacienteRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PacienteServiceApplication.class)
@Import(PacienteJpaGateway.class)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:clean.sql"})
@Transactional
public class PacienteJpaGatewayIT {

    @Autowired
    private PacienteJpaGateway pacienteJpaGateway;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EntityManager entityManager;

    private Paciente pacienteAnderson;
    private PacienteEntity pacienteEntityAnderson;
    private PacienteEntity pacienteEntityJoao;

    @BeforeEach
    void setUp() {
        pacienteAnderson = new Paciente(
                "19276445854",
                "Anderson Rodrigues",
                "47",
                "M",
                "São Paulo",
                "Rua Aura",
                "09981400",
                "nand.rodrigues@gmail.com",
                "11 985937410"
        );

        pacienteEntityAnderson = new PacienteEntity(
                "19276445854",
                "Anderson Rodrigues",
                "47",
                "M",
                "São Paulo",
                "Rua Aura",
                "09981400",
                "nand.rodrigues@gmail.com",
                "11 985937410"
        );

        pacienteEntityJoao = new PacienteEntity(
                "11111111111",
                "João da Silva",
                "30",
                "M",
                "São Paulo",
                "Rua C",
                "30000000",
                "silva@email.com",
                "11111111111"
        );
    }

    @Test
    void cadastrarPaciente_DeveCadastrarPacienteComSucesso() {
        pacienteJpaGateway.cadastrarPaciente(pacienteAnderson);

        Optional<PacienteEntity> pacienteSalvo = pacienteRepository.findByCpf(pacienteAnderson.getCpf());
        assertTrue(pacienteSalvo.isPresent());
        assertEquals(pacienteAnderson.getCpf(), pacienteSalvo.get().getCpf());
        assertEquals(pacienteAnderson.getNome(), pacienteSalvo.get().getNome());
    }

    @Test
    void atualizarPaciente_DeveAtualizarPacienteComSucesso() {
        entityManager.persist(pacienteEntityAnderson);
        entityManager.flush();

        Paciente pacienteAtualizado = new Paciente(
                "19276445854",
                "Anderson Rodrigues Atualizado",
                "50",
                "M",
                "Rio de Janeiro",
                "Rua B",
                "20000000",
                "atualizado@email.com",
                "21999999999"
        );

        Optional<Paciente> pacienteRetornado = pacienteJpaGateway.atualizarPaciente(pacienteAnderson.getCpf(), pacienteAtualizado);

        assertTrue(pacienteRetornado.isPresent());
        assertEquals(pacienteAtualizado.getNome(), pacienteRetornado.get().getNome());
        assertEquals(pacienteAtualizado.getCidade(), pacienteRetornado.get().getCidade());
    }

    @Test
    void atualizarPaciente_DeveLancarPacienteNaoEncontradoException_QuandoPacienteNaoExiste() {
        Paciente pacienteNaoExistente = new Paciente(
                "99999999999",
                "Nome Qualquer",
                "20",
                "F",
                "Cidade Qualquer",
                "Endereco Qualquer",
                "CEP Qualquer",
                "email@qualquer.com",
                "telefone qualquer"
        );

        assertThrows(PacienteNaoEncontradoException.class, () -> {
            pacienteJpaGateway.atualizarPaciente(pacienteNaoExistente.getCpf(), pacienteNaoExistente);
        });
    }

    @Test
    void removerPaciente_DeveRemoverPacienteComSucesso() {
        entityManager.persist(pacienteEntityAnderson);
        entityManager.flush();

        pacienteJpaGateway.removerPaciente(pacienteAnderson.getCpf());

        Optional<PacienteEntity> pacienteRemovido = pacienteRepository.findByCpf(pacienteAnderson.getCpf());
        assertFalse(pacienteRemovido.isPresent());
    }

    @Test
    void buscarPacientePorNome_DeveLancarPacienteNaoEncontradoException_QuandoNenhumPacienteEncontrado() {
        assertThrows(PacienteNaoEncontradoException.class, () -> {
            pacienteJpaGateway.buscarPacientePorNome("NomeInexistente");
        });
    }

    @Test
    void buscarPacientePorCpf_DeveRetornarPacienteComSucesso() {
        entityManager.persist(pacienteEntityJoao);
        entityManager.flush();

        Optional<Paciente> pacienteRetornado = pacienteJpaGateway.buscarPacientePorCpf("11111111111");

        assertTrue(pacienteRetornado.isPresent());
        assertEquals("11111111111", pacienteRetornado.get().getCpf());
        assertEquals("João da Silva", pacienteRetornado.get().getNome());
    }

    @Test
    void buscarPacientePorCpf_DeveRetornarOptionalVazio_QuandoPacienteNaoEncontrado() {
        Optional<Paciente> pacienteRetornado = pacienteJpaGateway.buscarPacientePorCpf("CPFInexistente");
        assertFalse(pacienteRetornado.isPresent());
    }
}