package com.example.APITarefas.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		return ResponseEntity.created(localizacaoRecursoCriado).body(tarefaInserida);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Tarefa> alteraTarefa(@PathVariable(value = "id") Long id,
			@RequestBody TarefaRecordDto tarefaRecordDto) {
		Tarefa tarefaAlterada = this.tarefaService.alteraTarefa(id, tarefaRecordDto);
		return ResponseEntity.ok(tarefaAlterada);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Tarefa> buscaTarefa(@PathVariable(value = "id") Long id) {
		Tarefa tarefa = this.tarefaService.buscaTarefa(id);
		return ResponseEntity.ok(tarefa);
	}

	@GetMapping
	public ResponseEntity<Page<Tarefa>> buscaTarefas(Pageable pageable) {
		Page<Tarefa> tarefas = this.tarefaService.buscaTarefas(pageable);
		if (tarefas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(tarefas);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeTarefa(@PathVariable(value = "id") Long id) {
		this.tarefaService.removeTarefa(id);
		return ResponseEntity.noContent().build();
	}
}
