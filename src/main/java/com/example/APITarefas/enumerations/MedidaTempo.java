package com.example.APITarefas.enumerations;

public enum MedidaTempo {
	DIA(1, "Dia", "Dias"),
	SEMANA(2, "Semana", "Semanas"), 
	MES(3, "Mês", "Meses"),
	ANO(4, "Ano", "Anos");
	
	private int codigo;
	private String descricao;
	private String palavraPlural;

	private MedidaTempo(int codigo, String descricao, String palavraPlural) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.palavraPlural = palavraPlural;
	}
	
	public static MedidaTempo obterOpcaoPorCodigo(int codigo) {
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

	public String getPalavraPlural() {
		return palavraPlural;
	}
	
}
