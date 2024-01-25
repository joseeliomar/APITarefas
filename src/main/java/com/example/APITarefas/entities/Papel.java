package com.example.APITarefas.entities;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;

import com.example.APITarefas.enumerations.PapelUsuario;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_papeis")
public class Papel implements GrantedAuthority {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private PapelUsuario papelUsuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PapelUsuario getPapelUsuario() {
		return papelUsuario;
	}

	public void setPapelUsuario(PapelUsuario papelUsuario) {
		this.papelUsuario = papelUsuario;
	}

	@Override
	public String getAuthority() {
		return papelUsuario.toString();
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
		Papel other = (Papel) obj;
		return Objects.equals(id, other.id);
	}

}
