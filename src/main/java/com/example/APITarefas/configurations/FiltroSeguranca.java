package com.example.APITarefas.configurations;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.APITarefas.services.ContaUsuarioService;
import com.example.APITarefas.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroSeguranca extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private ContaUsuarioService contaUsuarioService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recuperaToken(request);
		if (token != null) {
			String emailUsuario = this.tokenService.extraiEmailUsuario(token);
			UserDetails userDetails = contaUsuarioService.loadUserByUsername(emailUsuario);

			if (userDetails != null) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}

		filterChain.doFilter(request, response);
	}

	/**
	 * Recupera o token enviado na requisição.
	 * 
	 * @param request
	 * @return o token recuperado.
	 */
	private String recuperaToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header == null) {
			return null;
		}
		return header.replace("Bearer ", "");
	}
}
