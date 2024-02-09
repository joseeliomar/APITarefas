package com.example.APITarefas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.APITarefas.entities.Etiqueta;
import com.example.APITarefas.entities.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

	Optional<Tarefa> findByIdAndContaUsuario_Email(Long id, String emailContaUsuario);

	Page<Tarefa> findByContaUsuario_Email(String emailContaUsuario, Pageable pageable);

	List<Tarefa> findByEtiquetas(Etiqueta etiqueta);

}
