package br.com.pacientes.cadastro.exception;

public class CustomSystemException extends SystemBaseException {

    private final String code;
    private final Integer httpStatus;
    private final String message;

    public CustomSystemException(String code, Integer httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Integer getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}