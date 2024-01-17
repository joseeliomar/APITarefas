package com.example.APITarefas.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
		Etiqueta etiquetaInserida = etiquetaService.insereEtiqueta(etiquetaDto);
		URI localizacaoRecursoCriado = Utils.obtemLocalizacaoRecursoCriado(etiquetaInserida.getId());
		return ResponseEntity.created(localizacaoRecursoCriado).body(etiquetaInserida);
	}
	
	
}
