package com.nhs.pc;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Sandeep
 * link class {com.nhs.pc.BulkImportInitial}
 */
public class BulkImportInitialTestPartTwo {
	
	private static Logger LOGGER = Logger.getLogger("BulkImportInitialTestPartTwo");
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private static final String SOURCE_FILE = "C:/import_data.csv";	
	private static final String FAILED_VALIDATION_FILE = "C:/failed_validation.csv";

	//Mock
	private BulkImportInitial bulkImport = new BulkImportInitial();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBulkImportPartTwo() throws Exception{		
		bulkImport.bulkImportPartTwo(SOURCE_FILE, FAILED_VALIDATION_FILE);
		File file = new File(FAILED_VALIDATION_FILE);
		assertTrue(file.exists());
		assertTrue(file.length() > 0);			
	}
	
	@Test
	public void testBulkImportPartTwoExceptionCase() throws Exception{
		String wrongFilePath = "test";
		exception.expect(IOException.class);
		bulkImport.bulkImportPartTwo(wrongFilePath, FAILED_VALIDATION_FILE);
	}
	
}
