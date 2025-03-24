package br.com.pacientes.cadastro.exception;

import lombok.Getter;

@Getter
public class PacienteNaoEncontradoException extends SystemBaseException {
	private final String code = "pacienteNaoEncontradoException";
	private final String message = "Paciente não encontrado.";
	private final Integer httpStatus = 404;
}
