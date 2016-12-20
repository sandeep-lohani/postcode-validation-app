package com.nhs.pc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.nhs.pc.model.PostCodeData;
import com.nhs.pc.task.ProcessListTask;

/**
 * Class to import postcodes from a file, validate and write to other files
 * @author Sandeep
 *
 */
public class BulkImportEnhanced {
	private static Logger LOGGER = Logger.getLogger("BulkImportEnhanced");

	private static final String DELIMETER = ",";
	private static final String ROWID = "row_id";
	private static final String NEWLINE = "\n";
	
	/**
	 * Similar to method {bulkImportPartThree} with performance fixes
	 * @param sourceFile
	 * @param failedValidationFile
	 * @param successValidationFile
	 */
	public void bulkImportPartThreePerformanceFix (String sourceFile, String failedValidationFile, String successValidationFile)
			throws IOException{
		List<PostCodeData> failedPostCodesLst = new ArrayList<>();
		List<PostCodeData> succeededPostCodesLst = new ArrayList<>();
		String line = "";
        FileReader fileReader = null;
        BufferedReader br = null;
        FileWriter fileWriterFail = null;
        BufferedWriter bwf = null;
        FileWriter fileWriterSuccess = null;
        BufferedWriter bws = null;
        try  {        	
        	fileReader = new FileReader(sourceFile);
            br = new BufferedReader(fileReader);
            fileWriterFail = new FileWriter(failedValidationFile);
            bwf = new BufferedWriter(fileWriterFail);
            fileWriterSuccess = new FileWriter(successValidationFile);
            bws = new BufferedWriter(fileWriterSuccess);
            
            while ((line = br.readLine()) != null) {
            	if (line.contains(ROWID)) {
            		bws.write(line+ NEWLINE);
            		bwf.write(line+ NEWLINE);
            		continue;
            	}
                // use comma as separator
                String[] zip = line.split(DELIMETER);
                // Calling method validatePostCodePerformanceFix for better performance
                if(PostCodeValidator.validatePostCodePerformanceFix(zip[1])) {                	
                	succeededPostCodesLst.add(new PostCodeData(Integer.parseInt(zip[0]), zip[1]));
                } else {
                	failedPostCodesLst.add(new PostCodeData(Integer.parseInt(zip[0]), zip[1]));
                }
            }
            
    		// Starting two threads
            Thread t1 = new Thread(new ProcessListTask(failedPostCodesLst, bwf));
            Thread t2 = new Thread(new ProcessListTask(succeededPostCodesLst, bws));
            t1.start();
            t2.start();            
            try {
            	t1.join();
				t2.join();
			} catch (InterruptedException e) {				
				LOGGER.info("Interrupted exception occured while main thread joining other threads " + e);
			}           
            
            LOGGER.info("Total invalid postcodes are : " + failedPostCodesLst.size());
            LOGGER.info("Total valid postcodes are : " + succeededPostCodesLst.size());

        } catch (IOException e) {
        	LOGGER.info("Following I/O exception occured while read write " + e);
        	throw e;
        } finally {
        	try{
        	br.close();
        	fileReader.close();  
        	bwf.close();
        	fileWriterFail.close();
        	bws.close();
        	fileWriterSuccess.close();
        	} catch (Exception e){
        		LOGGER.info("Following exception occured while closing the file/buffer reader " + e);
        	}
        }
	}
	
}
