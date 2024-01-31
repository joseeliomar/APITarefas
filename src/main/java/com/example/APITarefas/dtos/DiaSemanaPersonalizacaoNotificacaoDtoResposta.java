package com.example.APITarefas.dtos;

import java.util.Objects;

import com.example.APITarefas.entities.DiaSemanaPersonalizacaoNotificacao;
import com.example.APITarefas.enumerations.DiaSemana;

public class DiaSemanaPersonalizacaoNotificacaoDtoResposta {

	private final Long id;
	private final DiaSemana diaSemana;

	public DiaSemanaPersonalizacaoNotificacaoDtoResposta(
			DiaSemanaPersonalizacaoNotificacao diaSemanaPersonalizacaoNotificacao) {
		this.id = diaSemanaPersonalizacaoNotificacao.getId();
		this.diaSemana = diaSemanaPersonalizacaoNotificacao.getDiaSemana();
	}

	public Long getId() {
		return id;
	}

	public DiaSemana getDiaSemana() {
		return diaSemana;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiaSemanaPersonalizacaoNotificacaoDtoResposta other = (DiaSemanaPersonalizacaoNotificacaoDtoResposta) obj;
		return Objects.equals(id, other.id);
	}

}
