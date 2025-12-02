import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * DatabaseManager
 *
 * Handles connecting to the database and running queries.
 */
public class DatabaseManager {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String URL_PREFIX = "jdbc:mysql://localhost:3306/";

    private Connection conn;

    public boolean connect(String dbName, JFrame parent) {
        close();
        try {
            String url = URL_PREFIX + dbName;
            conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parent, "Could not open database: " + ex.getMessage());
            conn = null;
            return false;
        }
    }

    public boolean isConnected() {
        return conn != null;
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                // ignore
            } finally {
                conn = null;
            }
        }
    }

    public List<String> loadSingleColumn(String sql) throws SQLException {
        List<String> results = new ArrayList<String>();
        if (conn == null) {
            return results;
        }

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                results.add(rs.getString(1));
            }
        }
        return results;
    }

    /**
     * Search employees based on departments, projects, and NOT flags.
     * This method assumes a COMPANY style schema:
     * - EMPLOYEE(SSN, FNAME, MINIT, LNAME, DNO)
     * - DEPARTMENT(DNUMBER, DNAME)
     * - PROJECT(PNUMBER, PNAME, DNUM)
     * - WORKS_ON(ESSN, PNO)
     */
    public List<String> searchEmployees(
            List<String> deptNames,
            boolean notDept,
            List<String> projNames,
            boolean notProj) throws SQLException {

        List<String> results = new ArrayList<String>();
        if (conn == null) {
            return results;
        }

        StringBuilder sql = new StringBuilder();
        List<String> params = new ArrayList<String>();

        sql.append("SELECT DISTINCT E.FNAME, E.MINIT, E.LNAME ");
        sql.append("FROM EMPLOYEE E ");

        boolean hasDept = deptNames != null && !deptNames.isEmpty();
        boolean hasProj = projNames != null && !projNames.isEmpty();

        if (hasDept && !notDept) {
            sql.append("JOIN DEPARTMENT D ON E.DNO = D.DNUMBER ");
        }

        if (hasProj && !notProj) {
            sql.append("JOIN WORKS_ON W ON E.SSN = W.ESSN ");
            sql.append("JOIN PROJECT P ON W.PNO = P.PNUMBER ");
        }

        boolean whereAdded = false;

        if (hasDept) {
            if (!notDept) {
                sql.append(whereAdded ? " AND " : " WHERE ");
                whereAdded = true;
                sql.append("D.DNAME IN (");
                for (int i = 0; i < deptNames.size(); i++) {
                    if (i > 0) {
                        sql.append(", ");
                    }
                    sql.append("?");
                    params.add(deptNames.get(i));
                }
                sql.append(") ");
            } else {
                sql.append(whereAdded ? " AND " : " WHERE ");
                whereAdded = true;
                sql.append("E.DNO NOT IN (");
                sql.append("SELECT DNUMBER FROM DEPARTMENT WHERE DNAME IN (");
                for (int i = 0; i < deptNames.size(); i++) {
                    if (i > 0) {
                        sql.append(", ");
                    }
                    sql.append("?");
                    params.add(deptNames.get(i));
                }
                sql.append(")) ");
            }
        }

        if (hasProj) {
            if (!notProj) {
                sql.append(whereAdded ? " AND " : " WHERE ");
                whereAdded = true;
                sql.append("P.PNAME IN (");
                for (int i = 0; i < projNames.size(); i++) {
                    if (i > 0) {
                        sql.append(", ");
                    }
                    sql.append("?");
                    params.add(projNames.get(i));
                }
                sql.append(") ");
            } else {
                sql.append(whereAdded ? " AND " : " WHERE ");
                whereAdded = true;
                sql.append("E.SSN NOT IN (");
                sql.append("SELECT W.ESSN FROM WORKS_ON W ");
                sql.append("JOIN PROJECT P ON W.PNO = P.PNUMBER ");
                sql.append("WHERE P.PNAME IN (");
                for (int i = 0; i < projNames.size(); i++) {
                    if (i > 0) {
                        sql.append(", ");
                    }
                    sql.append("?");
                    params.add(projNames.get(i));
                }
                sql.append(")) ");
            }
        }

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String fname = rs.getString(1);
                    String minit = rs.getString(2);
                    String lname = rs.getString(3);
                    if (minit == null) {
                        minit = "";
                    } else {
                        minit = minit.trim();
                    }
                    String fullName;
                    if (minit.isEmpty()) {
                        fullName = fname + " " + lname;
                    } else {
                        fullName = fname + " " + minit + ". " + lname;
                    }
                    results.add(fullName);
                }
            }
        }

        return results;
    }
}
