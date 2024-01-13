package com.example.APITarefas.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.APITarefas.dtos.DetalhesExcecaoRecordDto;
import com.example.APITarefas.exceptions.ValidacaoException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ManipuladorExcecoes {

	/**
	 * Captura as exceções do tipo ValidacaoException e retorna detalhes da exceção.
	 * 
	 * @param excecao
	 * @param httpServletRequest
	 * @return detalhes da exceção.
	 */
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<DetalhesExcecaoRecordDto> validacao(ValidacaoException excecao,
			HttpServletRequest httpServletRequest) {
		HttpStatus httpStatus = excecao.getHttpStatus();

		DetalhesExcecaoRecordDto detalhesExcecao = new DetalhesExcecaoRecordDto(LocalDateTime.now(), httpStatus.value(),
				excecao.getMessage(), httpServletRequest.getRequestURI());

		return ResponseEntity.status(httpStatus).body(detalhesExcecao);
	}
}
