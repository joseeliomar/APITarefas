package com.example.APITarefas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.APITarefas.entities.Papel;

@Repository
public interface PapelRepository extends JpaRepository<Papel, Long> {

}
