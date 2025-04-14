package br.com.pacientes.infraestrutura;

import br.com.pacientes.cadastro.exception.CustomSystemException; // Importe a CustomSystemException
import br.com.pacientes.cadastro.exception.ErroAcessarRepositorioException;
import br.com.pacientes.cadastro.exception.IllegalArgumentException;
import br.com.pacientes.cadastro.exception.PacienteNaoEncontradoException;
import br.com.pacientes.cadastro.infraestrutura.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void deveTratarPacienteNaoEncontradoExceptionERetornarNotFoundComMensagemCorreta() {
        String mensagemEsperada = "Paciente não encontrado.";
        PacienteNaoEncontradoException exception = new PacienteNaoEncontradoException(mensagemEsperada);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                globalExceptionHandler.tratarErroPacienteNaoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(mensagemEsperada, response.getBody().message());
    }

    @Test
    void deveTratarErroAcessarRepositorioExceptionERetornarInternalServerErrorComMensagemCorreta() {
        String mensagemEsperada = "Erro ao acessar repositório.";
        ErroAcessarRepositorioException exception = new ErroAcessarRepositorioException(mensagemEsperada);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                globalExceptionHandler.tratarErroAcessarRepositorioException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(mensagemEsperada, response.getBody().message());
    }

    @Test
    void deveTratarIllegalArgumentExceptionERetornarBadRequestComMensagemCorreta() {
        String mensagemEsperada = "O argumento fornecido é inválido.";
        IllegalArgumentException exception = new IllegalArgumentException(mensagemEsperada);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                globalExceptionHandler.tratarErroIllegalArgumentException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(mensagemEsperada, response.getBody().message());
    }

    @Test
    void deveTratarSystemBaseExceptionERetornarHttpStatusCorretoComMensagemCorreta() {
        String codigoEsperado = "CUSTOM_ERROR";
        HttpStatus statusEsperado = HttpStatus.CONFLICT;
        String mensagemEsperada = "Ocorreu um erro customizado no sistema.";
        CustomSystemException exception = new CustomSystemException(codigoEsperado, statusEsperado.value(), mensagemEsperada);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                globalExceptionHandler.tratarSystemBaseException(exception);

        assertEquals(statusEsperado, response.getStatusCode());
        assertEquals(mensagemEsperada, response.getBody().message());
    }
}