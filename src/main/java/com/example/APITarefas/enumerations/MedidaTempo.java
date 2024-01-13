package com.example.APITarefas.enumerations;

public enum MedidaTempo {
	DIA(1, "Dia"),
	SEMANA(2, "Semana"), 
	MES(3, "Mês"),
	ANO(4, "Ano");
	
	private int codigo;
	private String descricao;

	private MedidaTempo(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public MedidaTempo obterOpcaoPorCodigo(int codigo) {
		for (MedidaTempo opcao: MedidaTempo.values()) {
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
