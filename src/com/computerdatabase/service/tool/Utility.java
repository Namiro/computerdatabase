package com.computerdatabase.service.tool;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Junior Burleon
 */
public class Utility {

	/**
	 * Convert a base string to MD5 string
	 *
	 * @param base
	 * @return string in MD5
	 */
	public static String convertMD5(final String base) {
		MessageDigest m;
		String hashtext = "";
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(base.getBytes());
			final byte[] digest = m.digest();
			final BigInteger bigInt = new BigInteger(1, digest);
			hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32)
				hashtext = "0" + hashtext;
		} catch (final NoSuchAlgorithmException e) {
		}

		return hashtext.toUpperCase();
	}
}
