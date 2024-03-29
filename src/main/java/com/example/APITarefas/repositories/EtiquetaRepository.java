package com.example.APITarefas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.APITarefas.entities.Etiqueta;

@Repository
public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long> {
	
	Etiqueta findByTitulo(String tituloEtiqueta);
	
	Etiqueta findByTituloAndIdNot(String tituloEtiqueta, Long id);

}
