package com.example.APITarefas.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Utils {
	
	private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	/**
	 * Verifica se a string informada está nula ou vazia ou em branco.
	 * 
	 * @param string
	 * @return true se a string informada estiver nula ou vazia ou em branco e false
	 *         se string informada não estiver nula, nem vazia, nem em branco.
	 */
	public static boolean stringNulaVaziaOuEmBraco(String string) {
		return string == null || string.isBlank();
	}
	
	/**
	 * Criptografa a string informada.
	 * @param string
	 * @return uma string criptografada.
	 */
	public static String criptografaString(String string) {
		return bCryptPasswordEncoder.encode(string);
	}
}
