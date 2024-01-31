package com.example.APITarefas.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.APITarefas.dtos.TarefaDtoResposta;
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
	public ResponseEntity<TarefaDtoResposta> insereTarefa(@AuthenticationPrincipal UserDetails userDetails,
			@RequestBody TarefaRecordDto tarefaRecordDto) {
		Tarefa tarefaInserida = this.tarefaService.insereTarefa(userDetails.getUsername(), tarefaRecordDto);
		TarefaDtoResposta tarefaDtoResposta = new TarefaDtoResposta(tarefaInserida);
		URI localizacaoRecursoCriado = Utils.obtemLocalizacaoRecursoCriado(tarefaInserida.getId());
		return ResponseEntity.created(localizacaoRecursoCriado).body(tarefaDtoResposta);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TarefaDtoResposta> alteraTarefa(@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long id, @RequestBody TarefaRecordDto tarefaRecordDto) {
		Tarefa tarefaAlterada = this.tarefaService.alteraTarefa(userDetails.getUsername(), id, tarefaRecordDto);
		TarefaDtoResposta tarefaDtoResposta = new TarefaDtoResposta(tarefaAlterada);
		return ResponseEntity.ok(tarefaDtoResposta);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TarefaDtoResposta> buscaTarefa(@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long id) {
		Tarefa tarefa = this.tarefaService.buscaTarefa(userDetails.getUsername(), id);
		TarefaDtoResposta tarefaDtoResposta = new TarefaDtoResposta(tarefa);
		return ResponseEntity.ok(tarefaDtoResposta);
	}

	@GetMapping
	public ResponseEntity<Page<TarefaDtoResposta>> buscaTarefas(@AuthenticationPrincipal UserDetails userDetails,
			Pageable pageable) {
		Page<Tarefa> tarefas = this.tarefaService.buscaTarefas(userDetails.getUsername(), pageable);
		if (tarefas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Page<TarefaDtoResposta> tarefaDtoResposta = tarefas.map(t -> new TarefaDtoResposta(t));

		return ResponseEntity.ok(tarefaDtoResposta);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeTarefa(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
		this.tarefaService.removeTarefa(userDetails.getUsername(), id);
		return ResponseEntity.noContent().build();
	}
}
