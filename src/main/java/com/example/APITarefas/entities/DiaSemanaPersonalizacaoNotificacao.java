package com.example.APITarefas.entities;

import java.io.Serializable;
import java.util.Objects;

import com.example.APITarefas.enumerations.DiaSemana;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_dias_semana_personalizacoes_notificacoes")
public class DiaSemanaPersonalizacaoNotificacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "id_personalizacao_notificacao")
	private PersonalizacaoNotificacao personalizacaoNotificacao;

	@Enumerated(value = EnumType.STRING)
	private DiaSemana diaSemana;

	public DiaSemanaPersonalizacaoNotificacao(PersonalizacaoNotificacao personalizacaoNotificacao,
			DiaSemana diaSemana) {
		this.personalizacaoNotificacao = personalizacaoNotificacao;
		this.diaSemana = diaSemana;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PersonalizacaoNotificacao getPersonalizacaoNotificacao() {
		return personalizacaoNotificacao;
	}

	public void setPersonalizacaoNotificacao(PersonalizacaoNotificacao personalizacaoNotificacao) {
		this.personalizacaoNotificacao = personalizacaoNotificacao;
	}

	public DiaSemana getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(DiaSemana diaSemana) {
		this.diaSemana = diaSemana;
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
		DiaSemanaPersonalizacaoNotificacao other = (DiaSemanaPersonalizacaoNotificacao) obj;
		return Objects.equals(id, other.id);
	}

}
