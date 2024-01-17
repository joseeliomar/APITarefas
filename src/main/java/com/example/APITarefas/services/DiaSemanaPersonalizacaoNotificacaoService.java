package com.example.APITarefas.services;

import org.springframework.stereotype.Service;

import com.example.APITarefas.entities.DiaSemanaPersonalizacaoNotificacao;
import com.example.APITarefas.repositories.DiaSemanaPersonalizacaoNotificacaoRepository;

@Service
public class DiaSemanaPersonalizacaoNotificacaoService {

	private DiaSemanaPersonalizacaoNotificacaoRepository diaSemanaPersonalizacaoNotificacaoRepository;

	/**
	 * Cria um relacionamente entre um dia da semana e a personalização da
	 * notificação e salva esse relacionamento.
	 * 
	 * @param diaSemanaPersonalizacaoNotificacao
	 */
	public void criaDiaSemanaPersonalizacaoNotificacao(
			DiaSemanaPersonalizacaoNotificacao diaSemanaPersonalizacaoNotificacao) {
		this.diaSemanaPersonalizacaoNotificacaoRepository.save(diaSemanaPersonalizacaoNotificacao);
	}

}
