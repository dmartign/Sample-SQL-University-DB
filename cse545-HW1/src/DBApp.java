/*
 * Author: Daniela Martignani
 * CSE545 - Oakland University - Winter2016
 */

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBApp {

	public static void main(String[] args) throws SQLException, IOException, InstantiationException, IllegalAccessException {
		
		Connection conn = null;
		int input = 0;
	
		// connecting to mysql database, schema university1
		try { Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException x) {
			System.out.println("Driver could not be loaded");
		}
		String studentName, studentID, classLevel, major;
		
		try {
		conn = DriverManager.getConnection("jdbc:mysql://localhost/university1?" +
                "user=root&password=danimaster15");
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		// process query section
		do {
			System.out.println("\n===========================================================================");
			System.out.println("WELCOME TO THE UNIVERSITY DATABASE");
			System.out.println("Directions:");
			System.out.println("To exit the menu, enter 0");
			System.out.println("To display a list of students and their information, enter 1");
			System.out.println("To display the list of available courses and their prerequisites, enter 2");
			System.out.println("To be able to lookup a student's grade by section, enter 3");
			System.out.println("To show a list of all available departments, enter 4");
			System.out.println("To delete a record on the database, enter 5");
			System.out.println(">> ");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			input = Integer.parseInt(in.readLine());
		
			if (input == 0){
				System.out.println("Exiting database menu.");
				break;
			} // ends option 0
			else if (input == 1){
				// query for list of students
				String stmt1 = "SELECT name, student_number, class, major FROM student";
				Statement s = conn.createStatement();
				ResultSet r = s.executeQuery(stmt1);
				System.out.println("Student\t\t\t\tStudentID\tClass Level\t\t\tMajor");
				System.out.println("_________________________________________________________________________________________________________");
				while (r.next()) {
					studentName = r.getString(1);
					studentID = r.getString(2);
					classLevel = r.getString(3);
					major = r.getString(4);
					String format = "%1$-30s%2$-20s%3$-20s%4$-20s\n";
					System.out.format(format, studentName, studentID, classLevel, major);
					System.out.println("_________________________________________________________________________________________________________");
				}
			} // ends option 1
			else if (input == 2) {
				// query for list of available courses 
				System.out.println("\nComplete list of courses:\n");
				String stmt2a = "SELECT * FROM course";
				Statement s1 = conn.createStatement();
				ResultSet r1 = s1.executeQuery(stmt2a);
				System.out.println("Course Name\t\t\t\t\tCourse Number\tCredit Hours\tDepartment");
				System.out.println("__________________________________________________________________________________________");
				while (r1.next()) {
					String cCourseName = r1.getString(1);
					String cCourseNumber = r1.getString(2);
					String creditHrs = r1.getString(3);
					String dept = r1.getString(4);
					String format = "%1$-50s%2$-20s%3$-10s%4$-10s\n";
				    System.out.format(format, cCourseName, cCourseNumber, creditHrs, dept);
					System.out.println("__________________________________________________________________________________________");
				}
				// query for list of courses with prerequisites
				System.out.println("\nList of courses that have prerequisites:\n");
				String stmt2 = "SELECT course.course_name, prerequisites.course_number, prerequisites.prerequisite_number FROM course, prerequisites "
						+ "WHERE course.course_number = prerequisites.course_number";
				Statement s = conn.createStatement();
				ResultSet r = s.executeQuery(stmt2);
				System.out.println("Course Name\t\t\tCourse Number\tPrerequisite Course(s) Number(s)");
				System.out.println("__________________________________________________________________________________________");
				while (r.next()) {
					String cCourseName = r.getString(1);
					String pCourseNumber = r.getString(2);
					String pPrereqNumber = r.getString(3);
					String format = "%1$-30s%2$-20s%3$-40s\n";
				    System.out.format(format, cCourseName, pCourseNumber, pPrereqNumber);
					System.out.println("__________________________________________________________________________________________");
				}
			} // ends option 2
			else if (input == 3) {
				// query for lookup of a student's grade by section
				System.out.println("Please, enter the name (first name and last name) of the student to find: ");
				BufferedReader studentIn = new BufferedReader(new InputStreamReader(System.in));
				String studentToLook = studentIn.readLine();
				String stmt3 = "SELECT student.name, grade_report.section_identifier_foreign, grade_report.grade "
						+ "FROM grade_report, student WHERE grade_report.student_number_foreign = student.student_number";   
				Statement s = conn.createStatement();
				ResultSet r = s.executeQuery(stmt3);
				System.out.println("Student\t\t\t\tSectionID\tSection Grade" );
				System.out.println("__________________________________________________________________");
				while (r.next()) {
					String student = r.getString(1);
					String sectionID = r.getString(2);
					String sectionGrade = r.getString(3);
					if (student.equalsIgnoreCase(studentToLook)) {
						String format = "%1$-30s%2$-20s%3$-20s\n";
					    System.out.format(format, student, sectionID, sectionGrade);
						System.out.println("__________________________________________________________________");
					}
				}
			} // ends option 3
			else if (input == 4) {
				// query for listing all available departments
				String stmt4 = "SELECT DISTINCT department FROM course";
				Statement s = conn.createStatement();
				ResultSet r = s.executeQuery(stmt4);
				System.out.println("Department Name");
				System.out.println("______________________________");
				while (r.next()) {
					String deptName = r.getString(1);
					String format = "%1$-30s\n";
				    System.out.format(format, deptName); 
					System.out.println("______________________________");
				}
			} // ends option 4
			else if (input == 5){
				// query to delete a row and cascade the deletion to all foreign records
				String itemToDelete;
				do {
					System.out.println("\nPlease, enter A for deleting a student and B for deleting a course, or type done to exit this option: ");
					BufferedReader toDelete = new BufferedReader(new InputStreamReader(System.in));
					itemToDelete = toDelete.readLine();
					if (itemToDelete.equalsIgnoreCase("a")) {
						// delete a student record
						// displaying original student list
						String stmt5a = "SELECT * FROM student";
						Statement s = conn.createStatement();
						ResultSet r = s.executeQuery(stmt5a);
						System.out.println("\nOriginal Student List:\n");
						System.out.println("Student\t\t\t\tStudentID\tClass Level\t\t\tMajor");
						System.out.println("_________________________________________________________________________________________________________");
						while (r.next()) {
							String output1 = r.getString(1);
							String output2 = r.getString(2);
							String output3 = r.getString(3);
							String output4 = r.getString(4);
							String format = "%1$-30s%2$-20s%3$-20s%4$-20s\n";
							System.out.format(format, output1, output2, output3, output4);
							System.out.println("_________________________________________________________________________________________________________");
						}
						// finding element to delete and deleting student record
						System.out.println("\nPlease, enter the first name and last name of the student to delete: ");
						BufferedReader personToDelete = new BufferedReader(new InputStreamReader(System.in));
						String person = personToDelete.readLine();
						String stmt5 = "DELETE FROM student WHERE name = '"+person+"'";
						String stmt5b = "SELECT * FROM student";
						Statement s1 = conn.createStatement();
						int r1 = s1.executeUpdate(stmt5);
						System.out.println("Deleted(updated) " + r1 + " record(s)"); // returns an integer if updated/deleted
						Statement s2 = conn.createStatement();
						ResultSet r2 = s2.executeQuery(stmt5b);
						// displaying the new student list
						System.out.println("\nNew Student List:\n");
						System.out.println("Student\t\t\t\tStudentID\tClass Level\t\t\tMajor");
						System.out.println("_________________________________________________________________________________________________________");
						while (r2.next()) {
							String output1 = r2.getString(1);
							String output2 = r2.getString(2);
							String output3 = r2.getString(3);
							String output4 = r2.getString(4);
							String format = "%1$-30s%2$-20s%3$-20s%4$-20s\n";
							System.out.format(format, output1, output2, output3, output4);
							System.out.println("_________________________________________________________________________________________________________");
						}
					}
					else if (itemToDelete.equalsIgnoreCase("b")) {
						// delete a course record
						// displaying original course list
						String stmt5a = "SELECT * FROM course";
						Statement s = conn.createStatement();
						ResultSet r = s.executeQuery(stmt5a);
						System.out.println("\nOriginal Course List:\n");
						System.out.println("Course Name\t\t\t\t\tCourse Number\tCredit Hours\tDepartment");
						System.out.println("____________________________________________________________________________________________");
						while (r.next()) {
							String output1 = r.getString(1);
							String output2 = r.getString(2);
							String output3 = r.getString(3);
							String output4 = r.getString(4);
							String format = "%1$-50s%2$-20s%3$-10s%4$-20s\n";
						    System.out.format(format, output1, output2, output3, output4);
							System.out.println("____________________________________________________________________________________________");
						}
						// finding element to delete and deleting course record
						System.out.println("\nPlease, enter the course number to be deleted (Example - CSE100): ");
						BufferedReader courseToDelete = new BufferedReader(new InputStreamReader(System.in));
						String course = courseToDelete.readLine();
						String stmt5 = "DELETE FROM course WHERE course_number = '"+course+"'";
						String stmt5b = "SELECT * FROM course";
						Statement s1 = conn.createStatement();
						int r1 = s1.executeUpdate(stmt5);
						System.out.println("Deleted(updated) " + r1 + " record(s)"); // returns integer if updated/deleted
						Statement s2 = conn.createStatement();
						ResultSet r2 = s2.executeQuery(stmt5b);
						// displaying new list after deleting record
						System.out.println("\nNew Course List:\n");
						System.out.println("Course Name\t\t\t\t\tCourse Number\tCredit Hours\tDepartment");
						System.out.println("____________________________________________________________________________________________");
						while (r2.next()) {
							String output1 = r2.getString(1);
							String output2 = r2.getString(2);
							String output3 = r2.getString(3);
							String output4 = r2.getString(4);
							String format = "%1$-50s%2$-20s%3$-10s%4$-20s\n";
						    System.out.format(format, output1, output2, output3, output4);
							System.out.println("____________________________________________________________________________________________");
						}
					}
					else if (itemToDelete.equalsIgnoreCase("done")){ // leave the main menu
						break;
					}
					else {
						System.out.println("\nERROR: Invalid choice, must choose either A or B to delete, or done to exit. Back to main menu.\n");
					}
				} while (itemToDelete.equalsIgnoreCase("a") || itemToDelete.equalsIgnoreCase("b") || itemToDelete.equalsIgnoreCase("done"));
			} // ends option 5
			else {
				System.out.println("Invalid choice, please choose an option between 0 and 5\n");
			}
			
		} while (input > 0 && input <= 5);
		
	} // ends main

} // ends class
