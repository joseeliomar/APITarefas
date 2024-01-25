package com.example.APITarefas.enumerations;

public enum PapelUsuario {
	ROLE_ADMIN("Administrador");
	
	private String descricao;
	
	PapelUsuario(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
