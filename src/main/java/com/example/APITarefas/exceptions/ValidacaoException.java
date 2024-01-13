package com.example.APITarefas.exceptions;

import org.springframework.http.HttpStatus;

public class ValidacaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus;

	public ValidacaoException(String mensagem, HttpStatus httpStatus) {
		super(mensagem);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
