package br.com.cotiinformatica.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1CryptoHelper {

	public static String hashString(String input) {

		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");

			byte[] inputBytes = input.getBytes();
			sha1.update(inputBytes);

			byte[] hashBytes = sha1.digest();

			StringBuilder hexStringBuilder = new StringBuilder();
			for (byte b : hashBytes) {
				hexStringBuilder.append(String.format("%02x", b));
			}

			return hexStringBuilder.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
