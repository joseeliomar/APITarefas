package com.example.APITarefas.dtos;

import java.time.LocalDateTime;

public record DetalhesExcecaoRecordDto(LocalDateTime dataHoraExcecao, Integer status, String mensagem, String caminho) {

}
