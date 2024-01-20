package com.example.APITarefas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.APITarefas.entities.DiaSemanaPersonalizacaoNotificacao;
import com.example.APITarefas.entities.PersonalizacaoNotificacao;

@Repository
public interface DiaSemanaPersonalizacaoNotificacaoRepository extends JpaRepository<DiaSemanaPersonalizacaoNotificacao, Long> {

	List<DiaSemanaPersonalizacaoNotificacao> findByPersonalizacaoNotificacao(
			PersonalizacaoNotificacao personalizacaoNotificacao);

}
