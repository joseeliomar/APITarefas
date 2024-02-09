package com.example.APITarefas.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		Tarefa tarefaInserida = salvaTarefa(tarefaRecordDto, null);
		return tarefaInserida;
	}

	/**
	 * Salva uma tarefa. Para esse método pode ser passada a tarefa a ser alterada,
	 * porém caso queira inserir uma tarefa ao invés de alterar alguma tarefa, não
	 * informe alguma tarefa e assim será criada uma nova tarefa com os dados
	 * informados.
	 * 
	 * @param tarefaRecordDto
	 * @param tarefaSerAlterada a tarefa a ser alterada.
	 * @return a tarefa salva.
	 */
	private Tarefa salvaTarefa(TarefaRecordDto tarefaRecordDto, Tarefa tarefaSerAlterada) {
		Long idContaUsuario = tarefaRecordDto.idContaUsuario();
		String tituloTarefa = tarefaRecordDto.titulo();
		String descricaoTarefa = tarefaRecordDto.descricao();
		LocalDateTime dataHoraNotificacaoTarefa = tarefaRecordDto.dataHoraNotificacao();

		OpcaoRepeticaoNotificacao opcaoRepeticaoNotificacao = tarefaRecordDto.opcaoRepeticaoNotificacao();

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

		if (tarefaSerAlterada != null) {
			tarefaSerAlterada.setContaUsuario(contaUsuario);
			tarefaSerAlterada.setTitulo(tituloTarefa);
			tarefaSerAlterada.setDescricao(descricaoTarefa);
			tarefaSerAlterada.setDataHoraAlteracao(LocalDateTime.now());
			tarefaSerAlterada.setDataHoraNotificacao(dataHoraNotificacaoTarefa);
			tarefaSerAlterada.setOpcaoRepeticaoNotificacao(opcaoRepeticaoNotificacao);
			tarefaSerAlterada.setPersonalizacaoNotificacao(personalizacaoNotificacaoInserida);
			List<Etiqueta> etiquetasTarefa = tarefaSerAlterada.getEtiquetas();
			etiquetasTarefa.clear();
			etiquetasTarefa.addAll(etiquetas);
			return this.tarefaRepository.save(tarefaSerAlterada);
		} else {
			Tarefa tarefa = new Tarefa(contaUsuario, tituloTarefa, descricaoTarefa, LocalDateTime.now(), null,
					dataHoraNotificacaoTarefa, opcaoRepeticaoNotificacao, personalizacaoNotificacaoInserida, etiquetas);
			return this.tarefaRepository.save(tarefa);
		}
	}

	/**
	 * Busca uma tarefa pelo e-mail da conta de usuário e id da tarefa que foram
	 * informados.
	 * 
	 * @param idTarefa
	 * @return a tarefa correspondente ao e-mail da conta de usuário e ao id da
	 *         tarefa que foram informados.
	 */
	public Tarefa buscaTarefa(Long idTarefa) {
		validaIdTarefaInformado(idTarefa);
		Optional<Tarefa> optionalTarefa = this.tarefaRepository.findById(idTarefa);

		if (optionalTarefa.isEmpty()) {
			throw new ValidacaoException("Tarefa não encontrada.", HttpStatus.NOT_FOUND);
		}

		Tarefa tarefa = optionalTarefa.get();
		return tarefa;
	}

	/**
	 * Valida se o código da tarefa não foi informado.
	 * 
	 * @param idTarefa
	 */
	private void validaIdTarefaInformado(Long idTarefa) {
		if (idTarefa == null) {
			throw new ValidacaoException("O código da tarefa não foi informado.", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Busca tarefas.
	 * 
	 * @param pageable
	 * @return tarefas.
	 */
	public Page<Tarefa> buscaTarefas(Pageable pageable) {
		Page<Tarefa> tarefas = this.tarefaRepository.findAll(pageable);
		return tarefas;
	}

	/**
	 * Remove uma tarefa pelo e-mail da conta de usuário e id da tarefa que foram
	 * informados.
	 * 
	 * @param idTarefa
	 */
	public void removeTarefa(Long idTarefa) {
		Tarefa tarefa = this.buscaTarefa(idTarefa);
		this.tarefaRepository.delete(tarefa);
	}

	/**
	 * Altera uma tarefa.
	 * 
	 * @param idTarefa
	 * @param tarefaRecordDto
	 * @return a tarefa alterada.
	 */
	public Tarefa alteraTarefa(Long idTarefa, TarefaRecordDto tarefaRecordDto) {
		Tarefa tarefa = buscaTarefa(idTarefa);
		Long idPersonalizacaoNotificacaoTarefa = tarefa.getPersonalizacaoNotificacao().getId();
		this.personalizacaoNotificacaoService.removePersonalizacaoNotificacao(idPersonalizacaoNotificacaoTarefa);
		Tarefa tarefaAlterada = salvaTarefa(tarefaRecordDto, tarefa);
		return tarefaAlterada;
	}

}
