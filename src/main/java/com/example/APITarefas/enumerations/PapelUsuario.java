package com.example.APITarefas.enumerations;

public enum PapelUsuario {
	ROLE_ADMIN("ROLE_ADMIN");
	
	private String nome;
	
	PapelUsuario(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
}
