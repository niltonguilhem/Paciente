package br.com.pacientes.cadastro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ErroAcessarRepositorioException extends RuntimeException {

	public ErroAcessarRepositorioException() {
		super();
	}

	public ErroAcessarRepositorioException(String message) {
		super(message);
	}
}