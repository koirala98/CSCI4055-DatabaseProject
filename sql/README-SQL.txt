# SQL Files for CSCI 4055 Database Project

This folder contains the SQL files used to create and populate the COMPANY style database required for our Java Swing application.

# Files

# 1. company_schema.sql
Defines all the database tables following the COMPANY schema, including:
- DEPARTMENT
- PROJECT
- EMPLOYEE
- WORKS_ON  
This file must be run first.

# 2. sample_data.sql
Inserts sample records into all tables.  
Used to test the GUI application with realistic data.

# 3. queries.sql
Contains the SQL queries used by the application, including:
- Loading department names  
- Loading project names  
- Searching employees based on selected filters  

# How to Use
1. Create a new MySQL database.
2. Run `company_schema.sql` to create tables.
3. Run `sample_data.sql` to insert data.
4. Use the queries from `queries.sql` in the Java application (DatabaseManager.java).

These SQL files are required for the Java GUI to load and search employee information.
