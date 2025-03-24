package br.com.pacientes.cadastro.usecase;

import br.com.pacientes.cadastro.domain.Paciente;
import br.com.pacientes.cadastro.gateway.PacienteGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GerenciarPacienteUsecase {

	private final PacienteGateway pacienteGateway;

	public void cadastrarPaciente(Paciente paciente) {
        pacienteGateway.cadastrarPaciente(paciente);
    }

    public List<Paciente> buscarPacientePorNome(String nome){

        return pacienteGateway.buscarPacientePorNome(nome);
    }

    public Optional<Paciente> buscarPacientePorCpf(String cpf) {
        return pacienteGateway.buscarPacientePorCpf(cpf);
}

    public Optional<Paciente> atualizarPaciente(String cpf, Paciente paciente){
        return pacienteGateway.atualizarPaciente(cpf, paciente);
    }

    public void removerPaciente(String cpf){
        pacienteGateway.removerPaciente(cpf);
    }
}
