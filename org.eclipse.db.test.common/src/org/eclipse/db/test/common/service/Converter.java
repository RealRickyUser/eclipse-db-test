package org.eclipse.db.test.common.service;

import java.util.Base64;

public class Converter {
	
	public static String toBase64(String str) {
		return Base64.getEncoder().encodeToString(str.getBytes());
	}

	public static String fromBase64(String str) {
		return new String(Base64.getDecoder().decode(str));
	}
}
