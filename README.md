# CSCI 4055 – Database Project

**Group Members:**  
- Ishant Shrestha  
- Sushant Koirala  

This project is our final group assignment for CSCI 4055.  
The goal was to build a Java Swing application that connects to a MySQL database using the COMPANY schema and allows users to search for employees based on departments and projects.

---

##  What’s Included in This Repository
- Java GUI code  
- Database manager code  
- SQL schema  
- Sample data  
- SQL queries for testing  

---

##  What the Program Does
The Java Swing program opens a window (built using Eclipse Window Builder) where the user can:

- Enter a database name  
- Load (Fill) all Departments and Projects  
- Select multiple departments  
- Select multiple projects  
- Use “Not” checkboxes to find:
  - employees **not** in a department  
  - employees **not** working on a project  
- Search for employees based on selected filters  
- Clear all selections and results  

All results appear in a scrollable employee list.

---

##  Project Files

### **src (Java Code)**

#### **1. EmployeeSearchFrame.java**
- Main GUI window  
- Contains:
  - Database name field  
  - Department, Project, Employee lists  
  - “Not” checkboxes  
  - Fill, Search, Clear buttons  
- Handles user interactions  
- Uses DatabaseManager to run SQL queries  

#### **2. DatabaseManager.java**
- Connects to MySQL using JDBC  
- Loads department list  
- Loads project list  
- Runs employee search queries based on:
  - selected departments  
  - selected projects  
  - “Not” checkboxes  
- Returns list of matching employees  

#### **3. Main.java**
- Simple launcher  
- Opens EmployeeSearchFrame  

---

### **sql (Database Files)**

#### **1. company_schema.sql**
Creates all COMPANY tables:
- DEPARTMENT  
- EMPLOYEE  
- PROJECT  
- WORKS_ON  

#### **2. sample_data.sql**
- Inserts sample departments, projects, employees, and works_on entries  

#### **3. queries.sql**
- Contains SQL queries used for testing  
- Helps verify search logic  

---

##  Expected Database Setup
The program works with a MySQL database using the COMPANY schema.

Tables required:
- DEPARTMENT (DNUMBER, DNAME, …)  
- EMPLOYEE (SSN, FNAME, MINIT, LNAME, DNO, …)  
- PROJECT (PNUMBER, PNAME, DNUM, …)  
- WORKS_ON (ESSN, PNO, HOURS, …)  

IMPORTANT: Connection Credentials Connection settings are hardcoded in **DatabaseManager.java**

Connection settings can be changed in **DatabaseManager.java**:
- username  
- password  
- JDBC URL  

---

##  How to Run the Project

### **In Eclipse**
1. Open Eclipse  
2. Create a new Java Project  
3. Copy everything from our `src` folder into Eclipse’s `src`  
4. Refresh the project  
5. Add the MySQL JDBC driver to the Build Path  
6. Run `Main.java`  

### **In MySQL**
1. Create a database (e.g., `companydb`)  
2. Run:
   - `company_schema.sql`
   - `sample_data.sql`
3. Open the Java program  
4. Enter your database name  
5. Press **Fill** to load departments and projects  

---

##  Project Status
-  All required functions completed  
-  Java files finished  
-  SQL schema & sample data included  
-  Ready for testing and submission  

---
