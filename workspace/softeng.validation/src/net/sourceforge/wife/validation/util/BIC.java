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
 * Helper class to validate a BIC
 * 
 * @author mgriffa
 * @since 3.3
 * @version $Id: BIC.java,v 1.2 2007/06/24 17:39:35 zubri Exp $
 */
public class BIC {
	private String invalidCause = null;
	private String bic;
	
	/**
	 * Constructor with bic code
	 * @param bic the bic code to use in this bic
	 */
	public BIC(String bic) {
		this.bic = bic;
	}
	/**
	 * Default constructor
	 */
	public BIC() {
	}
	/**
	 * Get the bic code of this bic.
	 * This method does not guarantee that the bic is valid. use {@link #isValid()}
	 * 
	 * @return a string with the code
	 */
	public String getBic() {
		return bic;
	}
	/**
	 * Set the bic code for this bic
	 * @param bic the bic code
	 */
	public void setBic(String bic) {
		this.bic = bic;
	}
	/**
	 * Get a human readable (english) string that gives information about why the bic was found invalid.
	 * @return a string or <code>null</code> if there's no invalid cause set
	 */
	public String getInvalidCause() {
		return invalidCause;
	}
	
	/**
	 * Validate a bic.
	 * Currently only checks that lenght is 8 or 11 and the country code is valid
	 * 
	 * @return <code>true</code> if the bic is found to be valid and <code>false</code> in other case
	 * @throws IllegalStateException if bic is <code>null</code>
	 */
	public boolean isValid() {
		if (this.bic==null)
			throw new IllegalStateException("bic is null");
		this.invalidCause = null;
		if ( !( this.bic.length() == 8 || this.bic.length() == 11)) {
			this.invalidCause = "BIC must be 8 or 11 chars, got "+this.bic.length();
			return false;
		}
		final String country = this.bic.substring(4,6);
		if (!ISOCountries.getInstance().isValidCode(country.toUpperCase())) {
			this.invalidCause = "Invalid country code: "+country;
			return false;
		}
		return true;
	}

}
