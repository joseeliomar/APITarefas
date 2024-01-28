package com.example.APITarefas.exceptions;

import org.springframework.http.HttpStatus;

public class AlgoritmoException extends ExcecaoComStatusHttp {
	private static final long serialVersionUID = 1L;

	public AlgoritmoException(String mensagem, HttpStatus httpStatus) {
		super(mensagem, httpStatus);
	}

}
