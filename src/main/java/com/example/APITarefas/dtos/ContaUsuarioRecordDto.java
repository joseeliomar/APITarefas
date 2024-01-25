package com.example.APITarefas.dtos;

import java.util.List;

import com.example.APITarefas.enumerations.PapelUsuario;

public record ContaUsuarioRecordDto(String nomeUsuario, String email, String senha, List<PapelUsuario> papeisUsuario) {

}
