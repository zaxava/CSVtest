package DatabaseTest;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class SQLiteJDBC {
	
	      static Connection c = null;	
	      static Statement stmt = null;
	      static int totalCount;
	      static int failCount;
	      static int successCount;
	      static String fileName;
	      
	public static void main( String args[] ) throws FileNotFoundException {
		 // To Store Name of the file to be opened
		  String file = args[0];
//		  String location = "C:\\Users\\zaxava\\eclipse\\";
//		  String file = location + "ms3Interview.csv";
		  fileName = file.split(".csv")[0];
		  System.out.println(fileName);
	      
	      try {
	    	 // Open database
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:test.db");
	         c.setAutoCommit(false);
	         System.out.println("Opened database successfully");
	         
	         // Create Table in database
	         stmt = c.createStatement();
	         String sql = "CREATE TABLE CSV " +
	                        "(A		TEXT		NOT NULL, " +
	                        " B		TEXT		NOT NULL, " + 
	                        " C		TEXT		NOT NULL, " + 
	                        " D		TEXT		NOT NULL, " + 
	                        " E		TEXT		NOT NULL, " + 
	                        " F		TEXT		NOT NULL, " + 
	                        " G		TEXT		NOT NULL, " +
	                        " H		BOOLEAN 	NOT NULL, " + 
	                        " I		BOOLEAN 	NOT NULL, " + 
	                        " J		TEXT		NOT NULL)";
	         stmt.executeUpdate(sql);
	         
	         //Seperate function to scan the file
	         ScanFile(file);
	         //Close Statement
	         stmt.close();
	         //Close Connection
	         c.close(); 
	         // Write log File
	         try {
	             File log = new File(fileName + ".log");
	             //Check to see if new file needs created
	             if (log.createNewFile()) {
	               System.out.println("File created: " + log.getName());
	               
	             } else {
	               System.out.println("File already exists.");
	             }
	             //Write counts to log file
	             FileWriter fw = new FileWriter(fileName + ".log");
	             fw.write("Total - " + totalCount + "\n");
	             fw.write("Fail - " + failCount + "\n");
	             fw.write("Success - " + successCount + "\n");
	             fw.close();
	           } catch (IOException e) {
	             System.out.println("An error occurred.");
	             e.printStackTrace();
	           }
			 System.out.println("Total - " + totalCount);
			 System.out.println("Fail - " + failCount);
			 System.out.println("Success - " + successCount);
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      
	   }
	public static void ScanFile(String f) {
		Scanner sc;	
	    FileInputStream fin = null;
	    // To hold each single record obtained from CSV file
	    String oneRecord = "";
	    // To hold each value of a row
	    String A1 = "";	    String B1 = "";	    String C1 = "";	    String D1 = "";	    String E1 = "";
	    String F1 = "";	    String G1 = "";	    String H1 = "";	    String I1 = "";	    String J1 = "";
		try {
			fin = new FileInputStream(f);
			sc = new Scanner(fin);
			sc.useDelimiter(",");   //sets the delimiter pattern
			// Create csv file for incorrect input lines
			File bad = new File(fileName + "bad.csv");
            if (bad.createNewFile()) {
              System.out.println("File created: " + bad.getName());
              
            } else {
              System.out.println("File already exists.");
            }
            FileWriter fw = new FileWriter(fileName + "bad.csv");
		while (sc.hasNextLine())  //returns a boolean value  
		{  
			oneRecord = sc.nextLine();
			stmt = c.createStatement();
	         String sql = oneRecord; 
	         totalCount = totalCount + 1; // increase count for every line processed
	         System.out.println(oneRecord);
	         
	         //Split each line to gather individual information
	         String[] splitRecord = oneRecord.split(",");
	         // Invalidate line if there are too many blank entries
	         if(splitRecord.length < 10) { 
	        	 System.out.println("Invaild Line");
	        	 failCount = failCount + 1;
	        	 fw.write(oneRecord + "\n");
	         } else {
		         // Invalidate line if there is an empty space that needs recorded
		         A1 = splitRecord[0]; B1 = splitRecord[1]; C1 = splitRecord[2]; D1 = splitRecord[3]; E1 = splitRecord[4]; 
		         F1 = splitRecord[5]; G1 = splitRecord[6]; H1 = splitRecord[7]; I1 = splitRecord[8]; J1 = splitRecord[9];
		         if(A1.isEmpty() || B1.isEmpty() || C1.isEmpty() || D1.isEmpty() || E1.isEmpty() || F1.isEmpty() || G1.isEmpty() || H1.isEmpty() || I1.isEmpty() || J1.isEmpty()) {
		        	 System.out.println("Invaild Line");
		        	 failCount = failCount + 1;
		        	 fw.write(oneRecord + "\n");
		         } else {
		        	 // Create Prepared Statement to be able to process special characters into the database
			         PreparedStatement pstmt = c.prepareStatement("INSERT INTO CSV (A,B,C,D,E,F,G,H,I,J) " +
		                     "VALUES (?,?,?,?,?,?,?,?,?,?);");
			         pstmt.setString(1, A1); pstmt.setString(2, B1); pstmt.setString(3, C1); pstmt.setString(4, D1); pstmt.setString(5, E1);
			         pstmt.setString(6, F1); pstmt.setString(7, G1); pstmt.setString(8, H1); pstmt.setString(9, I1); pstmt.setString(10, J1);
			         
			         pstmt.executeUpdate();
			         System.out.println("Line Recorded");
			         successCount = successCount + 1;
			         	}	
	         }
		  //find and returns the next complete token from this scanner  
		}   
		sc.close();
		fw.close();
		} catch (FileNotFoundException e) {
			System.err.println("No File Found" + e);
		} catch (SQLException e) {
			System.err.println(e);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		 
	}
	
	
	
	}