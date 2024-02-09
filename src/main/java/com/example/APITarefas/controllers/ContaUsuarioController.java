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

import com.example.APITarefas.dtos.ContaUsuarioDtoResposta;
import com.example.APITarefas.dtos.ContaUsuarioRecordDto;
import com.example.APITarefas.entities.ContaUsuario;
import com.example.APITarefas.services.ContaUsuarioService;
import com.example.APITarefas.utils.Utils;

@RestController
@RequestMapping(value = "/contas-usuarios")
public class ContaUsuarioController {

	@Autowired
	private ContaUsuarioService contaUsuarioService;

	@PostMapping
	public ResponseEntity<ContaUsuarioDtoResposta> insereContaUsuario(
			@RequestBody ContaUsuarioRecordDto contaUsuarioRecordDto) {
		ContaUsuario contaUsuarioInserida = this.contaUsuarioService.insereContaUsuario(contaUsuarioRecordDto);
		ContaUsuarioDtoResposta contaUsuarioDtoResposta = new ContaUsuarioDtoResposta(contaUsuarioInserida);
		URI localizacaoRecursoCriado = Utils.obtemLocalizacaoRecursoCriado(contaUsuarioInserida.getId());
		return ResponseEntity.created(localizacaoRecursoCriado).body(contaUsuarioDtoResposta);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ContaUsuarioDtoResposta> alteraContaUsuario(@PathVariable Long id,
			@RequestBody ContaUsuarioRecordDto contaUsuarioRecordDto) {
		ContaUsuario contaUsuarioAlterada = this.contaUsuarioService.alteraContaUsuario(id, contaUsuarioRecordDto);
		ContaUsuarioDtoResposta contaUsuarioDtoResposta = new ContaUsuarioDtoResposta(contaUsuarioAlterada);
		return ResponseEntity.ok(contaUsuarioDtoResposta);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContaUsuarioDtoResposta> buscaContaUsuario(@PathVariable Long id) {
		ContaUsuario contaUsuario = this.contaUsuarioService.buscaContaUsuario(id);
		ContaUsuarioDtoResposta contaUsuarioDtoResposta = new ContaUsuarioDtoResposta(contaUsuario);
		return ResponseEntity.ok(contaUsuarioDtoResposta);
	}

	@GetMapping
	public ResponseEntity<Page<ContaUsuarioDtoResposta>> buscaContasUsuarios(Pageable pageable) {
		Page<ContaUsuario> contasUsuarios = this.contaUsuarioService.buscaContasUsuarios(pageable);
		if (contasUsuarios.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Page<ContaUsuarioDtoResposta> contasUsuariosDtoResposta = contasUsuarios
				.map(c -> new ContaUsuarioDtoResposta(c));

		return ResponseEntity.ok(contasUsuariosDtoResposta);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeContaUsuario(@PathVariable Long id) {
		this.contaUsuarioService.removeContaUsuario(id);
		return ResponseEntity.noContent().build();
	}

}
