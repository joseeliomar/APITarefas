package com.example.APITarefas.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.APITarefas.dtos.TarefaRecordDto;
import com.example.APITarefas.entities.Tarefa;
import com.example.APITarefas.services.TarefaService;
import com.example.APITarefas.utils.Utils;

@RestController
@RequestMapping(value = "/tarefas")
public class TarefaController {

	@Autowired
	private TarefaService tarefaService;
	
	@PostMapping
	public ResponseEntity<Tarefa> insereTarefa(@RequestBody TarefaRecordDto tarefaRecordDto) {
		Tarefa tarefaInserida = this.tarefaService.insereTarefa(tarefaRecordDto);
		URI localizacaoRecursoCriado = Utils.obtemLocalizacaoRecursoCriado(tarefaInserida.getId());
		
//		tarefaInserida.setEtiquetas(null);
		
		return ResponseEntity.created(localizacaoRecursoCriado).body(tarefaInserida);
	}
}
