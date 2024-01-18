package com.example.APITarefas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.APITarefas.entities.DiaSemanaPersonalizacaoNotificacao;

@Repository
public interface DiaSemanaPersonalizacaoNotificacaoRepository extends JpaRepository<DiaSemanaPersonalizacaoNotificacao, Long> {

}