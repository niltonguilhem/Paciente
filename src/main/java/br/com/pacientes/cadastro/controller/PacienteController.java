package br.com.pacientes.cadastro.controller;

import br.com.pacientes.cadastro.controller.json.PacienteJson;
import br.com.pacientes.cadastro.domain.Paciente;
import br.com.pacientes.cadastro.usecase.GerenciarPacienteUsecase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/pacientes")
@RequiredArgsConstructor
public class PacienteController {

	private final GerenciarPacienteUsecase gerenciarPacienteUsecase;

	@PostMapping
	public ResponseEntity<String> cadastrar(@Valid @RequestBody PacienteJson pacienteJson) {
		System.out.println("Paciente a ser salvo: " + pacienteJson.getCpf());
		gerenciarPacienteUsecase.cadastrarPaciente(mapToDomain(pacienteJson));
		return ResponseEntity.ok("Paciente cadastrado com sucesso!");
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<PacienteJson> buscarPacientePorCpf(@PathVariable String cpf) {
		return gerenciarPacienteUsecase.buscarPacientePorCpf(cpf)
				.map(paciente -> ResponseEntity.ok(mapToJson(paciente)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("nome/{nome}")
	public ResponseEntity<List<PacienteJson>> buscarPacientePorNome(@PathVariable String nome) {
		List<Paciente> pacientes = gerenciarPacienteUsecase.buscarPacientePorNome(nome);
		List<PacienteJson> pacienteJsons = pacientes.stream().map(this::mapToJson).collect(Collectors.toList());
		return ResponseEntity.ok(pacienteJsons);
	}

	@PutMapping("/{cpf}")
	public ResponseEntity<?> atualizarPaciente(@PathVariable String cpf, @RequestBody PacienteJson pacienteJson) {
		Optional<Paciente> paciente = gerenciarPacienteUsecase.atualizarPaciente(cpf, mapToDomain(pacienteJson));
		return paciente.map(c -> ResponseEntity.ok(mapToJson(c))).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{cpf}")
	public ResponseEntity<Void> removerPaciente(@PathVariable String cpf) {
		gerenciarPacienteUsecase.removerPaciente(cpf);
		return ResponseEntity.noContent().build();
	}

	private Paciente mapToDomain(PacienteJson pacienteJson) {
		return new Paciente(
				pacienteJson.getCpf(),
				pacienteJson.getNome(),
				pacienteJson.getIdade(),
				pacienteJson.getSexo(),
				pacienteJson.getEndereco(),
				pacienteJson.getCep(),
				pacienteJson.getEmail(),
				pacienteJson.getTelefone()
		);
	}

	private PacienteJson mapToJson(Paciente paciente) {
		return new PacienteJson(
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