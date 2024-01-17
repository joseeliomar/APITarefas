package com.example.APITarefas.dtos;

import java.util.List;

public record PersonalizacaoNotificacaoDto(int quantidade, Integer codigoMedidaTempo, List<Integer> codigosDiasSemana) {

}
