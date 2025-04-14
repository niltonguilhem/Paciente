package br.com.pacientes.gateway.database.jpa;

import br.com.pacientes.cadastro.domain.Paciente;
import br.com.pacientes.cadastro.exception.PacienteNaoEncontradoException;
import br.com.pacientes.cadastro.exception.ErroAcessarRepositorioException;
import br.com.pacientes.cadastro.gateway.database.jpa.PacienteJpaGateway;
import br.com.pacientes.cadastro.gateway.database.jpa.entity.PacienteEntity;
import br.com.pacientes.cadastro.gateway.database.jpa.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PacienteJpaGatewayTest {
    @InjectMocks
    private PacienteJpaGateway pacienteJpaGateway;

    @Mock
    private PacienteRepository pacienteRepository;

    private PacienteEntity pacienteEntityJoao;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        pacienteEntityJoao = new PacienteEntity(
                "12345678901",
                "João da Silva",
                "30",
                "M",
                "Rio de Janeiro",
                "Rua ABC",
                "20000000",
                "joao@email.com",
                "21 99999999"
        );
    }

    @Test
    void deveCadastrarPaciente() {
        Paciente paciente = new Paciente(
                "12345678901",
                "Jão da Silva",
                "47",
                "M",
                "São Paulo",
                "Rua Aura",
                "09981400",
                "nand.rodrigues@gmail.com",
                "11 985937410"
        );

        PacienteEntity pacienteEntityEsperado = new PacienteEntity(
                "12345678901",
                "Jão da Silva",
                "47",
                "M",
                "São Paulo",
                "Rua Aura",
                "09981400",
                "nand.rodrigues@gmail.com",
                "11 985937410"
        );

        pacienteJpaGateway.cadastrarPaciente(paciente);

        ArgumentCaptor<PacienteEntity> captor = ArgumentCaptor.forClass(PacienteEntity.class);
        verify(pacienteRepository, times(1)).save(captor.capture());

        PacienteEntity pacienteEntityCapturado = captor.getValue();

        assertEquals(pacienteEntityEsperado.getCpf(), pacienteEntityCapturado.getCpf());
        assertEquals(pacienteEntityEsperado.getNome(), pacienteEntityCapturado.getNome());
        assertEquals(pacienteEntityEsperado.getIdade(), pacienteEntityCapturado.getIdade());
        assertEquals(pacienteEntityEsperado.getSexo(), pacienteEntityCapturado.getSexo());
        assertEquals(pacienteEntityEsperado.getCidade(), pacienteEntityCapturado.getCidade());
        assertEquals(pacienteEntityEsperado.getEndereco(), pacienteEntityCapturado.getEndereco());
        assertEquals(pacienteEntityEsperado.getCep(), pacienteEntityCapturado.getCep());
        assertEquals(pacienteEntityEsperado.getEmail(), pacienteEntityCapturado.getEmail());
        assertEquals(pacienteEntityEsperado.getTelefone(), pacienteEntityCapturado.getTelefone());
    }

    @Test
    void buscarPacientePorCpf_DeveRetornarPacienteComSucesso() {
        when(pacienteRepository.findByCpf("12345678901")).thenReturn(Optional.of(pacienteEntityJoao));

        Optional<Paciente> pacienteRetornado = pacienteJpaGateway.buscarPacientePorCpf("12345678901");

        assertTrue(pacienteRetornado.isPresent());
        assertEquals("12345678901", pacienteRetornado.get().getCpf());
        assertEquals("João da Silva", pacienteRetornado.get().getNome());
    }

    @Test
    void buscarPacientePorCpf_DeveRetornarOptionalVazio_QuandoPacienteNaoEncontrado() {
        Optional<Paciente> pacienteRetornado = pacienteJpaGateway.buscarPacientePorCpf("CPFInexistente");
        assertFalse(pacienteRetornado.isPresent());
        when(pacienteRepository.findByCpf("CPFInexistente")).thenReturn(Optional.empty());
    }

    @Test
    void deveBuscarPacientePorNome() {
        String nome = "Anderson Rodrigues";

        List<PacienteEntity> pacientesEsperados = Arrays.asList(new PacienteEntity(
                "12345678901",
                "Anderson Rodrigues",
                "47",
                "M",
                "São Paulo",
                "Rua Aura",
                "09981400",
                "nand.rodrigues@gmail.com",
                "11 985937410"
        ));

        Mockito.when(pacienteRepository.findByNome(nome)).thenReturn(pacientesEsperados);

        List<Paciente> pacientesRetornados = pacienteJpaGateway.buscarPacientePorNome(nome);

        assertEquals(pacientesEsperados.get(0).getCpf(), pacientesRetornados.get(0).getCpf());
        assertEquals(pacientesEsperados.get(0).getNome(), pacientesRetornados.get(0).getNome());
        assertEquals(pacientesEsperados.get(0).getIdade(), pacientesRetornados.get(0).getIdade());
        assertEquals(pacientesEsperados.get(0).getSexo(), pacientesRetornados.get(0).getSexo());
        assertEquals(pacientesEsperados.get(0).getCidade(), pacientesRetornados.get(0).getCidade());
        assertEquals(pacientesEsperados.get(0).getEndereco(), pacientesRetornados.get(0).getEndereco());
        assertEquals(pacientesEsperados.get(0).getCep(), pacientesRetornados.get(0).getCep());
        assertEquals(pacientesEsperados.get(0).getEmail(), pacientesRetornados.get(0).getEmail());
        assertEquals(pacientesEsperados.get(0).getTelefone(), pacientesRetornados.get(0).getTelefone());
        Mockito.verify(pacienteRepository, Mockito.times(1)).findByNome(nome);
    }

    @Test
    void deveBuscarPacientePorNome_LancarPacienteNaoEncontradoException_QuandoPacienteNaoForEncontrado() {
        String nome = "Anderson Rodrigues";

        Mockito.when(pacienteRepository.findByNome(nome)).thenReturn(Collections.emptyList());

        assertThrows(PacienteNaoEncontradoException.class, () -> {
            pacienteJpaGateway.buscarPacientePorNome(nome);
        });
        Mockito.verify(pacienteRepository, Mockito.times(1)).findByNome(nome);
    }
    @Test
    void deveAtualizarPaciente() {
        String cpf = "12345678901";

        Paciente pacienteAtualizado = new Paciente(
                cpf,
                "Anderson Rodrigues",
                "47",
                "M",
                "São Paulo",
                "Rua Aura",
                "09981400",
                "nand.rodrigues@gmail.com",
                "11 985937410"
        );

        Optional<PacienteEntity> pacienteAntigoRetornado = Optional.of(new PacienteEntity(
                "12345678901",
                "Anderson Rodrigues",
                "47",
                "M",
                "São Paulo",
                "Rua Aura",
                "09981400",
                "nand.rodrigues@gmail.com",
                "11 985937410"
        ));

        PacienteEntity pacienteAtualizadoSalvo = new PacienteEntity(
                cpf,
                "Anderson Rodrigues",
                "47",
                "M",
                "São Paulo",
                "Rua Aura",
                "09981400",
                "nand.rodrigues@gmail.com",
                "11 985937410"
        );

        Mockito.when(pacienteRepository.findByCpf(cpf)).thenReturn(pacienteAntigoRetornado);
        Mockito.when(pacienteRepository.save(any(PacienteEntity.class))).thenReturn(pacienteAtualizadoSalvo);

        Optional<Paciente> pacienteRetornado = pacienteJpaGateway.atualizarPaciente(cpf, pacienteAtualizado);

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

        verify(pacienteRepository, times(1)).save(any(PacienteEntity.class));
    }

    @Test
    void deveAtualizarPaciente_LancarPacienteNaoEncontradoException_QuandoPacienteNaoForEncontrado() {
        String cpf = "19276445854";

        Paciente pacienteAtualizado = new Paciente(
                cpf,
                "Anderson Rodrigues",
                "47",
                "M",
                "São Paulo",
                "Rua Aura",
                "09981400",
                "nand.rodrigues@gmail.com",
                "11 985937410"
        );

        Mockito.when(pacienteRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(PacienteNaoEncontradoException.class, () -> {
            pacienteJpaGateway.atualizarPaciente(cpf, pacienteAtualizado);
        });
        Mockito.verify(pacienteRepository, Mockito.times(1)).findByCpf(cpf);
    }

    @Test
    void deveRemoverPaciente() {
        String cpf = "12345678901";

        pacienteJpaGateway.removerPaciente(cpf);

        Mockito.verify(pacienteRepository, Mockito.times(1)).deleteByCpf(cpf);
    }

    @Test
    void criar_DeveLancarErroAoAcessarRepositorioException_QuandoOcorreErro() {

        Paciente paciente = new Paciente();

        when(pacienteRepository.save(any(PacienteEntity.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        assertThrows(ErroAcessarRepositorioException.class, () -> pacienteJpaGateway.cadastrarPaciente(paciente));
        verify(pacienteRepository, times(1)).save(any(PacienteEntity.class));
    }

}
