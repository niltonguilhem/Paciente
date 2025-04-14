package br.com.pacientes.bdd;

import br.com.pacientes.cadastro.domain.Paciente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteResponse {
    private List<Paciente> pacientes;
    private String message;
}
