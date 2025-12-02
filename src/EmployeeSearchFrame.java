import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * EmployeeSearchFrame
 *
 * - Database name entry
 * - Department, Project, and Employee lists (scrollable)
 * - Multiple selection for Department and Project
 * - Fill, Search, and Clear buttons
 * - NOT check boxes for Department and Project
 *
 * The actual database logic is handled in DatabaseManager.
 */
public class EmployeeSearchFrame extends JFrame {

    private JPanel contentPane;
    private JTextField txtDatabase;

    private DefaultListModel<String> departmentModel;
    private DefaultListModel<String> projectModel;
    private DefaultListModel<String> employeeModel;

    private JList<String> lstDepartment;
    private JList<String> lstProject;
    private JList<String> lstEmployee;

    private JCheckBox chkNotDept;
    private JCheckBox chkNotProject;

    private DatabaseManager dbManager;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EmployeeSearchFrame frame = new EmployeeSearchFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public EmployeeSearchFrame() {
        setTitle("Employee Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 480, 360);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        dbManager = new DatabaseManager();

        JLabel lblDatabase = new JLabel("Database:");
        lblDatabase.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDatabase.setBounds(20, 20, 60, 20);
        contentPane.add(lblDatabase);

        txtDatabase = new JTextField();
        txtDatabase.setBounds(90, 20, 200, 20);
        contentPane.add(txtDatabase);
        txtDatabase.setColumns(10);

        JButton btnFill = new JButton("Fill");
        btnFill.setBounds(310, 19, 80, 23);
        contentPane.add(btnFill);

        JLabel lblDepartment = new JLabel("Department");
        lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDepartment.setBounds(20, 60, 100, 20);
        contentPane.add(lblDepartment);

        JLabel lblProject = new JLabel("Project");
        lblProject.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblProject.setBounds(250, 60, 100, 20);
        contentPane.add(lblProject);

        JLabel lblEmployee = new JLabel("Employee");
        lblEmployee.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEmployee.setBounds(20, 170, 100, 20);
        contentPane.add(lblEmployee);

        chkNotDept = new JCheckBox("Not");
        chkNotDept.setBounds(130, 60, 60, 23);
        contentPane.add(chkNotDept);

        chkNotProject = new JCheckBox("Not");
        chkNotProject.setBounds(340, 60, 60, 23);
        contentPane.add(chkNotProject);

        departmentModel = new DefaultListModel<String>();
        projectModel = new DefaultListModel<String>();
        employeeModel = new DefaultListModel<String>();

        lstDepartment = new JList<String>(departmentModel);
        lstDepartment.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        lstProject = new JList<String>(projectModel);
        lstProject.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        lstEmployee = new JList<String>(employeeModel);
        lstEmployee.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollDept = new JScrollPane(lstDepartment);
        scrollDept.setBounds(20, 85, 180, 70);
        contentPane.add(scrollDept);

        JScrollPane scrollProj = new JScrollPane(lstProject);
        scrollProj.setBounds(250, 85, 180, 70);
        contentPane.add(scrollProj);

        JScrollPane scrollEmp = new JScrollPane(lstEmployee);
        scrollEmp.setBounds(20, 195, 410, 90);
        contentPane.add(scrollEmp);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(90, 300, 100, 23);
        contentPane.add(btnSearch);

        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(260, 300, 100, 23);
        contentPane.add(btnClear);

        // Action listeners

        btnFill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleFill();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSearch();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleClear();
            }
        });
    }

    private void handleFill() {
        String dbName = txtDatabase.getText().trim();
        if (dbName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a database name.");
            return;
        }

        boolean ok = dbManager.connect(dbName, this);
        if (!ok) {
            // DatabaseManager already shows an error message
            return;
        }

        departmentModel.clear();
        projectModel.clear();
        employeeModel.clear();

        try {
            List<String> depts = dbManager.loadSingleColumn(
                "SELECT DNAME FROM DEPARTMENT ORDER BY DNAME"
            );
            for (String d : depts) {
                departmentModel.addElement(d);
            }

            List<String> projs = dbManager.loadSingleColumn(
                "SELECT PNAME FROM PROJECT ORDER BY PNAME"
            );
            for (String p : projs) {
                projectModel.addElement(p);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading departments or projects: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleSearch() {
        employeeModel.clear();

        if (!dbManager.isConnected()) {
            JOptionPane.showMessageDialog(this, "Database is not connected. Click Fill first.");
            return;
        }

        List<String> selectedDepts = lstDepartment.getSelectedValuesList();
        List<String> selectedProjs = lstProject.getSelectedValuesList();
        boolean notDept = chkNotDept.isSelected();
        boolean notProj = chkNotProject.isSelected();

        try {
            List<String> employees = dbManager.searchEmployees(selectedDepts, notDept, selectedProjs, notProj);
            if (employees.isEmpty()) {
                employeeModel.addElement("No employees found for the selected criteria.");
            } else {
                for (String emp : employees) {
                    employeeModel.addElement(emp);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error searching employees: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleClear() {
        employeeModel.clear();
        lstDepartment.clearSelection();
        lstProject.clearSelection();
        chkNotDept.setSelected(false);
        chkNotProject.setSelected(false);
    }
}
