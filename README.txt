README CSE545-HW1 
Daniela Martignani

a.	How to compile/run your application
	The application was developed in Java, as a console mode, using Eclipse IDE. 
	When the application starts running, it will connect to the mysql database university1, using the specific username and password and using the appropriate 
	mysql driver.
	Then, after a successful connection was established, it will display an option's menu to the user. The user will have a total of six choices,
	(0 to exit the menu, 1 to display a list of students, 2 to display a list of courses and the courses with prerequisites, 3 to lookup a student's grades,
	4 to display all available departments, 5 to delete a record - either a student record or a course record). 
	The main menu will repeat continuously until the user chooses to exit (option 0). 
	A nested menu for option 5 will allow the user to select the option of deleting a student (option A), deleting a course (option B), 
	or exit option 5 by using 'done'. 
	Also, for ease of use, both option A and option B (of menu's option 5) will first display the original set of records, then after the deletion takes place,
	the new list of records will be displayed, along with an update message, to allow the user to visualize the result of the delete query. 
	
b.	Short overview of your database schema (describe your columns, what type of data exists, etc.)
	The university1 schema used in this application consists of 5 tables (student, course, prerequisites, section and grade_report).
	Below is a brief overview of the schema, tables, corresponding attributes and attribute types, as well as each primary key and corresponding 
	foreign keys. Also, as specified in the requirements, the foreign keys option of cascade was given on every update and delete operation, 
	allowing foreign records to be updated/deleted when a specific record with the primary key is updated/deleted.
	Some attributes can be null and some attributes from the original university schema were renamed, after a conflict on using the same name for 
	foreign keys on MySQL Workbench.
	TABLE STUDENT
		(name							VARCHAR(30)			NOT NULL,
		 student_number					VARCHAR(15)			NOT NULL,
		 class							VARCHAR(15)			
		 major							VARCHAR(35)
	  PRIMARY KEY(student_number));
	TABLE COURSE
		(course_name					VARCHAR(40)			NOT NULL,
		 course_number					VARCHAR(20)			NOT NULL,
		 credit_hours					INT					NOT NULL,
		 department						VARCHAR(8)			NOT NULL,
	  PRIMARY KEY(course_number));
	TABLE PREREQUISITES
		(course_number					VARCHAR(20)			NOT NULL,
		 prerequisite_number			VARCHAR(30)			NOT NULL,
      PRIMARY KEY(course_number, prerequisite_number),
	  FOREIGN KEY(course_number) REFERENCES COURSE(course_number));
	  FOREIGN KEY OPTIONS: On Update Cascade, On Delete Cascade
	TABLE SECTION
		(section_identifier				VARCHAR(25)			NOT NULL,
		 course_number_foreign			VARCHAR(20)			NOT NULL,
		 semester						VARCHAR(15)			
		 year							INT
		 instructor						VARCHAR(30)
	  PRIMARY KEY(section_identifier),
	  FOREIGN KEY(course_number_foreign) REFERENCES COURSE(course_number));
	  FOREIGN KEY OPTIONS: On Update Cascade, On Delete Cascade
	TABLE GRADE_REPORT
		(student_number_foreign			VARCHAR(15)			NOT NULL,
		 section_identifier_foreign		VARCHAR(25)			NOT NULL,
		 grade							DECIMAL(2,1)		
	  PRIMARY KEY(student_number_foreign, section_identifier_foreign),
	  FOREIGN KEY(student_number_foreign) REFERENCES STUDENT(student_number),
	  FOREIGN KEY(section_identifier_foreign) REFERENCES SECTION(section_identifier));
	  FOREIGN KEY OPTIONS: On Update Cascade, On Delete Cascade
