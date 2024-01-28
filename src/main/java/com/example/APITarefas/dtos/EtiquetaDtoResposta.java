package com.example.APITarefas.dtos;

import java.util.Objects;

import com.example.APITarefas.entities.Etiqueta;

public class EtiquetaDtoResposta {

	private final Long id;
	private final String titulo;

	public EtiquetaDtoResposta(Etiqueta etiqueta) {
		this.id = etiqueta.getId();
		this.titulo = etiqueta.getTitulo();
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
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
		EtiquetaDtoResposta other = (EtiquetaDtoResposta) obj;
		return Objects.equals(id, other.id);
	}

}
