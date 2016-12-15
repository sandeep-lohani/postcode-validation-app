package com.nhs.pc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Sandeep
 * link class {com.nhs.pc.PostCodeValidator}
 */
public class PostCodeValidatorTest {
	
	private static Logger LOGGER = Logger.getLogger("PostCodeValidatorTest");

	@Before
	public void setUp() throws Exception {
		
	}	
	
	/* //Part 1 - Postcode validation [Test Scenarios]
	Postcode	Expected problem
	$%± ()()	Junk
	XX XXX		Invalid
	A1 9A		Incorrect inward code length
	LS44PL		No space
	Q1A 9AA		'Q' in first position
	V1A 9AA		'V' in first position
	X1A 9BB		'X' in first position
	LI10 3QP	'I' in second position
	LJ10 3QP	'J' in second position
	LZ10 3QP	'Z' in second position
	A9Q 9AA		'Q' in third position with 'A9A' structure
	AA9C 9AA	'C' in fourth position with 'AA9A' structure
	FY10 4PL	Area with only single digit districts
	SO1 4QQ		Area with only double digit districts
	EC1A 1BB	None
	W1A 0AX		None
	M1 1AE		None
	B33 8TH		None
	CR2 6XH		None
	DN55 1PT	None
	GIR 0AA		None
	SO10 9AA	None
	FY9 9AA		None
	WC1A 9AA	None
	 */
	@Test
	public void testValidatePostCode() {
		String[] validPostCode = { "EC1A 1BB", "W1A 0AX", "M1 1AE", "B33 8TH", "CR2 6XH", "DN55 1PT", "GIR 0AA", "SO10 9AA",
								"FY9 9AA", "WC1A 9AA" };	
		String[] invalidPostCode = { "$%± ()()", "XX XXX", "A1 9A", "LS44PL", "Q1A 9AA", "V1A 9AA", "X1A 9BB", "LI10 3QP", 
								"LJ10 3QP", "LZ10 3QP", "A9Q 9AA", "AA9C 9AA", "FY10 4PL", "SO1 4QQ"};
		
		boolean matched = false;
		for (String zip : invalidPostCode) {
			matched = PostCodeValidator.validatePostCode(zip);
			LOGGER.info("Postcode " + zip + " matched " + matched);
			assertFalse(matched);
		}
		for (String zip : validPostCode) {
			matched = PostCodeValidator.validatePostCode(zip);
			LOGGER.info("Postcode " + zip + " matched " + matched);
			assertTrue(matched);
		}
		
	}
	
	//Edge cases where valid postcodes of most of Special cases like Crown dependencies, Overseas territories, 
	//the National Health Service (NHS) and other public services have a series of pseudo-postcodes 
	//which are not covered by the regular expression

	@Test
	public void testEdgeCases() {
		String[] validPostCode = { "BBND 1ZZ", "BIQQ 1ZZ", "FIQQ 1ZZ", "AI-2640", "ZZ99 3AA" };
		boolean matched = false;
		for (String zip : validPostCode) {
			matched = PostCodeValidator.validatePostCode(zip);
			LOGGER.info("Postcode " + zip + " matched " + matched);
			assertTrue(matched); //This will fail
		}
	}

}
