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
public class BulkImport {
	private static Logger LOGGER = Logger.getLogger("BulkImport");

	private static final String DELIMETER = ",";
	
	private static List<PostCodeData> failedPostCodesLst = new ArrayList<>();
	private static List<PostCodeData> succeededPostCodesLst = new ArrayList<>();
	private static List<BufferedWriter> objectholder = new ArrayList<>(); 

	/**
	 * Load post codes from source file, validate and write failed ones to a new file
	 * @param sourceFile
	 * @param failedValidationFile
	 */
	public void bulkImportPartTwo (String sourceFile, String failedValidationFile){
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
            	if (line.contains("row_id")) {
            		bwf.write(line+ "\n");
            		continue;
            	}
                // use comma as separator
                String[] zip = line.split(DELIMETER);

                if(!PostCodeValidator.validatePostCode(zip[1])) {  
                	//Write the failed post code to the file
                	bwf.write(zip[0] + DELIMETER + zip[1] + "\n");                	                	
                }
            }    		 
            
        } catch (IOException e) {
            e.printStackTrace();
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
	public void bulkImportPartThree(String sourceFile, String failedValidationFile, String successValidationFile) {
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
				if (line.contains("row_id")) {
					bws.write(line + "\n");
					bwf.write(line + "\n");
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
				bwf.write(postCodeData.getRowNum() + DELIMETER	+ postCodeData.getPostCode() + "\n");
			}

			Collections.sort(succeededPostCodesLst);

			for (PostCodeData postCodeData : succeededPostCodesLst) {
				bws.write(postCodeData.getRowNum() + DELIMETER	+ postCodeData.getPostCode() + "\n");
			}

			LOGGER.info("Total invalid postcodes are : " + failedPostCodesLst.size());
			LOGGER.info("Total valid postcodes are : "	+ succeededPostCodesLst.size());

		} catch (IOException e) {
			LOGGER.info("Following I/O exception occured while read write " + e);
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
	
	/**
	 * Similar to method {bulkImportPartThree} with performance fixes
	 * @param sourceFile
	 * @param failedValidationFile
	 * @param successValidationFile
	 */
	public void bulkImportPartThreePerformanceFix (String sourceFile, String failedValidationFile, String successValidationFile){
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
            
            //objectholder is used so that non final local variables can be passed to local thread scope 
            objectholder.add(bwf);
            objectholder.add(bws);
            
            while ((line = br.readLine()) != null) {
            	if (line.contains("row_id")) {
            		bws.write(line+ "\n");
            		bwf.write(line+ "\n");
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
            Thread t1 = new Thread(new Runnable() {				
				@Override
				public void run() {
					Collections.sort(failedPostCodesLst);
										
					for(PostCodeData postCodeData:failedPostCodesLst){
		            	try {
							objectholder.get(0).write(postCodeData.getRowNum() + DELIMETER + postCodeData.getPostCode() + "\n");
						} catch (IOException e) {							
							LOGGER.info("Following I/O exception occured while write " + e);
						}
		            }
				}
			});
            Thread t2 = new Thread(new Runnable() {				
				@Override
				public void run() {
					Collections.sort(succeededPostCodesLst);
					
					for(PostCodeData postCodeData:succeededPostCodesLst){
		            	try {
							objectholder.get(1).write(postCodeData.getRowNum() + DELIMETER + postCodeData.getPostCode() + "\n");
						} catch (IOException e) {							
							LOGGER.info("Following I/O exception occured while write " + e);
						}
		            }
				}
			});
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
