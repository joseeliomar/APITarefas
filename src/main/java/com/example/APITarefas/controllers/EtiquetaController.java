package com.example.APITarefas.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.APITarefas.dtos.EtiquetaDto;
import com.example.APITarefas.entities.Etiqueta;
import com.example.APITarefas.services.EtiquetaService;

@RestController
@RequestMapping(value = "etiquetas")
public class EtiquetaController {

	private EtiquetaService etiquetaService;
	
	@PostMapping
	public ResponseEntity<Etiqueta> criaEtiqueta(@RequestBody EtiquetaDto etiquetaDto) {
		Etiqueta etiquetaCriadaSalva = etiquetaService.criaEtiqueta(etiquetaDto);
		return ResponseEntity.created(null).body(etiquetaCriadaSalva);
	}
}
