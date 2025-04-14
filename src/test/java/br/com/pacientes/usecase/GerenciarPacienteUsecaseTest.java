package br.com.pacientes.usecase;

import br.com.pacientes.cadastro.domain.Paciente;
import br.com.pacientes.cadastro.gateway.PacienteGateway;
import br.com.pacientes.cadastro.usecase.GerenciarPacienteUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GerenciarPacienteUsecaseTest {

    @InjectMocks
    private GerenciarPacienteUsecase gerenciarPacienteUsecase;

    @Mock
    private PacienteGateway pacienteGateway;

    private Paciente paciente;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        paciente = criarPaciente();
    }

    private Paciente criarPaciente() {
        return new Paciente(
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
    }

    @Test
    void deveCadastrarPaciente_QuandoPacienteValido() {
        gerenciarPacienteUsecase.cadastrarPaciente(paciente);
        verify(pacienteGateway, times(1)).cadastrarPaciente(paciente);
    }

    @Test
    void deveBuscarPacientePorNome_QuandoNomeEncontrado() {
        String nome = "João da Silva";
        List<Paciente> pacientesEsperados = Arrays.asList(paciente);

        when(pacienteGateway.buscarPacientePorNome(nome)).thenReturn(pacientesEsperados);

        List<Paciente> pacientesRetornados = gerenciarPacienteUsecase.buscarPacientePorNome(nome);

        assertAll(
                "Verifica os dados do paciente retornado",
                () -> assertEquals(1, pacientesRetornados.size(), "Deve retornar um paciente"),
                () -> assertEquals(paciente.getCpf(), pacientesRetornados.get(0).getCpf(), "CPF deve ser igual"),
                () -> assertEquals(paciente.getNome(), pacientesRetornados.get(0).getNome(), "Nome deve ser igual"),
                () -> assertEquals(paciente.getIdade(), pacientesRetornados.get(0).getIdade(), "Idade deve ser igual"),
                () -> assertEquals(paciente.getSexo(), pacientesRetornados.get(0).getSexo(), "Sexo deve ser igual"),
                () -> assertEquals(paciente.getCidade(), pacientesRetornados.get(0).getCidade(),"Cidade deve ser igual"),
                () -> assertEquals(paciente.getEndereco(), pacientesRetornados.get(0).getEndereco(), "Endereço deve ser igual"),
                () -> assertEquals(paciente.getCep(), pacientesRetornados.get(0).getCep(), "CEP deve ser igual"),
                () -> assertEquals(paciente.getEmail(), pacientesRetornados.get(0).getEmail(), "Email deve ser igual"),
                () -> assertEquals(paciente.getTelefone(), pacientesRetornados.get(0).getTelefone(), "Telefone deve ser igual")
        );

        verify(pacienteGateway, times(1)).buscarPacientePorNome(nome);
    }

    @Test
    void deveBuscarPacientePorCpf_QuandoCpfEncontrado() {
        String cpf = "12345678901";
        when(pacienteGateway.buscarPacientePorCpf(cpf)).thenReturn(Optional.of(paciente));

        Optional<Paciente> pacienteRetornado = gerenciarPacienteUsecase.buscarPacientePorCpf(cpf);

        assertTrue(pacienteRetornado.isPresent(), "Paciente deve estar presente");

        assertAll(
                "Verifica os dados do paciente retornado",
                () -> assertEquals(paciente.getCpf(), pacienteRetornado.get().getCpf(), "CPF deve ser igual"),
                () -> assertEquals(paciente.getNome(), pacienteRetornado.get().getNome(), "Nome deve ser igual"),
                () -> assertEquals(paciente.getIdade(), pacienteRetornado.get().getIdade(), "Idade deve ser igual"),
                () -> assertEquals(paciente.getSexo(), pacienteRetornado.get().getSexo(), "Sexo deve ser igual"),
                () -> assertEquals(paciente.getCidade(), pacienteRetornado.get().getCidade(),"Cidade deve ser igual"),
                () -> assertEquals(paciente.getEndereco(), pacienteRetornado.get().getEndereco(), "Endereço deve ser igual"),
                () -> assertEquals(paciente.getCep(), pacienteRetornado.get().getCep(), "CEP deve ser igual"),
                () -> assertEquals(paciente.getEmail(), pacienteRetornado.get().getEmail(), "Email deve ser igual"),
                () -> assertEquals(paciente.getTelefone(), pacienteRetornado.get().getTelefone(), "Telefone deve ser igual")
        );

        verify(pacienteGateway, times(1)).buscarPacientePorCpf(cpf);
    }

    @Test
    void deveAtualizarPaciente_QuandoPacienteEncontrado() {
        String cpf = "12345678901";
        when(pacienteGateway.atualizarPaciente(cpf, paciente)).thenReturn(Optional.of(paciente));

        Optional<Paciente> pacienteRetornado = gerenciarPacienteUsecase.atualizarPaciente(cpf, paciente);

        assertTrue(pacienteRetornado.isPresent(), "Paciente deve estar presente");

        assertAll(
                "Verifica os dados do paciente retornado",
                () -> assertEquals(paciente.getCpf(), pacienteRetornado.get().getCpf(), "CPF deve ser igual"),
                () -> assertEquals(paciente.getNome(), pacienteRetornado.get().getNome(), "Nome deve ser igual"),
                () -> assertEquals(paciente.getIdade(), pacienteRetornado.get().getIdade(), "Idade deve ser igual"),
                () -> assertEquals(paciente.getSexo(), pacienteRetornado.get().getSexo(), "Sexo deve ser igual"),
                () -> assertEquals(paciente.getCidade(), pacienteRetornado.get().getCidade(),"Cidade deve ser igual"),
                () -> assertEquals(paciente.getEndereco(), pacienteRetornado.get().getEndereco(), "Endereço deve ser igual"),
                () -> assertEquals(paciente.getCep(), pacienteRetornado.get().getCep(), "CEP deve ser igual"),
                () -> assertEquals(paciente.getEmail(), pacienteRetornado.get().getEmail(), "Email deve ser igual"),
                () -> assertEquals(paciente.getTelefone(), pacienteRetornado.get().getTelefone(), "Telefone deve ser igual")
        );

        verify(pacienteGateway, times(1)).atualizarPaciente(cpf, paciente);
    }


    @Test
    void deveRemoverPaciente_QuandoCpfValido() {
        String cpf = "12345678901";
        gerenciarPacienteUsecase.removerPaciente(cpf);
        verify(pacienteGateway, times(1)).removerPaciente(cpf);
    }

    @Test
    void deveBuscarPacientePorCpf_QuandoCpfNaoEncontrado() {
        String cpf = "99999999999";
        when(pacienteGateway.buscarPacientePorCpf(cpf)).thenReturn(Optional.empty());

        Optional<Paciente> pacienteRetornado = gerenciarPacienteUsecase.buscarPacientePorCpf(cpf);

        assertFalse(pacienteRetornado.isPresent(), "Paciente não deve estar presente");
        verify(pacienteGateway, times(1)).buscarPacientePorCpf(cpf);
    }
}