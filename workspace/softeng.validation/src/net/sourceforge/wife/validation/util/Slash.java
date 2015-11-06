/* 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
*/
package net.sourceforge.wife.validation.util;

/**
 * Helper class for validations regarding the presence, or lack of it of a slash or double slash inside a tag.
 * 
 * @author mgriffa
 * @since 3.3
 * @version $Id: Slash.java,v 1.2 2007/06/24 17:39:35 zubri Exp $
 */
public class Slash {
	/**
	 * The caracter used as slash in this class
	 */
	public static final char SLASH_CHAR = '/';
	/**
	 * String used to find two consecutive slashes
	 */
	public static final String TWO_SLASHES = "//";
	private Slash() {
		// prevent proliferation
	}
	
	/**
	 * Evaluates tag to see if the first character is the slash.
	 * This method does not consider multiline tags
	 * 
	 * @param tag the tag to evaluate
	 * @return <code>true</code> if the first character of tag is <i>/</i> and <code>false</code> in other case
	 * @throws NullPointerException if tag is <code>null</code>
	 */
	public static boolean atStart(final String tag) { 
		return tag.indexOf(SLASH_CHAR)==0;
	}

	/**
	 * Evaluates value searching for two consecutive <i>//</i>.
	 * return <code>true</code> if there are two slashes (not a start) of the value and false in other case.
	 * This method does not consider multiline tags
	 * 
	 * <p><em>NOTE: if there are two consecutive slashes right at the beginning of the string, and nowhere else, this method will return false
	 * 
	 * @param value
	 * @return <code>true</code> if two consecutive slashes are found inside (not at the start) of the string and false in other case
	 * @throws NullPointerException if value is <code>null</code>
	 */
	public static boolean containsConsecutive(String value) {
		int pos = value.indexOf(TWO_SLASHES, 1);
		return pos>0;
	}
}
