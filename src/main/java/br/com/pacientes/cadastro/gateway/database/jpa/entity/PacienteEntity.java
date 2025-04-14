package br.com.pacientes.cadastro.gateway.database.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.lang.annotation.Documented;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Paciente")
@Entity
public class PacienteEntity {
	@Id
	@Column(unique = true)
	private String cpf;
	private String nome;
	private String idade;
	private String sexo;
	private String cidade;
	private String endereco;
	private String cep;
	private String email;
	private String telefone;
}
