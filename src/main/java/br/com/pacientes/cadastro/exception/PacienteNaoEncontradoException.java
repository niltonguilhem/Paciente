package br.com.pacientes.cadastro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PacienteNaoEncontradoException extends RuntimeException {

	public PacienteNaoEncontradoException() {
		super();
	}

	public PacienteNaoEncontradoException(String message) {
		super(message);
	}

	public int getHttpStatus() {
		return HttpStatus.NOT_FOUND.value();
	}
}