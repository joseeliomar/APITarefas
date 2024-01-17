package com.example.APITarefas.services;

import java.util.ArrayList;
import java.util.List;
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
	public PersonalizacaoNotificacao inserePersonalizacaoNotificacao(PersonalizacaoNotificacaoDto personalizacaoNotificacaoDto) {
		if (personalizacaoNotificacaoDto == null) {
			throw new ValidacaoException(
					"Os dados obrigatórios para a personalização da repetição da notificação da tarefa precisam ser informados.",
					HttpStatus.BAD_REQUEST);
		}

		String contexto = "Em relação a personalização da repetição da notificação da tarefa";

		if (personalizacaoNotificacaoDto.codigoMedidaTempo() == null) {
			throw new ValidacaoException("A medida de tempo não foi informada.", HttpStatus.BAD_REQUEST);
		}

		Integer codigoMedidaTempo = personalizacaoNotificacaoDto.codigoMedidaTempo();
		MedidaTempo medidaTempo = MedidaTempo.obterOpcaoPorCodigo(codigoMedidaTempo);
		if (medidaTempo == null) {
			throw new ValidacaoException(
					contexto + ", a medida de tempo informada é inválida. Código de medida de tempo informado: "
							+ codigoMedidaTempo + ".",
					HttpStatus.BAD_REQUEST);
		}

		int quantidade = personalizacaoNotificacaoDto.quantidade();
		if (quantidade < 1) {
			throw new ValidacaoException(contexto + ", a quantidade de "
					+ medidaTempo.getPalavraPlural().toLowerCase() + " informada é inválida.",
					HttpStatus.BAD_REQUEST);
		}

		ArrayList<DiaSemana> diasSemana = new ArrayList<>();
		if (medidaTempo.equals(MedidaTempo.DIA)) {
			List<Integer> codigosDiasSemana = personalizacaoNotificacaoDto.codigosDiasSemana();
			if (codigosDiasSemana == null 
					|| codigosDiasSemana.stream().filter(c -> c != null).count() == 0) { // pode ser que venham itens nulos na lista
				throw new ValidacaoException(contexto + ", os dias da semana não foram informados.",
						HttpStatus.BAD_REQUEST);
			}

			codigosDiasSemana = codigosDiasSemana.stream().filter(c -> c != null).collect(Collectors.toList());
			for (Integer codigoDiaSemana : codigosDiasSemana) {
				DiaSemana diaSemana = DiaSemana.obterOpcaoPorCodigo(codigoDiaSemana);
				if (diaSemana == null) {
					throw new ValidacaoException(contexto
							+ ", foi encontrado entre os dias informados, um dia da semana inválido. Código de dia da semana informado: "
							+ codigoDiaSemana + ".", HttpStatus.BAD_REQUEST);
				}
				diasSemana.add(diaSemana);
			}
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
	private void insereDiasSemanaPersonalizacaoNotificacao(ArrayList<DiaSemana> diasSemana,
			PersonalizacaoNotificacao personalizacaoNotificacao) {
		for (DiaSemana diaSemana : diasSemana) {
			diaSemanaPersonalizacaoNotificacaoService.insereDiaSemanaPersonalizacaoNotificacao(
					new DiaSemanaPersonalizacaoNotificacao(personalizacaoNotificacao, diaSemana));
		}
	}
}
