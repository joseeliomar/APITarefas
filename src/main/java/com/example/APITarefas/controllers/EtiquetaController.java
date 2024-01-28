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
import com.example.APITarefas.dtos.EtiquetaDtoResposta;
import com.example.APITarefas.entities.Etiqueta;
import com.example.APITarefas.services.EtiquetaService;
import com.example.APITarefas.utils.Utils;

@RestController
@RequestMapping(value = "etiquetas")
public class EtiquetaController {

	@Autowired
	private EtiquetaService etiquetaService;

	@PostMapping
	public ResponseEntity<EtiquetaDtoResposta> insereEtiqueta(@RequestBody EtiquetaDto etiquetaDto) {
		Etiqueta etiquetaInserida = this.etiquetaService.insereEtiqueta(etiquetaDto);
		EtiquetaDtoResposta etiquetaDtoResposta = new EtiquetaDtoResposta(etiquetaInserida);
		URI localizacaoRecursoCriado = Utils.obtemLocalizacaoRecursoCriado(etiquetaInserida.getId());
		return ResponseEntity.created(localizacaoRecursoCriado).body(etiquetaDtoResposta);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EtiquetaDtoResposta> alteraEtiqueta(@PathVariable Long id,
			@RequestBody EtiquetaDto etiquetaDto) {
		Etiqueta etiquetaAlterada = this.etiquetaService.alteraEtiqueta(id, etiquetaDto);
		EtiquetaDtoResposta etiquetaDtoResposta = new EtiquetaDtoResposta(etiquetaAlterada);
		return ResponseEntity.ok(etiquetaDtoResposta);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EtiquetaDtoResposta> buscaEtiqueta(@PathVariable Long id) {
		Etiqueta etiqueta = this.etiquetaService.buscaEtiqueta(id);
		EtiquetaDtoResposta etiquetaDtoResposta = new EtiquetaDtoResposta(etiqueta);
		return ResponseEntity.ok(etiquetaDtoResposta);
	}
	
	@GetMapping
	public ResponseEntity<Page<EtiquetaDtoResposta>> buscaEtiquetas(Pageable pageable) {
		Page<Etiqueta> etiquetas = this.etiquetaService.buscaEtiquetas(pageable);
		
		if (etiquetas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Page<EtiquetaDtoResposta> etiquetasDtoResposta = etiquetas.map(e -> new EtiquetaDtoResposta(e));
		
		return ResponseEntity.ok(etiquetasDtoResposta);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeEtiqueta(@PathVariable Long id) {
		this.etiquetaService.removeEtiqueta(id);
		return ResponseEntity.noContent().build();
	}
}
