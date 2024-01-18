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

import com.example.APITarefas.dtos.EtiquetaDto;
import com.example.APITarefas.entities.Etiqueta;
import com.example.APITarefas.services.EtiquetaService;
import com.example.APITarefas.utils.Utils;

@RestController
@RequestMapping(value = "etiquetas")
public class EtiquetaController {

	@Autowired
	private EtiquetaService etiquetaService;

	@PostMapping
	public ResponseEntity<Etiqueta> insereEtiqueta(@RequestBody EtiquetaDto etiquetaDto) {
		Etiqueta etiquetaInserida = this.etiquetaService.insereEtiqueta(etiquetaDto);
		URI localizacaoRecursoCriado = Utils.obtemLocalizacaoRecursoCriado(etiquetaInserida.getId());
		return ResponseEntity.created(localizacaoRecursoCriado).body(etiquetaInserida);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Etiqueta> alteraEtiqueta(@PathVariable(value = "id") Long id,
			@RequestBody EtiquetaDto etiquetaDto) {
		Etiqueta etiquetaAlterada = this.etiquetaService.alteraEtiqueta(id, etiquetaDto);
		return ResponseEntity.ok(etiquetaAlterada);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Etiqueta> buscaEtiqueta(@PathVariable(value = "id") Long id) {
		Etiqueta etiqueta = this.etiquetaService.buscaEtiqueta(id);
		return ResponseEntity.ok(etiqueta);
	}
	
	@GetMapping
	public ResponseEntity<Page<Etiqueta>> buscaEtiquetas(Pageable pageable) {
		Page<Etiqueta> etiquetas = this.etiquetaService.buscaEtiquetas(pageable);
		if (etiquetas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(etiquetas);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeEtiqueta(@PathVariable(value = "id") Long id) {
		this.etiquetaService.removeEtiqueta(id);
		return ResponseEntity.noContent().build();
	}
}
