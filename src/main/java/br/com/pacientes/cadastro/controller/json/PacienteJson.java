package br.com.pacientes.cadastro.controller.json;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PacienteJson {
	@Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
	private String cpf;

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@NotBlank(message = "Idade é obrigatório")
	private String idade;

	@NotBlank(message = "Informe o sexo, campo obrigatório")
	private String sexo;

	@NotBlank(message = "Endereço é obrigatório")
	private String endereco;

	@Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato 00000-000")
	private String cep;

	@NotBlank(message = "E-mail é obrigatório")
	private String email;

	@NotBlank(message = "Telefone é obrigratório")
	private String telefone;
}