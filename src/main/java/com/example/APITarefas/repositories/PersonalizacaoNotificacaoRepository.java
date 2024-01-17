package com.example.APITarefas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.APITarefas.entities.PersonalizacaoNotificacao;

@Repository
public interface PersonalizacaoNotificacaoRepository extends JpaRepository<PersonalizacaoNotificacao, Long> {

}
