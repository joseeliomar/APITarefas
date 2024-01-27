package com.example.APITarefas.dtos;

import java.util.List;

import com.example.APITarefas.enumerations.DiaSemana;
import com.example.APITarefas.enumerations.MedidaTempo;

public record PersonalizacaoNotificacaoDto(int quantidade, MedidaTempo medidaTempo, List<DiaSemana> diasSemana) {

}
