package com.example.APITarefas.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.APITarefas.dtos.ContaUsuarioRecordDto;
import com.example.APITarefas.entities.ContaUsuario;
import com.example.APITarefas.entities.Papel;
import com.example.APITarefas.enumerations.PapelUsuario;
import com.example.APITarefas.exceptions.ValidacaoException;
import com.example.APITarefas.repositories.ContaUsuarioRepository;
import com.example.APITarefas.utils.Utils;

@Service
public class ContaUsuarioService implements UserDetailsService {

	@Autowired
	private ContaUsuarioRepository contaUsuarioRepository;
	
	@Autowired
	private PapelService papelService;

	/**
	 * Insere uma conta de usuário.
	 * 
	 * @param contaUsuarioRecordDto
	 * @return a conta inserida.
	 */
	public ContaUsuario insereContaUsuario(ContaUsuarioRecordDto contaUsuarioRecordDto) {
		String nomeUsuario = contaUsuarioRecordDto.nomeUsuario();
		String emailUsuario = contaUsuarioRecordDto.email();
		String senhaConta = contaUsuarioRecordDto.senha();
		List<PapelUsuario> papeisUsuario = contaUsuarioRecordDto.papeisUsuario();

		executaValidacoesInsercaoAlteracao(nomeUsuario, emailUsuario, senhaConta);

		if (this.existeUmaContaUsuarioComEmailInformado(emailUsuario)) {
			throw new ValidacaoException("Já existe uma conta de usuário com o e-mail informado.", HttpStatus.CONFLICT);
		}

		String senhaContaCriptografada = Utils.criptografaString(senhaConta);
		
		List<Papel> papeisUsuarioInseridos = inserePapeisUsuario(papeisUsuario);
		
		ContaUsuario contaUsuario = new ContaUsuario(nomeUsuario, emailUsuario, LocalDateTime.now(), null,
				senhaContaCriptografada, papeisUsuarioInseridos);
		
		return this.contaUsuarioRepository.save(contaUsuario);
	}
	
	/**
	 * Insere os papeis do usuário.
	 * 
	 * @param papeisUsuario
	 * @return os papeis do usuário que foram inseridos.
	 */
	private List<Papel> inserePapeisUsuario(List<PapelUsuario> papeisUsuario) {
		List<Papel> papeis = new ArrayList<>();
		
		if (papeisUsuario != null) {
			for (PapelUsuario papelUsuario : papeisUsuario) {
				Papel papelInserido = this.papelService.inserePapel(papelUsuario);
				papeis.add(papelInserido);
			}
		}
		
		return papeis;
	}

	/**
	 * Executa validações que devem ser executadas na inserção e na alteração de uma
	 * conta de usuário.
	 * 
	 * @param nomeUsuario
	 * @param emailUsuario
	 * @param senhaConta
	 */
	private void executaValidacoesInsercaoAlteracao(String nomeUsuario, String emailUsuario, String senhaConta) {
		if (Utils.stringNulaVaziaOuEmBraco(nomeUsuario)) {
			throw new ValidacaoException("O nome do usuário não foi informado.", HttpStatus.BAD_REQUEST);
		}
		validaEmailInformado(emailUsuario);
		if (Utils.stringNulaVaziaOuEmBraco(senhaConta)) {
			throw new ValidacaoException("A senha não foi informada.", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 *  Valida se o e-mail da conta do usuário não foi informado.
	 *  
	 * @param emailUsuario
	 */
	private void validaEmailInformado(String emailUsuario) {
		if (Utils.stringNulaVaziaOuEmBraco(emailUsuario)) {
			throw new ValidacaoException("O e-mail não foi informado.", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Verifica se já existe uma conta de usuário com o email informado.
	 * 
	 * @param emailUsuario
	 * @return true se já existir uma conta de usuário com o email informado e false
	 *         se não existir uma conta de usuário com o email informado.
	 */
	private boolean existeUmaContaUsuarioComEmailInformado(String emailUsuario) {
		ContaUsuario contaUsuario = this.contaUsuarioRepository.findByEmail(emailUsuario);
		if (contaUsuario != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Verifica se já existe uma outra conta de usuário com o email informado.
	 * 
	 * @param emailUsuario
	 * @param id
	 * @return true se já existir uma outra conta de usuário com o email informado e
	 *         false se não existir uma outra conta de usuário com o email
	 *         informado.
	 */
	private boolean existeUmaOutraContaUsuarioComEmailInformado(String emailUsuario, Long id) {
		ContaUsuario contaUsuario = this.contaUsuarioRepository.findByEmailAndIdNot(emailUsuario, id);
		if (contaUsuario != null) {
			return true;
		}
		return false;
	}

	/**
	 * Altera uma conta de usuário.
	 * 
	 * @param id
	 * @param contaUsuarioRecordDto
	 * @return a conta alterada.
	 */
	public ContaUsuario alteraContaUsuario(Long id, ContaUsuarioRecordDto contaUsuarioRecordDto) {
		ContaUsuario contaUsuario = buscaContaUsuario(id);
		
		String nomeUsuario = contaUsuarioRecordDto.nomeUsuario();
		String emailUsuario = contaUsuarioRecordDto.email();
		String senhaConta = contaUsuarioRecordDto.senha();
		List<PapelUsuario> papeisUsuario = contaUsuarioRecordDto.papeisUsuario();
		
		executaValidacoesInsercaoAlteracao(nomeUsuario, emailUsuario, senhaConta);
		
		if (this.existeUmaOutraContaUsuarioComEmailInformado(emailUsuario, contaUsuario.getId())) {
			throw new ValidacaoException("Já existe uma outra conta de usuário com o e-mail informado.", HttpStatus.CONFLICT);
		}

		contaUsuario.setNomeUsuario(contaUsuarioRecordDto.nomeUsuario());
		contaUsuario.setEmail(contaUsuarioRecordDto.email());
		
		String senhaContaCriptografada = Utils.criptografaString(senhaConta);
		contaUsuario.setSenha(senhaContaCriptografada);
		
		contaUsuario.setDataHoraAlteracao(LocalDateTime.now());
		
		List<Papel> papeisContaUsuario = contaUsuario.getPapeis();
		papeisContaUsuario.clear();
		List<Papel> papeisUsuarioInseridos = inserePapeisUsuario(papeisUsuario);
		papeisContaUsuario.addAll(papeisUsuarioInseridos);

		return this.contaUsuarioRepository.save(contaUsuario);
	}

	/**
	 * Busca conta de usuário pelo id informado.
	 * 
	 * @param id
	 * @return a conta de usuário correspondente ao id informado.
	 */
	public ContaUsuario buscaContaUsuario(Long id) {
		validaIdContaUsuarioInformado(id);
		Optional<ContaUsuario> optionalContaUsuario = this.contaUsuarioRepository.findById(id);
		
		if (optionalContaUsuario.isEmpty()) {
			throw new ValidacaoException("Conta de usuário não encontrada.", HttpStatus.NOT_FOUND);
		}
		
		ContaUsuario contaUsuario = optionalContaUsuario.get();
		return contaUsuario;
	}

	/**
	 * Busca contas de usuários.
	 * 
	 * @return contas de usuários.
	 */
	public Page<ContaUsuario> buscaContasUsuarios(Pageable pageable) {
		Page<ContaUsuario> contasUsuarios = this.contaUsuarioRepository.findAll(pageable);
		return contasUsuarios;
	}

	/**
	 * Remove uma conta de usuário pelo id informado.
	 * 
	 * @param id
	 */
	public void removeContaUsuario(Long id) {
		ContaUsuario contaUsuario = this.buscaContaUsuario(id);
		this.contaUsuarioRepository.delete(contaUsuario);
	}

	/**
	 * Valida se o código da conta do usuário não foi informado.
	 * 
	 * @param id
	 */
	public void validaIdContaUsuarioInformado(Long id) {
		if (id == null) {
			throw new ValidacaoException("O código da conta do usuário não foi informado.", HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Busca conta de usuário pelo e-mail informado.
	 * 
	 * @param email
	 * @return a conta de usuário correspondente ao e-mail informado
	 */
	private ContaUsuario findByEmail(String email) {
		validaEmailInformado(email);
		ContaUsuario contaUsuario = this.contaUsuarioRepository.findByEmail(email);
		
		if (contaUsuario == null) {
			throw new ValidacaoException("Conta de usuário não encontrada.", HttpStatus.NOT_FOUND);
		}
		
		return contaUsuario;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findByEmail(username);
	}

}
