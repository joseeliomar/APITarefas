package com.example.APITarefas.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.APITarefas.dtos.PersonalizacaoNotificacaoDto;
import com.example.APITarefas.dtos.TarefaRecordDto;
import com.example.APITarefas.entities.ContaUsuario;
import com.example.APITarefas.entities.Etiqueta;
import com.example.APITarefas.entities.PersonalizacaoNotificacao;
import com.example.APITarefas.entities.Tarefa;
import com.example.APITarefas.enumerations.OpcaoRepeticaoNotificacao;
import com.example.APITarefas.exceptions.ValidacaoException;
import com.example.APITarefas.repositories.TarefaRepository;
import com.example.APITarefas.utils.Utils;

@Service
public class TarefaService {

	@Autowired
	private TarefaRepository tarefaRepository;

	@Autowired
	private ContaUsuarioService contaUsuarioService;
	
	@Autowired
	private EtiquetaService etiquetaService;
	
	@Autowired
	private PersonalizacaoNotificacaoService personalizacaoNotificacaoService;

	/**
	 * Insere uma tarefa.
	 * 
	 * @param tarefaRecordDto
	 * @return a tarefa inserida.
	 */
	public Tarefa insereTarefa(TarefaRecordDto tarefaRecordDto) {
		Long idContaUsuario = tarefaRecordDto.idContaUsuario();
		String tituloTarefa = tarefaRecordDto.titulo();
		String descricaoTarefa = tarefaRecordDto.descricao();
		LocalDateTime dataHoraNotificacaoTarefa = tarefaRecordDto.dataHoraNotificacao();

		int codigoOpcaoRepeticaoNotificacao = tarefaRecordDto.codigoOpcaoRepeticaoNotificacao();
		OpcaoRepeticaoNotificacao opcaoRepeticaoNotificacao = OpcaoRepeticaoNotificacao
				.obterOpcaoPorCodigo(codigoOpcaoRepeticaoNotificacao);

		PersonalizacaoNotificacaoDto personalizacaoNotificacaoDto = tarefaRecordDto.personalizacaoNotificacaoDto();

		ContaUsuario contaUsuario = this.contaUsuarioService.buscaContaUsuario(idContaUsuario);

		if (Utils.stringNulaVaziaOuEmBraco(tituloTarefa)) {
			throw new ValidacaoException("O título da tarefa não foi informado.", HttpStatus.BAD_REQUEST);
		}

		if (Utils.stringNulaVaziaOuEmBraco(descricaoTarefa)) {
			throw new ValidacaoException("A descrição da tarefa não foi informada.", HttpStatus.BAD_REQUEST);
		}

		if (dataHoraNotificacaoTarefa == null) {
			throw new ValidacaoException("A data e a hora da tarefa não foram informadas.", HttpStatus.BAD_REQUEST);
		}

		PersonalizacaoNotificacao personalizacaoNotificacaoInserida = null;
		if (opcaoRepeticaoNotificacao.equals(OpcaoRepeticaoNotificacao.PERSONALIZADO)) {
			personalizacaoNotificacaoInserida = this.personalizacaoNotificacaoService
					.inserePersonalizacaoNotificacao(personalizacaoNotificacaoDto);
		}
		
		ArrayList<Etiqueta> etiquetas = new ArrayList<>();
		List<Long> codigosEtiquetas = tarefaRecordDto.codigosEtiquetas();
		for (Long codigoEtiqueta : codigosEtiquetas) {
			Etiqueta etiqueta = this.etiquetaService.buscaEtiqueta(codigoEtiqueta);
			etiquetas.add(etiqueta);
		}

		Tarefa tarefa = new Tarefa(contaUsuario, tituloTarefa, descricaoTarefa, LocalDateTime.now(), null,
				dataHoraNotificacaoTarefa, opcaoRepeticaoNotificacao, personalizacaoNotificacaoInserida, etiquetas);
		return this.tarefaRepository.save(tarefa);
	}

}
