package br.com.pacientes.cadastro.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IllegalArgumentException extends SystemBaseException{
    private final String code = "paciente.dateTimeParseException";
    private final Integer httpStatus = 500;
    private String message;
}
