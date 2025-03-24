package br.com.pacientes.cadastro.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {
	private String cpf;
	private String nome;
	private String idade;
	private String sexo;
	private String endereco;
	private String cep;
	private String email;
	private String telefone;
}
