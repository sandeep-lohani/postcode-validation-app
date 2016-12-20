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
 * link class {com.nhs.pc.BulkImportEnhanced}
 */
public class BulkImportEnhancedTest {
	
	private static Logger LOGGER = Logger.getLogger("BulkImportEnhancedTest");
	
	private static final String SOURCE_FILE = "C:/import_data.csv";
	private static final String FAILED_VALIDATION_FILE = "C:/failed_validation.csv";
	private static final String SUCCESS_VALIDATION_FILE = "C:/succeeded_validation.csv";

	//Mock
	private BulkImportEnhanced bulkImport = new BulkImportEnhanced();
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void bulkImportPartThreePerformanceFix() throws Exception {	
		long start = System.currentTimeMillis();
		long end = 0l;
		LOGGER.info("Start app @ " + start);
		
		bulkImport.bulkImportPartThreePerformanceFix(SOURCE_FILE, FAILED_VALIDATION_FILE, SUCCESS_VALIDATION_FILE);
		File fileFailed = new File(FAILED_VALIDATION_FILE);
		assertTrue(fileFailed.exists());
		assertTrue(fileFailed.length() > 0);
		File fileSuccess = new File(SUCCESS_VALIDATION_FILE);
		assertTrue(fileSuccess.exists());
		assertTrue(fileSuccess.length() > 0);
		
		end = System.currentTimeMillis();
		LOGGER.info("Stop app @ " + end);
		LOGGER.info("Total time taken is " + (end - start) + " milli seconds");				
	}
	
	@Test
	public void testBulkImportPartThreeExceptionCase() throws Exception{
		String wrongFilePath = "test";
		exception.expect(IOException.class);
		bulkImport.bulkImportPartThreePerformanceFix(wrongFilePath, FAILED_VALIDATION_FILE, SUCCESS_VALIDATION_FILE);
	}

}
