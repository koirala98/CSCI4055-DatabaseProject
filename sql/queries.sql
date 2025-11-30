
-- COMPANY DATABASE — GUI QUERIES

-- Load all Departments
SELECT Dname, Dnumber
FROM DEPARTMENT
ORDER BY Dname;


-- Load all Projects
SELECT Pname, Pnumber
FROM PROJECT
ORDER BY Pname;


-- Employees IN a department
-- (Parameter: department number)
SELECT *
FROM EMPLOYEE
WHERE Dno = ?;


-- Employees NOT IN a department
SELECT *
FROM EMPLOYEE
WHERE Dno <> ?;


-- Employees IN a project
SELECT E.*
FROM EMPLOYEE E
JOIN WORKS_ON W ON E.Ssn = W.Essn
WHERE W.Pno = ?;


-- Employees NOT IN a project
SELECT *
FROM EMPLOYEE
WHERE Ssn NOT IN (
    SELECT Essn FROM WORKS_ON WHERE Pno = ?
);


-- Employees IN a department AND IN a project
SELECT E.*
FROM EMPLOYEE E
JOIN WORKS_ON W ON E.Ssn = W.Essn
WHERE E.Dno = ?
  AND W.Pno = ?;


-- Employees IN department BUT NOT in project
SELECT E.*
FROM EMPLOYEE E
WHERE E.Dno = ?
  AND E.Ssn NOT IN (
        SELECT Essn FROM WORKS_ON WHERE Pno = ?
    );


-- Employees IN project BUT NOT in department
SELECT E.*
FROM EMPLOYEE E
JOIN WORKS_ON W ON E.Ssn = W.Essn
WHERE W.Pno = ?
  AND E.Dno <> ?;


-- Employees NOT in department AND NOT in project
SELECT *
FROM EMPLOYEE
WHERE Dno <> ?
  AND Ssn NOT IN (
        SELECT Essn FROM WORKS_ON WHERE Pno = ?
    );
