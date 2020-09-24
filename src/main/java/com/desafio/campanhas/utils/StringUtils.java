package com.desafio.campanhas.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class StringUtils {

	private StringUtils() {
	}
	
	public static String decodeName(String nome) {
		try {
			return URLDecoder.decode(nome, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
