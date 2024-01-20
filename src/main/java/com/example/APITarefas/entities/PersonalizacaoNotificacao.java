package com.example.APITarefas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.APITarefas.enumerations.MedidaTempo;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_personalizacoes_notificacoes")
public class PersonalizacaoNotificacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int quantidade;

	@Enumerated(value = EnumType.STRING)
	private MedidaTempo medidaTempo;

	@OneToMany(mappedBy = "personalizacaoNotificacao")
	private List<DiaSemanaPersonalizacaoNotificacao> diasSemanaPersonalizacaoNotificacao = new ArrayList<>();
	
	public PersonalizacaoNotificacao() {
	}

	public PersonalizacaoNotificacao(int quantidade, MedidaTempo medidaTempo) {
		this.quantidade = quantidade;
		this.medidaTempo = medidaTempo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public MedidaTempo getMedidaTempo() {
		return medidaTempo;
	}

	public void setMedidaTempo(MedidaTempo medidaTempo) {
		this.medidaTempo = medidaTempo;
	}

	public List<DiaSemanaPersonalizacaoNotificacao> getDiasSemanaPersonalizacaoNotificacao() {
		return diasSemanaPersonalizacaoNotificacao;
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
		PersonalizacaoNotificacao other = (PersonalizacaoNotificacao) obj;
		return Objects.equals(id, other.id);
	}

}
