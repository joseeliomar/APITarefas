package com.example.APITarefas.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.APITarefas.enumerations.PapelUsuario;

@Configuration
@EnableWebSecurity
public class ConfiguracaoesSeguranca {

	@Autowired
	private FiltroSeguranca filtroSeguranca;

	@Bean
	public SecurityFilterChain cadeiaFiltrosSeguranca(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "/autenticacao/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/contas-usuarios").permitAll()
						.requestMatchers(HttpMethod.GET, "/contas-usuarios/todas")
						.hasAnyAuthority(PapelUsuario.ROLE_ADMIN.getNome())
						.anyRequest().authenticated())
				.addFilterBefore(this.filtroSeguranca, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public AuthenticationManager gerenciadorAutenticacao(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder codificadorSenha() {
		return new BCryptPasswordEncoder();
	}
}
