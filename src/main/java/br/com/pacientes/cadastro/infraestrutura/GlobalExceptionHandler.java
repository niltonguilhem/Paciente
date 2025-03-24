package br.com.pacientes.cadastro.infraestrutura;

import br.com.pacientes.cadastro.exception.PacienteNaoEncontradoException;
import br.com.pacientes.cadastro.exception.ErroAcessarRepositorioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PacienteNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> tratarErroClienteNaoEncontradoException(PacienteNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.valueOf(ex.getHttpStatus())).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ErroAcessarRepositorioException.class)
    public ResponseEntity<ErrorResponse> tratarErroAcessarRepositorioException(ErroAcessarRepositorioException ex) {
        return ResponseEntity.status(HttpStatus.valueOf(ex.getHttpStatus())).body(new ErrorResponse(ex.getMessage()));
    }

    public record ErrorResponse(String message) { }
}
