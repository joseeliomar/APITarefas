package com.example.APITarefas.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record TarefaRecordDto(Long idContaUsuario, String titulo, String descricao, LocalDateTime dataHoraNotificacao,
		int codigoOpcaoRepeticaoNotificacao, PersonalizacaoNotificacaoDto personalizacaoNotificacaoDto,
		List<Long> codigosEtiquetas) {

}
