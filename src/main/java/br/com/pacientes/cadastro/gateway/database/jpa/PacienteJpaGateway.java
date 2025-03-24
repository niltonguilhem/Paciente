package br.com.pacientes.cadastro.gateway.database.jpa;

import br.com.pacientes.cadastro.domain.Paciente;
import br.com.pacientes.cadastro.exception.PacienteNaoEncontradoException;
import br.com.pacientes.cadastro.exception.ErroAcessarRepositorioException;
import br.com.pacientes.cadastro.gateway.PacienteGateway;
import br.com.pacientes.cadastro.gateway.database.jpa.entity.PacienteEntity;
import br.com.pacientes.cadastro.gateway.database.jpa.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PacienteJpaGateway implements PacienteGateway {


	private final PacienteRepository pacienteRepository;

	@Override
	public void cadastrarPaciente(Paciente paciente) {
		try {
			PacienteEntity pacienteEntity = mapToEntity(paciente);
			log.info("Salvando paciente no banco: {}", pacienteEntity.getCpf());
			pacienteRepository.save(pacienteEntity);

		}catch (Exception e){
			log.error("Erro ao salvar paciente no banco", e);
			throw new ErroAcessarRepositorioException();
		}

	}

	@Override
	public Optional<Paciente> atualizarPaciente(String cpf, Paciente paciente) {
		Optional<PacienteEntity> pacienteEntity = pacienteRepository.findByCpf(cpf);
		if(pacienteEntity.isEmpty()){
			throw new PacienteNaoEncontradoException();
		}
		return Optional.of(mapToDomain(pacienteRepository.save(mapToEntity(paciente))));
	}

	@Override
	public void removerPaciente(String cpf) {
		try {
			pacienteRepository.deleteByCpf(cpf);
		}catch (Exception e){
			throw new ErroAcessarRepositorioException();
		}
	}

	@Override
	public List<Paciente> buscarPacientePorNome(String nome) {
		List<PacienteEntity> pacientes = pacienteRepository.findByNome(nome);

		if(pacientes.isEmpty()){
			throw new PacienteNaoEncontradoException();
		}

		return pacientes.stream().map(this::mapToDomain).collect(Collectors.toList());
	}

	@Override
	public Optional<Paciente> buscarPacientePorCpf(String cpf) {
		return pacienteRepository.findByCpf(cpf)
				.map(this::mapToDomain);
	}

	private Paciente mapToDomain(PacienteEntity pacienteEntity) {
		return new Paciente(
				pacienteEntity.getCpf(),
				pacienteEntity.getNome(),
				pacienteEntity.getIdade(),
				pacienteEntity.getSexo(),
				pacienteEntity.getEndereco(),
				pacienteEntity.getCep(),
				pacienteEntity.getEmail(),
				pacienteEntity.getTelefone()
		);
	}
	
	private PacienteEntity mapToEntity(Paciente paciente) {
		return new PacienteEntity(
				paciente.getCpf(),
				paciente.getNome(),
				paciente.getIdade(),
				paciente.getSexo(),
				paciente.getEndereco(),
				paciente.getCep(),
				paciente.getEmail(),
				paciente.getTelefone()
		);
	}
}
