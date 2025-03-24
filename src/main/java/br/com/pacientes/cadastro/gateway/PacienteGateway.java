package br.com.pacientes.cadastro.gateway;

import br.com.pacientes.cadastro.domain.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteGateway {

	List<Paciente> buscarPacientePorNome(String nome);

	Optional<Paciente> buscarPacientePorCpf(String cpf);

	void cadastrarPaciente(Paciente paciente);

	Optional<Paciente> atualizarPaciente(String cpf, Paciente paciente);

	void removerPaciente(String cpf);
}
