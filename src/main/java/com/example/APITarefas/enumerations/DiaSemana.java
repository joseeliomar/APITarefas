package com.example.APITarefas.enumerations;

public enum DiaSemana {
	DOMINGO(1, "Domingo"),
	SEGUNDA_FEIRA(2, "Segunda-feira"), 
	TERCA_FEIRA(3, "Terça-feira"),
	QUARTA_FEIRA(4, "Quarta-feira"),
	QUINTA_FEIRA(5, "Quinta-feira"),
	SEXTA_FEIRA(6, "Sexta-feira"),
	SABADO(7, "Sábado");
	
	private int codigo;
	private String descricao;

	private DiaSemana(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static DiaSemana obterOpcaoPorCodigo(int codigo) {
		for (DiaSemana opcao: DiaSemana.values()) {
			if (opcao.codigo == codigo) {
				return opcao;
			}
		}
		return null;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
