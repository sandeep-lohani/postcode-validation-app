package com.nhs.pc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.nhs.pc.model.PostCodeData;

/**
 * Class to import postcodes from a file, validate and write to other files
 * @author Sandeep
 *
 */
public class BulkImportInitial {
	private static Logger LOGGER = Logger.getLogger("BulkImportInitial");

	private static final String DELIMETER = ",";
	private static final String ROWID = "row_id";
	private static final String NEWLINE = "\n";
	
	/**
	 * Load post codes from source file, validate and write failed ones to a new file
	 * @param sourceFile
	 * @param failedValidationFile
	 */
	public void bulkImportPartTwo (String sourceFile, String failedValidationFile) throws IOException{
		String line = "";
        FileReader fileReader = null;
        BufferedReader br = null;
        FileWriter fileWriterFail = null;
        BufferedWriter bwf = null;
        try  {        	
        	fileReader = new FileReader(sourceFile);
            br = new BufferedReader(fileReader);
            fileWriterFail = new FileWriter(failedValidationFile);
            bwf = new BufferedWriter(fileWriterFail);
            
            while ((line = br.readLine()) != null) {
            	if (line.contains(ROWID)) {
            		bwf.write(line+ NEWLINE);
            		continue;
            	}
                // use comma as separator
                String[] zip = line.split(DELIMETER);

                if(!PostCodeValidator.validatePostCode(zip[1])) {  
                	//Write the failed post code to the file
                	bwf.write(line + NEWLINE);                	                	
                }
            }    		 
            
        } catch (IOException e) {
        	LOGGER.info("Following exception occured while processing file " + failedValidationFile + " : " + e);
            throw e;            
        } finally {
        	try{
        	br.close();
        	fileReader.close();  
        	bwf.close();
        	fileWriterFail.close();
        	} catch (Exception e){
        		LOGGER.info("Following exception occured while closing the file/buffer reader " + e);
        	}
        }		
	}
	
	/**
	 * Load post codes from source file, validate and write both failed and succeeded ones to new files in a sorted order
	 * @param sourceFile
	 * @param failedValidationFile
	 * @param successValidationFile
	 */
	public void bulkImportPartThree(String sourceFile, String failedValidationFile, String successValidationFile)  throws IOException{
		List<PostCodeData> failedPostCodesLst = new ArrayList<>();
		List<PostCodeData> succeededPostCodesLst = new ArrayList<>();
		String line = "";
		FileReader fileReader = null;
		BufferedReader br = null;
		FileWriter fileWriterFail = null;
		BufferedWriter bwf = null;
		FileWriter fileWriterSuccess = null;
		BufferedWriter bws = null;
		try {
			fileReader = new FileReader(sourceFile);
			br = new BufferedReader(fileReader);
			fileWriterFail = new FileWriter(failedValidationFile);
			bwf = new BufferedWriter(fileWriterFail);
			fileWriterSuccess = new FileWriter(successValidationFile);
			bws = new BufferedWriter(fileWriterSuccess);

			while ((line = br.readLine()) != null) {
				if (line.contains(ROWID)) {
					bws.write(line + NEWLINE);
					bwf.write(line + NEWLINE);
					continue;
				}
				// use comma as separator
				String[] zip = line.split(DELIMETER);

				if (PostCodeValidator.validatePostCode(zip[1])) {
					succeededPostCodesLst.add(new PostCodeData(Integer.parseInt(zip[0]), zip[1]));
				} else {
					failedPostCodesLst.add(new PostCodeData(Integer.parseInt(zip[0]), zip[1]));
				}
			}

			Collections.sort(failedPostCodesLst);

			for (PostCodeData postCodeData : failedPostCodesLst) {
				bwf.write(postCodeData.getRowNum() + DELIMETER	+ postCodeData.getPostCode() + NEWLINE);
			}

			Collections.sort(succeededPostCodesLst);

			for (PostCodeData postCodeData : succeededPostCodesLst) {
				bws.write(postCodeData.getRowNum() + DELIMETER	+ postCodeData.getPostCode() + NEWLINE);
			}

			LOGGER.info("Total invalid postcodes are : " + failedPostCodesLst.size());
			LOGGER.info("Total valid postcodes are : "	+ succeededPostCodesLst.size());

		} catch (IOException e) {
			LOGGER.info("Following I/O exception occured while read write " + e);
			throw e; 
		} finally {
			try {
				br.close();
				fileReader.close();
				bwf.close();
				fileWriterFail.close();
				bws.close();
				fileWriterSuccess.close();
			} catch (Exception e) {
				LOGGER.info("Following exception occured while closing the file/buffer reader "	+ e);
			}
		}
	}
}
