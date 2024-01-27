package com.example.APITarefas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.APITarefas.dtos.DadosAutenticacaoDto;
import com.example.APITarefas.dtos.TokenRespostaDto;
import com.example.APITarefas.entities.ContaUsuario;
import com.example.APITarefas.services.TokenService;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody DadosAutenticacaoDto dadosAutenticacao) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.email(), dadosAutenticacao.senha());
		Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		String tokenGerado = this.tokenService.geraToken((ContaUsuario) authentication.getPrincipal());
		return ResponseEntity.ok(new TokenRespostaDto(tokenGerado));
	}
}
