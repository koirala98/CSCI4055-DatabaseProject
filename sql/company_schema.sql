
-- COMPANY DATABASE SCHEMA
USE COMPANYDB;
SET FOREIGN_KEY_CHECKS = 0;
CREATE TABLE EMPLOYEE (
    Fname VARCHAR(15) NOT NULL,
    Minit CHAR,
    Lname VARCHAR(15) NOT NULL,
    Ssn CHAR(9) NOT NULL,
    Bdate DATE,
    Address VARCHAR(30),
    Sex CHAR,
    Salary DECIMAL(10,2),
    Super_ssn CHAR(9),
    Dno INT,
    PRIMARY KEY (Ssn)
);

CREATE TABLE DEPARTMENT (
    Dname VARCHAR(15) NOT NULL,
    Dnumber INT NOT NULL,
    Mgr_ssn CHAR(9) NOT NULL,
    Mgr_start_date DATE,
    PRIMARY KEY (Dnumber)
);

CREATE TABLE DEPT_LOCATIONS (
    Dnumber INT NOT NULL,
    Dlocation VARCHAR(15) NOT NULL,
    PRIMARY KEY (Dnumber, Dlocation)
);

CREATE TABLE PROJECT (
    Pname VARCHAR(15) NOT NULL,
    Pnumber INT NOT NULL,
    Plocation VARCHAR(15),
    Dnum INT NOT NULL,
    PRIMARY KEY (Pnumber),
    UNIQUE (Pname)
);

CREATE TABLE WORKS_ON (
    Essn CHAR(9) NOT NULL,
    Pno INT NOT NULL,
    Hours DECIMAL(3,1),
    PRIMARY KEY (Essn, Pno)
);

CREATE TABLE DEPENDENT (
    Essn CHAR(9) NOT NULL,
    Dependent_name VARCHAR(15) NOT NULL,
    Sex CHAR,
    Bdate DATE,
    Relationship VARCHAR(8),
    PRIMARY KEY (Essn, Dependent_name)
);

-- FOREIGN KEYS
ALTER TABLE EMPLOYEE 
    ADD CONSTRAINT fk_supervisor
    FOREIGN KEY (Super_ssn) REFERENCES EMPLOYEE(Ssn);

ALTER TABLE EMPLOYEE 
    ADD CONSTRAINT fk_department
    FOREIGN KEY (Dno) REFERENCES DEPARTMENT(Dnumber);

ALTER TABLE DEPARTMENT 
    ADD CONSTRAINT fk_manager
    FOREIGN KEY (Mgr_ssn) REFERENCES EMPLOYEE(Ssn);

ALTER TABLE DEPT_LOCATIONS 
    ADD CONSTRAINT fk_loc_department
    FOREIGN KEY (Dnumber) REFERENCES DEPARTMENT(Dnumber);

ALTER TABLE PROJECT 
    ADD CONSTRAINT fk_control_department
    FOREIGN KEY (Dnum) REFERENCES DEPARTMENT(Dnumber);

ALTER TABLE WORKS_ON 
    ADD CONSTRAINT fk_work_employee
    FOREIGN KEY (Essn) REFERENCES EMPLOYEE(Ssn);

ALTER TABLE WORKS_ON 
    ADD CONSTRAINT fk_work_project
    FOREIGN KEY (Pno) REFERENCES PROJECT(Pnumber);

ALTER TABLE DEPENDENT 
    ADD CONSTRAINT fk_depend_employee
    FOREIGN KEY (Essn) REFERENCES EMPLOYEE(Ssn);

