package com.example.APITarefas.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.example.APITarefas.enumerations.OpcaoRepeticaoNotificacao;

public record TarefaRecordDto(Long idContaUsuario, String titulo, String descricao, LocalDateTime dataHoraNotificacao,
		OpcaoRepeticaoNotificacao opcaoRepeticaoNotificacao, PersonalizacaoNotificacaoDto personalizacaoNotificacaoDto,
		List<Long> codigosEtiquetas) {

}
