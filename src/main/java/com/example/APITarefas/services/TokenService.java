package com.example.APITarefas.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.APITarefas.entities.ContaUsuario;
import com.example.APITarefas.exceptions.AlgoritmoException;
import com.example.APITarefas.exceptions.TokenException;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;
	
	private static final String EMISSOR_TOKEN = "APITarefas";
	
	/**
	 * Gera um token.
	 * 
	 * @param contaUsuario
	 * @return o token gerado.
	 */
	public String geraToken(ContaUsuario contaUsuario) {
		Algorithm algoritmoGerado = geraAlgoritmoUsandoHmacSHA256();
		
		try {
			String tokenGerado = JWT.create()
					.withIssuer(EMISSOR_TOKEN)
					.withSubject(contaUsuario.getEmail())
					.withExpiresAt(geraInstanteExpiracao())
					.sign(algoritmoGerado);
			
			return tokenGerado;
		} catch (Exception exception) {
			throw new TokenException(
					"Erro ao tentar gerar um token. Mensagem de erro: " + exception.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Extrai do token o e-mail do usuário.
	 * 
	 * @param token
	 * @return o e-mail do usuário. Caso ocorrá algum problema na extração do e-mail será retornado null.
	 */
	public String extraiEmailUsuario(String token) {
		Algorithm algoritmoGerado = geraAlgoritmoUsandoHmacSHA256();
		
		try {
			return JWT.require(algoritmoGerado)
					.withIssuer(EMISSOR_TOKEN)
					.build()
					.verify(token)
					.getSubject();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gera um algoritmo usando HmacSHA256.
	 * 
	 * @return o algoritmo gerado.
	 */
	private Algorithm geraAlgoritmoUsandoHmacSHA256() {
		try {
			Algorithm algorithm = Algorithm.HMAC256(this.secret);
			return algorithm;
		} catch (Exception exception) {
			throw new AlgoritmoException("Erro ao tentar gerar um algoritmo usando HmacSHA256. Mensagem de erro: "
					+ exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Gera o instante (momento) da expiração.
	 * 
	 * @return o instante (momento) da expiração.
	 */
	private Instant geraInstanteExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
