The purpose of this executable is to record any number of given lines from a csv file and to record the into a preset up database.
The executable will record all of the lines recieved and output how many were successful and how many failed as a log file in the location of the given csv file.
There will also be a different csv filed outputed with all of the failed lines that did not have enough information.

You can run this executable from the command line or any other program that executes jar files.
You will need to enter the file name that you want to give to the jar file as an argument.

I started this project with the needs to connected to a database and create a table to process all of the columns needed.
I then created a new fuction to scan the inputed csv file.
I did run into an issue where it was having a problem processing special characters.
I fixed that issue by seperating every input individually and inputing that into a prepared statement that is then uploaded into the database.
The next error was not having enough inputs in a line which was a quick fix just to make sure there was enough items in the line before seperating them into variables.
After the full successful run I implemented the log file and the csv file to be outputed.
Finally it was to set up the ablility that the jar file can run with different csv files as inputs. 