package com.example.APITarefas.enumerations;

public enum OpcaoRepeticaoNotificacao {
	NAO_REPETIR(1, "Não repetir"),
	DIARIAMENTE(2, "Diariamente"), 
	SEMANALMENTE(3, "Semanalmente"),
	MENSALMENTE(4, "Mensalmente"),
	ANUALMENTE(5, "Anualmente"),
	PERSONALIZADO(6, "Personalizado");
	
	private int codigo;
	private String descricao;

	private OpcaoRepeticaoNotificacao(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
