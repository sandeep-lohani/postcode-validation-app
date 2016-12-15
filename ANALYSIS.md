# Analysis

* The regular expression provided doesn't deal with all UK postcode edge cases

	> As per the details at https://en.wikipedia.org/wiki/Postcodes_in_the_United_Kingdom#Validation, the given REGEX doesn't cover all postcodes and miss out on some valid Special cases including most of Crown dependencies, Overseas territories and ZZ99 ones [Though not used postally, the National Health Service (NHS) and other public services have a series of pseudo-postcodes, used in records about individual people]
	+	i.e. AI-2640 or ASCN 1ZZ or ZZ99 NNN etc. are valid post codes which are flagged as invaid by the REGEX

 		
* Performance improvement analysis and the impact of these changes
	> The original solution compiles regex for each postcode to generate a pattern object and also uses serial processing which results in a higher overall 'wall' time. Compile parses the regular expression and builds an in-memory representation. The overhead to compile is significant compared to a match


	>	So to fix this in part 3 solution:-
	
	+ Regex is compiled only once to generate pattern and cached for later uses
	+ Initiated two threads in parallel to sort and write the success and failed lists to the output files.
	
*Without any adverse affect on the output which is same as in Part1&2, these fixes give a drastic improvement on the overall processing time. Here is the comparison statistics*


Run without fixes  | Run after fixes
-------------------| ---------------
Start app @ 1481763911055|Start app @ 1481764360560
Dec 15, 2016 1:05:59 AM com.nhs.pc.BulkImport bulkImportPartThree|Dec 15, 2016 1:12:58 AM com.nhs.pc.BulkImport bulkImportPartThreePerformanceFix
INFO: Total invalid postcodes are : 19365|INFO: Total invalid postcodes are : 19365
Stop app @ 1481763959462|Stop app @ 1481764378565
Total time taken is **48407 milli seconds**|Total time taken is **18005 milli seconds**
INFO: Total valid postcodes are : 2128489  | INFO: Total valid postcodes are : 2128489






