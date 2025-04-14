package br.com.pacientes.cadastro.infraestrutura;

import br.com.pacientes.cadastro.exception.PacienteNaoEncontradoException;
import br.com.pacientes.cadastro.exception.IllegalArgumentException;
import br.com.pacientes.cadastro.exception.ErroAcessarRepositorioException;
import br.com.pacientes.cadastro.exception.SystemBaseException; // Importe a SystemBaseException
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PacienteNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> tratarErroPacienteNaoEncontradoException(PacienteNaoEncontradoException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND; // Use o HttpStatus da anotação @ResponseStatus
        return ResponseEntity.status(status).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ErroAcessarRepositorioException.class)
    public ResponseEntity<ErrorResponse> tratarErroAcessarRepositorioException(ErroAcessarRepositorioException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // Use o HttpStatus da anotação @ResponseStatus
        return ResponseEntity.status(status).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> tratarErroIllegalArgumentException(IllegalArgumentException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // Assumindo que IllegalArgumentException tem @ResponseStatus(HttpStatus.BAD_REQUEST)
        return ResponseEntity.status(status).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(SystemBaseException.class)
    public ResponseEntity<ErrorResponse> tratarSystemBaseException(SystemBaseException ex) {
        HttpStatus status = HttpStatus.resolve(ex.getHttpStatus());
        return ResponseEntity.status(status).body(new ErrorResponse(ex.getMessage()));
    }

    public record ErrorResponse(String message) { }
}