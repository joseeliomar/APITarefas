package com.example.APITarefas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.APITarefas.entities.DiaSemanaPersonalizacaoNotificacao;
import com.example.APITarefas.repositories.DiaSemanaPersonalizacaoNotificacaoRepository;

@Service
public class DiaSemanaPersonalizacaoNotificacaoService {

	@Autowired
	private DiaSemanaPersonalizacaoNotificacaoRepository diaSemanaPersonalizacaoNotificacaoRepository;

	/**
	 * Insere um dia da semana associado com a personalização da notificação, ou seja, cria
	 * um relacionamente entre um dia da semana e a personalização da notificação e
	 * insere esse relacionamento no banco de dados.
	 * 
	 * @param diaSemanaPersonalizacaoNotificacao
	 * @return um dia da semana associado com a personalização da notificação.
	 */
	public DiaSemanaPersonalizacaoNotificacao insereDiaSemanaPersonalizacaoNotificacao(
			DiaSemanaPersonalizacaoNotificacao diaSemanaPersonalizacaoNotificacao) {
		return this.diaSemanaPersonalizacaoNotificacaoRepository.save(diaSemanaPersonalizacaoNotificacao);
	}

}
