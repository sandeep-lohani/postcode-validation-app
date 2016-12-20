package com.nhs.pc.task;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.nhs.pc.model.PostCodeData;

public class ProcessListTask implements Runnable {
	
	private static Logger LOGGER = Logger.getLogger("ProcessListTask");
	private static final String DELIMETER = ",";
	private static final String NEWLINE = "\n";	
	private List<PostCodeData> postCodesLst;
	private BufferedWriter bwf;

	public ProcessListTask(List<PostCodeData> postCodesLst, BufferedWriter bwf) {
		super();
		this.postCodesLst = postCodesLst;
		this.bwf = bwf;
	}

	@Override
	public void run() {
		Collections.sort(postCodesLst);
		
		for(PostCodeData postCodeData:postCodesLst){
        	try {
        		bwf.write(postCodeData.getRowNum() + DELIMETER + postCodeData.getPostCode() + NEWLINE);
			} catch (IOException e) {							
				LOGGER.info("Following I/O exception occured while write " + e);
			}
        }
		
	}

}
