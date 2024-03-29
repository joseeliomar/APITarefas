package com.example.APITarefas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.APITarefas.dtos.PersonalizacaoNotificacaoDto;
import com.example.APITarefas.entities.DiaSemanaPersonalizacaoNotificacao;
import com.example.APITarefas.entities.PersonalizacaoNotificacao;
import com.example.APITarefas.enumerations.DiaSemana;
import com.example.APITarefas.enumerations.MedidaTempo;
import com.example.APITarefas.exceptions.ValidacaoException;
import com.example.APITarefas.repositories.PersonalizacaoNotificacaoRepository;

@Service
public class PersonalizacaoNotificacaoService {

	@Autowired
	private PersonalizacaoNotificacaoRepository personalizacaoNotificacaoRepository;

	@Autowired
	private DiaSemanaPersonalizacaoNotificacaoService diaSemanaPersonalizacaoNotificacaoService;

	/**
	 * Insere uma personalização de notificação.
	 * 
	 * @param personalizacaoNotificacaoDto
	 * @return a personalização da notificação inserida.
	 */
	public PersonalizacaoNotificacao inserePersonalizacaoNotificacao(
			PersonalizacaoNotificacaoDto personalizacaoNotificacaoDto) {
		if (personalizacaoNotificacaoDto == null) {
			throw new ValidacaoException(
					"Os dados obrigatórios para a personalização da repetição da notificação da tarefa precisam ser informados.",
					HttpStatus.BAD_REQUEST);
		}

		String contexto = "Em relação a personalização da repetição da notificação da tarefa";

		MedidaTempo medidaTempo = personalizacaoNotificacaoDto.medidaTempo();
		if (medidaTempo == null) {
			throw new ValidacaoException(contexto + ", a medida de tempo não foi informada.",
					HttpStatus.BAD_REQUEST);
		}

		int quantidade = personalizacaoNotificacaoDto.quantidade();
		if (quantidade < 1) {
			throw new ValidacaoException(contexto + ", a quantidade de " + medidaTempo.getPalavraPlural().toLowerCase()
					+ " informada é inválida.", HttpStatus.BAD_REQUEST);
		}

		List<DiaSemana> diasSemana = new ArrayList<>();
		if (medidaTempo.equals(MedidaTempo.SEMANA)) {
			diasSemana = personalizacaoNotificacaoDto.diasSemana();
			if (diasSemana == null 
					|| diasSemana.stream().filter(c -> c != null).count() == 0) { // pode ser que venham itens nulos na lista
				throw new ValidacaoException(contexto + ", os dias da semana não foram informados.",
						HttpStatus.BAD_REQUEST);
			}

			diasSemana = diasSemana.stream().filter(c -> c != null).collect(Collectors.toList());
		}

		PersonalizacaoNotificacao personalizacaoNotificacaoInserida = this.personalizacaoNotificacaoRepository
				.save(new PersonalizacaoNotificacao(quantidade, medidaTempo));
		
		insereDiasSemanaPersonalizacaoNotificacao(diasSemana, personalizacaoNotificacaoInserida);

		return personalizacaoNotificacaoInserida;
	}
	
	/**
	 * Insere os dias da semana associados com a personalização da notificação.
	 * 
	 * @param diasSemana
	 * @param personalizacaoNotificacao
	 */
	private void insereDiasSemanaPersonalizacaoNotificacao(List<DiaSemana> diasSemana,
			PersonalizacaoNotificacao personalizacaoNotificacao) {
		for (DiaSemana diaSemana : diasSemana) {
			DiaSemanaPersonalizacaoNotificacao diaSemanaPersonalizacaoNotificacaoInserido = this.diaSemanaPersonalizacaoNotificacaoService
					.insereDiaSemanaPersonalizacaoNotificacao(
							new DiaSemanaPersonalizacaoNotificacao(personalizacaoNotificacao, diaSemana));
			personalizacaoNotificacao.getDiasSemanaPersonalizacaoNotificacao()
					.add(diaSemanaPersonalizacaoNotificacaoInserido);
		}
	}
	
	/**
	 * Remove uma personalização de notificação pelo id informado.
	 * 
	 * @param id
	 */
	public void removePersonalizacaoNotificacao(Long id) {
		PersonalizacaoNotificacao personalizacaoNotificacao = buscaPersonalizacaoNotificacao(id);
		this.diaSemanaPersonalizacaoNotificacaoService
				.removeDiasSemanaPersonalizacaoNotificacao(personalizacaoNotificacao);
	}

	/**
	 * Busca uma personalização de notificação pelo id informado.
	 * 
	 * @param id
	 * @return uma personalização de notificação.
	 */
	private PersonalizacaoNotificacao buscaPersonalizacaoNotificacao(Long id) {
		validaIdPersonalizacaoNotificacaoInformado(id);
		Optional<PersonalizacaoNotificacao> optionalPersonalizacaoNotificacao = this.personalizacaoNotificacaoRepository.findById(id);
		
		if (optionalPersonalizacaoNotificacao.isEmpty()) {
			throw new ValidacaoException("Personalização de notificação não encontrada.", HttpStatus.NOT_FOUND);
		}
		
		PersonalizacaoNotificacao personalizacaoNotificacao = optionalPersonalizacaoNotificacao.get();
		return personalizacaoNotificacao;
	}

	/**
	 * Valida se o código da personalização de notificação não foi informado.
	 * @param id
	 */
	private void validaIdPersonalizacaoNotificacaoInformado(Long id) {
		if (id == null) {
			throw new ValidacaoException("O código da personalização de notificação não foi informado.",
					HttpStatus.BAD_REQUEST);
		}
	}
}
