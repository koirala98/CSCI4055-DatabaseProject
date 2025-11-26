# CSCI 4055 Database Project – Final Template

This project is a complete Java Swing application that matches the database project requirements:

- Uses a window similar to the provided template.
- Loads Departments and Projects from a database that follows the COMPANY schema.
- Allows multiple selection of Departments and Projects.
- Provides scrollable lists for Departments, Projects, and Employees.
- Supports searching for Employees that are in / not in the selected Departments and/or Projects.
- Implements Fill and Clear buttons with the required behavior.
- Includes “Not” check boxes for Department and Project filters.

## Files

- src/EmployeeSearchFrame.java  
  Main GUI window for the application. Contains:
  - Database name text field
  - Department, Project, and Employee lists
  - “Not” check boxes
  - Fill, Search, and Clear buttons

- src/DatabaseManager.java  
  Small helper class that:
  - Connects to a MySQL database by database name
  - Loads single column lists (departments, projects)
  - Runs the employee search query based on selected filters

- src/Main.java  
  Simple launcher that opens the EmployeeSearchFrame.

- sql/README-SQL.txt  
  Notes about expected tables and example queries.

## Expected Database

This template assumes a MySQL database running on localhost with a schema similar to the classic COMPANY database:

- DEPARTMENT(DNUMBER, DNAME, ...)
- PROJECT(PNUMBER, PNAME, DNUM, ...)
- EMPLOYEE(SSN, FNAME, MINIT, LNAME, DNO, ...)
- WORKS_ON(ESSN, PNO, HOURS, ...)

You can change the connection settings in `DatabaseManager.java`:

- USERNAME
- PASSWORD
- JDBC URL format

## How to Open in Eclipse

1. Open Eclipse.
2. File → New → Java Project → Name: `DatabaseProjectFinal` → Finish.
3. Copy the `src` folder contents from this project into the `src` folder of the new Eclipse project.
4. Right click the project → Refresh.
5. Right click `Main.java` → Run As → Java Application.

You can also turn this into a GitHub project by pushing this folder as your main repository contents.
