package com.example.APITarefas.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.APITarefas.entities.Etiqueta;
import com.example.APITarefas.entities.Tarefa;
import com.example.APITarefas.enumerations.OpcaoRepeticaoNotificacao;

public class TarefaDtoResposta {

	private final Long id;
	private final String titulo;
	private final String descricao;
	private final LocalDateTime dataHoraNotificacao;
	private final OpcaoRepeticaoNotificacao opcaoRepeticaoNotificacao;
	private final PersonalizacaoNotificacaoDtoResposta personalizacaoNotificacao;
	private final List<EtiquetaDtoResposta> etiquetas;
	private final ContaUsuarioDtoResposta contaUsuario;

	public TarefaDtoResposta(Tarefa tarefa) {
		this.id = tarefa.getId();
		this.titulo = tarefa.getTitulo();
		this.descricao = tarefa.getDescricao();
		this.dataHoraNotificacao = tarefa.getDataHoraNotificacao();
		this.opcaoRepeticaoNotificacao = tarefa.getOpcaoRepeticaoNotificacao();
		this.personalizacaoNotificacao = new PersonalizacaoNotificacaoDtoResposta(
				tarefa.getPersonalizacaoNotificacao());

		List<Etiqueta> etiquetas = tarefa.getEtiquetas();
		if (etiquetas != null) {
			this.etiquetas = etiquetas.stream().filter(e -> e != null).map(e -> new EtiquetaDtoResposta(e))
					.collect(Collectors.toList());
		} else {
			this.etiquetas = new ArrayList<>();
		}

		this.contaUsuario = new ContaUsuarioDtoResposta(tarefa.getContaUsuario());
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public LocalDateTime getDataHoraNotificacao() {
		return dataHoraNotificacao;
	}

	public OpcaoRepeticaoNotificacao getOpcaoRepeticaoNotificacao() {
		return opcaoRepeticaoNotificacao;
	}

	public PersonalizacaoNotificacaoDtoResposta getPersonalizacaoNotificacao() {
		return personalizacaoNotificacao;
	}

	public List<EtiquetaDtoResposta> getEtiquetas() {
		return etiquetas;
	}

	public ContaUsuarioDtoResposta getContaUsuario() {
		return contaUsuario;
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
		TarefaDtoResposta other = (TarefaDtoResposta) obj;
		return Objects.equals(id, other.id);
	}

}
