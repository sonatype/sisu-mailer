package org.sonatype.micromailer.imp;

/**
 * String helpers.
 *
 * @since 1.7
 */
public class Strings
{
    public static boolean isNotEmpty(final String str) {
        return ((str != null) && (str.length() > 0));
    }

    public static boolean isEmpty(final String str) {
        return ((str == null) || (str.trim().length() == 0));
    }
}
