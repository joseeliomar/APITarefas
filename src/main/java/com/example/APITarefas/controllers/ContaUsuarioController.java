package com.example.APITarefas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.APITarefas.dtos.ContaUsuarioRecordDto;
import com.example.APITarefas.entities.ContaUsuario;
import com.example.APITarefas.services.ContaUsuarioService;

@RestController
@RequestMapping(value = "/contas-usuarios")
public class ContaUsuarioController {

	@Autowired
	private ContaUsuarioService contaUsuarioService;
	
	@PostMapping
	public ResponseEntity<ContaUsuario> insereContaUsuario(@RequestBody ContaUsuarioRecordDto contaUsuarioRecordDto) {
		ContaUsuario contaUsuarioInserida = this.contaUsuarioService.insereContaUsuario(contaUsuarioRecordDto);
		return ResponseEntity.created(null).body(contaUsuarioInserida); // falta criar a URI
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ContaUsuario> alteraContaUsuario(@PathVariable(value = "id") Long id, @RequestBody ContaUsuarioRecordDto contaUsuarioRecordDto) {
		ContaUsuario contaUsuarioAlterada = this.contaUsuarioService.alteraContaUsuario(id, contaUsuarioRecordDto);
		return ResponseEntity.ok(contaUsuarioAlterada);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ContaUsuario> buscaContaUsuario(@PathVariable(value = "id") Long id) {
		ContaUsuario contaUsuario = this.contaUsuarioService.buscaContaUsuario(id);
		return ResponseEntity.ok(contaUsuario);
	}
	
	@GetMapping
	public ResponseEntity<Page<ContaUsuario>> buscaContasUsuarios(Pageable pageable) {
		Page<ContaUsuario> contasUsuarios = this.contaUsuarioService.buscaContasUsuarios(pageable);
		if (contasUsuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(contasUsuarios);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeContaUsuario(@PathVariable(value = "id") Long id) {
		this.contaUsuarioService.removeContaUsuario(id);
		return ResponseEntity.noContent().build();
	}
}
