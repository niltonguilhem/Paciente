package br.com.pacientes.cadastro.exception;

import lombok.Getter;

@Getter
public class ErroAcessarRepositorioException extends SystemBaseException {
	private final String code = "paciente.erroAcessarRepositorio";
	private final String message = "Erro ao acessar repositório.";
	private final Integer httpStatus = 500;
}
