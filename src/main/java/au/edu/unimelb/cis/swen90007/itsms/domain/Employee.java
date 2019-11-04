package au.edu.unimelb.cis.swen90007.itsms.domain;

import au.edu.unimelb.cis.swen90007.itsms.database.EmployeeGateway;

/**
 * The Employee class defines the Domain layer interface
 * for Employee data,
 */
public class Employee extends User {

    private int departmentId;

    public Employee(int id, String username, String password, String firstName,
                    String lastName, int departmentId) {
        super(id, username, password, firstName, lastName);
        this.departmentId = departmentId;
    }

    public Employee(EmployeeGateway employeeGateway) {
        super(employeeGateway);
        this.departmentId = employeeGateway.getDepartmentId();
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
