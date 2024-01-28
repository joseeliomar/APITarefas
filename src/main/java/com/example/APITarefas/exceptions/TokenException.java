package com.example.APITarefas.exceptions;

import org.springframework.http.HttpStatus;

public class TokenException extends ExcecaoComStatusHttp {
	private static final long serialVersionUID = 1L;

	public TokenException(String mensagem, HttpStatus httpStatus) {
		super(mensagem, httpStatus);
	}

}
