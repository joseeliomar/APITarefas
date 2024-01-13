package com.example.APITarefas.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.APITarefas.enumerations.OpcaoRepeticaoNotificacao;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_tarefas")
public class Tarefa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "id_conta_usuario")
	private ContaUsuario contaUsuario;

	private String titulo;
	private String descricao;
	private LocalDateTime dataHoraCriacao;
	private LocalDateTime dataHoraAlteracao;
	private LocalDateTime dataHoraNotificacao;

	@Enumerated(value = EnumType.STRING)
	private OpcaoRepeticaoNotificacao opcaoRepeticaoNotificacao;

	private PersonalizacaoNotificacao personalizacaoNotificacao;

	@ManyToMany
	@JoinTable(name = "tb_tarefas_etiquetas", joinColumns = { @JoinColumn(name = "id_tarefa") }, inverseJoinColumns = {
			@JoinColumn(name = "id_projeto") })
	private List<Etiqueta> etiquetas = new ArrayList<>();

	public Tarefa(ContaUsuario contaUsuario, String titulo, String descricao, LocalDateTime dataHoraCriacao,
			LocalDateTime dataHoraAlteracao, LocalDateTime dataHoraNotificacao,
			OpcaoRepeticaoNotificacao opcaoRepeticaoNotificacao, PersonalizacaoNotificacao personalizacaoNotificacao) {
		this.contaUsuario = contaUsuario;
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataHoraCriacao = dataHoraCriacao;
		this.dataHoraAlteracao = dataHoraAlteracao;
		this.dataHoraNotificacao = dataHoraNotificacao;
		this.opcaoRepeticaoNotificacao = opcaoRepeticaoNotificacao;
		this.personalizacaoNotificacao = personalizacaoNotificacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ContaUsuario getContaUsuario() {
		return contaUsuario;
	}

	public void setContaUsuario(ContaUsuario contaUsuario) {
		this.contaUsuario = contaUsuario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getDataHoraCriacao() {
		return dataHoraCriacao;
	}

	public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) {
		this.dataHoraCriacao = dataHoraCriacao;
	}

	public LocalDateTime getDataHoraAlteracao() {
		return dataHoraAlteracao;
	}

	public void setDataHoraAlteracao(LocalDateTime dataHoraAlteracao) {
		this.dataHoraAlteracao = dataHoraAlteracao;
	}

	public LocalDateTime getDataHoraNotificacao() {
		return dataHoraNotificacao;
	}

	public void setDataHoraNotificacao(LocalDateTime dataHoraNotificacao) {
		this.dataHoraNotificacao = dataHoraNotificacao;
	}

	public OpcaoRepeticaoNotificacao getOpcaoRepeticaoNotificacao() {
		return opcaoRepeticaoNotificacao;
	}

	public void setOpcaoRepeticaoNotificacao(OpcaoRepeticaoNotificacao opcaoRepeticaoNotificacao) {
		this.opcaoRepeticaoNotificacao = opcaoRepeticaoNotificacao;
	}

	public PersonalizacaoNotificacao getPersonalizacaoNotificacao() {
		return personalizacaoNotificacao;
	}

	public void setPersonalizacaoNotificacao(PersonalizacaoNotificacao personalizacaoNotificacao) {
		this.personalizacaoNotificacao = personalizacaoNotificacao;
	}

	public List<Etiqueta> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(List<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
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
		Tarefa other = (Tarefa) obj;
		return Objects.equals(id, other.id);
	}

}
