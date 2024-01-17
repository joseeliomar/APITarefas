package com.example.APITarefas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.APITarefas.dtos.TarefaRecordDto;
import com.example.APITarefas.entities.Tarefa;
import com.example.APITarefas.services.TarefaService;

@RestController
@RequestMapping(value = "/tarefas")
public class TarefaController {

	@Autowired
	private TarefaService tarefaService;
	
	@PostMapping
	public ResponseEntity<Tarefa> criaTarefa(@RequestBody TarefaRecordDto tarefaRecordDto) {
		Tarefa tarefaCriadaSalva = this.tarefaService.criaTarefa(tarefaRecordDto);
		return ResponseEntity.created(null).body(tarefaCriadaSalva);  // falta criar a URI
	}
}
