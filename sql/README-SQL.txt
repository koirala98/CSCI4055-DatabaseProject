SQL Notes for Database Project

This project expects a database that follows a COMPANY style schema.

Suggested minimal tables:

CREATE TABLE DEPARTMENT (
    DNUMBER INT PRIMARY KEY,
    DNAME   VARCHAR(50) NOT NULL
);

CREATE TABLE EMPLOYEE (
    SSN   CHAR(9) PRIMARY KEY,
    FNAME VARCHAR(30) NOT NULL,
    MINIT CHAR(1),
    LNAME VARCHAR(30) NOT NULL,
    DNO   INT,
    FOREIGN KEY (DNO) REFERENCES DEPARTMENT(DNUMBER)
);

CREATE TABLE PROJECT (
    PNUMBER INT PRIMARY KEY,
    PNAME   VARCHAR(50) NOT NULL,
    DNUM    INT,
    FOREIGN KEY (DNUM) REFERENCES DEPARTMENT(DNUMBER)
);

CREATE TABLE WORKS_ON (
    ESSN CHAR(9),
    PNO  INT,
    HOURS DECIMAL(5,2),
    FOREIGN KEY (ESSN) REFERENCES EMPLOYEE(SSN),
    FOREIGN KEY (PNO) REFERENCES PROJECT(PNUMBER)
);

You can change these definitions to match the schema used in your course.
Update the queries in DatabaseManager if your column or table names differ.
