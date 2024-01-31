package com.example.APITarefas.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.APITarefas.entities.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

	Optional<Tarefa> findByIdAndContaUsuario_Email(Long id, String email);

	Page<Tarefa> findByContaUsuario_Email(String email, Pageable pageable);

}
