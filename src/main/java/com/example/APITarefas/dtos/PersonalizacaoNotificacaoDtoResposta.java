package com.example.APITarefas.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.APITarefas.entities.DiaSemanaPersonalizacaoNotificacao;
import com.example.APITarefas.entities.PersonalizacaoNotificacao;
import com.example.APITarefas.enumerations.MedidaTempo;

public class PersonalizacaoNotificacaoDtoResposta {

	private final Long id;
	private final int quantidade;
	private final MedidaTempo medidaTempo;
	private final List<DiaSemanaPersonalizacaoNotificacaoDtoResposta> diasSemana;

	public PersonalizacaoNotificacaoDtoResposta(PersonalizacaoNotificacao personalizacaoNotificacao) {
		this.id = personalizacaoNotificacao.getId();
		this.quantidade = personalizacaoNotificacao.getQuantidade();
		this.medidaTempo = personalizacaoNotificacao.getMedidaTempo();

		List<DiaSemanaPersonalizacaoNotificacao> diasSemanaPersonalizacaoNotificacao = personalizacaoNotificacao
				.getDiasSemanaPersonalizacaoNotificacao();
		if (diasSemanaPersonalizacaoNotificacao != null) {
			this.diasSemana = diasSemanaPersonalizacaoNotificacao.stream().filter(d -> d != null)
					.map(d -> new DiaSemanaPersonalizacaoNotificacaoDtoResposta(d)).collect(Collectors.toList());
		} else {
			this.diasSemana = new ArrayList<>();
		}
	}

	public Long getId() {
		return id;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public MedidaTempo getMedidaTempo() {
		return medidaTempo;
	}

	public List<DiaSemanaPersonalizacaoNotificacaoDtoResposta> getDiasSemana() {
		return diasSemana;
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
		PersonalizacaoNotificacaoDtoResposta other = (PersonalizacaoNotificacaoDtoResposta) obj;
		return Objects.equals(id, other.id);
	}

}
