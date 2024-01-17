package com.example.APITarefas.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.APITarefas.dtos.EtiquetaDto;
import com.example.APITarefas.entities.Etiqueta;
import com.example.APITarefas.exceptions.ValidacaoException;
import com.example.APITarefas.repositories.EtiquetaRepository;
import com.example.APITarefas.utils.Utils;

@Service
public class EtiquetaService {

	@Autowired
	private EtiquetaRepository etiquetaRepository;

	/**
	 * Busca etiqueta pelo id informado.
	 * 
	 * @param id
	 * @return a etiqueta correspondente ao id informado.
	 */
	public Etiqueta buscaEtiqueta(Long id) {
		validaIdEtiquetaInformado(id);
		Optional<Etiqueta> optionalEtiqueta = this.etiquetaRepository.findById(id);

		if (optionalEtiqueta.isEmpty()) {
			throw new ValidacaoException("Etiqueta não encontrada.", HttpStatus.NOT_FOUND);
		}

		Etiqueta etiqueta = optionalEtiqueta.get();
		return etiqueta;
	}

	/**
	 * Valida se o código da etiqueta não foi informado.
	 * 
	 * @param id
	 */
	private void validaIdEtiquetaInformado(Long id) {
		if (id == null) {
			throw new ValidacaoException("O código da etiqueta não foi informado.", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Insere uma etiqueta.
	 * 
	 * @param etiquetaDto
	 * @return a etiqueta inserida.
	 */
	public Etiqueta insereEtiqueta(EtiquetaDto etiquetaDto) {
		String tituloEtiqueta = etiquetaDto.titulo();
		if (Utils.stringNulaVaziaOuEmBraco(tituloEtiqueta)) {
			throw new ValidacaoException("O título da etiqueta não foi informado.", HttpStatus.BAD_REQUEST);
		}
		if (this.existeUmaEtiquetaComTituloInformado(tituloEtiqueta)) {
			throw new ValidacaoException("Já existe uma etiqueta com o título informado.", HttpStatus.CONFLICT);
		}
		return this.etiquetaRepository.save(new Etiqueta(tituloEtiqueta));
	}

	/**
	 * Verifica se já existe uma etiqueta com o título informado.
	 * 
	 * @param tituloEtiqueta
	 * @return true se já existir uma etiqueta com o título informado e false se não
	 *         existir uma etiqueta com o título informado.
	 */
	private boolean existeUmaEtiquetaComTituloInformado(String tituloEtiqueta) {
		Etiqueta etiqueta = this.etiquetaRepository.findByTitulo(tituloEtiqueta);
		if (etiqueta != null) {
			return true;
		}
		return false;
	}
}
