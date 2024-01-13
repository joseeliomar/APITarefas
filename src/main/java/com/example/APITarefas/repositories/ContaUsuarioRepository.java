package com.example.APITarefas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.APITarefas.entities.ContaUsuario;

@Repository
public interface ContaUsuarioRepository extends JpaRepository<ContaUsuario, Long> {

	ContaUsuario findByEmail(String emailUsuario);
	
	ContaUsuario findByEmailAndIdNot(String emailUsuario, Long id);

}
