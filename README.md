# postcode-validation-app

#Introduction 
This is a Technical Task to cover the following : - 
  + Part 1 - Postcode validation for all UK postcodes
  + Part 2 - Bulk import to check against 2 million records and produce results in an output file
  + Part 3 - Performance engineering to measure the performance of the above solution and ways to optimize without compromizing the output

#Implementation details
	+ Java SE1.7 is used without any external libraries
	+ Junit tests are available to run each part in the task
	
#Dependencies
junit version 4.11

##How to Run
	+ Make sure a csv source file with row_ids and postcodes available under C:/import_data.csv
	+ Run Junit tests in class PostCodeValidatorTest.java for Part 1
	+ Run Junit tests in class BulkImportTest.java for Part 2 & 3
	+ private static final String SOURCE_FILE = "C:/import_data.csv" 
	+ Result - Junits will be success or fail and files will be generated with invalid/valid postcodes under C drive.

#Further Read
	+ ANALYSIS.md covers the following
		+ How the regular expression provided doesn't deal with all UK postcode edge cases
		+ Performance improvement analysis and the impact of these changes


