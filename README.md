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
None

##How to Run
	+ Hit URL on browser - http://localhost:8080/local/ 
	+ private static final String SOURCE_FILE = "C:/import_data.csv";
	+ Input - Enter note id or leave blank in the search input box provided 
	+ or Hit create new note and enter note in the main area to create notes
	+ or Click on any of the notes in notes list to modify or delete notes 
	+ Result - The notes data is displayed in list format etc.

#Further Read
	+ ANALYSIS.md covers the following
		+ How the regular expression provided doesn't deal with all UK postcode edge cases
		+ Performance improvement analysis and the impact of these changes


