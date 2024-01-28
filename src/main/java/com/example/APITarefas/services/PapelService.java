package com.example.APITarefas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.APITarefas.entities.Papel;
import com.example.APITarefas.enumerations.PapelUsuario;
import com.example.APITarefas.exceptions.ValidacaoException;
import com.example.APITarefas.repositories.PapelRepository;

@Service
public class PapelService {
	
	@Autowired
	private PapelRepository papelRepository;

	/**
	 * Insere um papel.
	 * 
	 * @param papelUsuario
	 * @return o papel inserido.
	 */
	public Papel inserePapel(PapelUsuario papelUsuario) {
		if (papelUsuario == null) {
			throw new ValidacaoException("O papel do usuário não foi encontrado.", HttpStatus.NOT_FOUND);
		}
		
		return this.papelRepository.save(new Papel(papelUsuario));
	}

}
