# postcode-validation-app

#Introduction 
This is a Technical Task to cover the following : - 
  + Part 1 - Postcode validation for all UK postcodes
  + Part 2 - Bulk import to check against 2 million records and produce results in an output file
  + Part 3 - Performance engineering to measure the performance of the above solution and ways to optimize without compromizing the output

#Implementation details
	+ the backend of the application uses core Java SE8, EE with spring boot
	+ the project compiles on a machine with Maven 3.3 and Java SE8 
	+ the website runs on 8080 port of ‘localhost’ i.e. http://localhost:8080/local/, via embedded application server (e.g. Jetty) in spring boot 
	+ In memory H2 database is used http://localhost:8080/local/console/login.do
#Set-up
	+ Maven build project using command mvn:install which compiles the application, creates a jar(app-notes-api.jar) and runs the Junit tests.
	+ Run java command to run this spring boot jar java –jar app-notes-api.jar from /target folder
	+ Logs are generated at /var/log/application/notes-api

#Usage
##Dependencies
Included dependencies for spring boot, spring data jpa and junit 
##How to Run
	+ Hit URL on browser - http://localhost:8080/local/ 
	+ Input - Enter note id or leave blank in the search input box provided 
	+ or Hit create new note and enter note in the main area to create notes
	+ or Click on any of the notes in notes list to modify or delete notes 
	+ Result - The notes data is displayed in list format etc.
##Known Issue
	+ H2 dependency may not compile with maven cause of artifact id com.h2database is different from package structure org.h2 so please add it manually to classpath 
#To do
	+ UI can be further enhanced to do more validation and proper error response
	+ More exception handling can be put in place


