package com.nhs.pc.model;

/**
 * @author Sandeep
 * Model class to maintain post code data in file.
 */
public class PostCodeData implements Comparable<PostCodeData>{
	
	private static final String DELIMETER = ",";
	
	public PostCodeData(Integer rowNum, String postCode) {
		super();
		this.rowNum = rowNum;
		this.postCode = postCode;
	}
	private Integer rowNum;
	private String postCode;	
	
	public Integer getRowNum() {
		return rowNum;
	}
	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	@Override
	public String toString() {
		return rowNum + DELIMETER + postCode;
	}
	@Override
	public int compareTo(PostCodeData o) {		
		return this.rowNum - o.rowNum;
	}
}
