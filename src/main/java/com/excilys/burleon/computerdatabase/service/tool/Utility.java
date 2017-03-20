package com.excilys.burleon.computerdatabase.service.tool;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Junior Burleon
 */
public class Utility {

    /**
     * Convert a base string to MD5 string.
     *
     * @param base
     *            the string to encrypt.
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
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
        } catch (final NoSuchAlgorithmException e) {
        }

        return hashtext.toUpperCase();
    }

    /**
     * To convert a string to a localdatetime.
     *
     * @param dateStr
     *            The string
     * @return The localdatetime
     */
    public static LocalDateTime convertToLocalDateTime(final String dateStr) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateStr, formatter);
    }

    /**
     * To convert a localdatetime to a date for html (yyyy-MM-dd).
     *
     * @param localdatetime
     *            The date to convert to string
     * @return The date as string(yyyy-MM-dd)
     */
    public static String convertToString(final LocalDateTime localdatetime) {
        if (localdatetime == null) {
            return null;
        }
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localdatetime.format(formatter);

    }
}
