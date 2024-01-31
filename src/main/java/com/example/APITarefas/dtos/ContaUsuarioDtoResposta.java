package com.example.APITarefas.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.APITarefas.entities.ContaUsuario;
import com.example.APITarefas.entities.Papel;

public class ContaUsuarioDtoResposta {

	private final Long id;
	private final String nomeUsuario;
	private final String email;
	private final List<PapelDtoResposta> papeisUsuario;

	public ContaUsuarioDtoResposta(ContaUsuario contaUsuario) {
		this.id = contaUsuario.getId();
		this.nomeUsuario = contaUsuario.getNomeUsuario();
		this.email = contaUsuario.getEmail();

		List<Papel> papeis = contaUsuario.getPapeis();
		if (papeis != null) {
			this.papeisUsuario = contaUsuario.getPapeis().stream().filter(p -> p != null)
					.map(p -> new PapelDtoResposta(p.getPapelUsuario())).collect(Collectors.toList());
		} else {
			this.papeisUsuario = new ArrayList<>();
		}
	}

	public Long getId() {
		return id;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public String getEmail() {
		return email;
	}

	public List<PapelDtoResposta> getPapeisUsuario() {
		return papeisUsuario;
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
		ContaUsuarioDtoResposta other = (ContaUsuarioDtoResposta) obj;
		return Objects.equals(id, other.id);
	}

}
