package com.nhs.pc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sandeep
 *
 */
public class PostCodeValidator {
		
	private static final String REGEX = "^(GIR 0AA)|((([A-PR-UWYZ][0-9][0-9]?)|(([A-PR-UWYZ][A-HK-Y][0-9](?<!(BR|FY|HA|HD|HG|HR|HS|HX|JE|LD|SM|SR|WC|WN|ZE)[0-9])[0-9])|([A-PR-UWYZ][A-HK-Y](?<!AB|LL|SO)[0-9])|(WC[0-9][A-Z])|(([A-PR-UWYZ][0-9][A-HJKPSTUW])|([A-PR-UWYZ][A-HK-Y][0-9][ABEHMNPRVWXY])))) [0-9][ABD-HJLNP-UW-Z]{2})$";
	private static final Pattern pattern = Pattern.compile(REGEX);
		
	/**
	 * Validating postcode by compiling a regex pattern and matching
	 * @param postcode
	 * @return boolean
	 */
	public static boolean validatePostCode(String postcode) {
		Pattern pattern1 = Pattern.compile(REGEX);
		Matcher matcher = pattern1.matcher(postcode);
		return matcher.matches();
	}
	
	/**
	 * Validating postcode with a pre-Compiled regex pattern
	 * @param postcode
	 * @return boolean
	 */
	public static boolean validatePostCodePerformanceFix(String postcode) {		
		Matcher matcher = pattern.matcher(postcode);
		return matcher.matches();
	}

}
